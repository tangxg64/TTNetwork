package com.tangxg.netlibrary.download;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AtomicFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by tangxg on 2018/12/19 0019.
 */

public class DownLoadManager {
    private static DownLoadManager dlManager;
    private static final int MAX_THREAD_COUNT = 3;
    //防止重复请求下载
    private HashSet<DownLoadTask> taskHashSet = new HashSet<>();

    private DownLoadManager() {
    }

    public static DownLoadManager getInstance() {
        if (dlManager == null) {
            synchronized (DownLoadManager.class) {
                if (dlManager == null) {
                    dlManager = new DownLoadManager();
                }
            }
        }
        return dlManager;
    }

    private static final ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(MAX_THREAD_COUNT, MAX_THREAD_COUNT, 60, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>(), new ThreadFactory() {
        AtomicInteger integer = new AtomicInteger(1);

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            Thread thread = new Thread(runnable, "thread id:" + integer.getAndIncrement());
            return thread;
        }
    });

    public void startDownLoad(final String url, final DownLoadCallback callback) {
        if (TextUtils.isEmpty(url) || callback == null) {
            return;
        }
        final DownLoadTask task = new DownLoadTask(url, callback);
        if (taskHashSet.contains(task)) {
            callback.fail(HttpManager.TASK_REPEAT_ERROR_CODE, "任务存在！");
            return;
        }
        taskHashSet.add(task);
        //第一次请求获取到contentLength
        HttpManager.getInstance().asyncRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                callback.fail();
                taskRemove(task);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful() && callback != null) {
                    callback.fail(HttpManager.NETWORK_ERROR_CODE, "网络请求失败！");
                    return;
                }
                long length = response.body().contentLength();
                if (length == -1) {
                    callback.fail(HttpManager.LENGTH_ERROR_CODE, "contentLength -1！");
                    return;
                }
                //每个线程均分下载数据
                processDownLoad(length, url, callback);

            }
        });
    }

    private void processDownLoad(long length, String url, DownLoadCallback callback) {
        long threadLoadSize = length / MAX_THREAD_COUNT;
        for (int i = 0; i < MAX_THREAD_COUNT; i++) {
            //计算出每个线程需要下载的长度和起始位，结束位
            long startSize = i * threadLoadSize;
            long endSize = 0;
            if (endSize == MAX_THREAD_COUNT -1) {
                endSize = length - 1;
            }else {
                endSize = (i + 1) * threadLoadSize - 1;
            }
            poolExecutor.execute(new DownLoadRunnable(startSize, endSize, url, callback));
        }
    }

    private void taskRemove(DownLoadTask task) {
        if (taskHashSet != null && task != null) {
            taskHashSet.remove(task);
        }
    }
}

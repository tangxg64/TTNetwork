package com.tangxg.netlibrary.download;

import android.content.Context;

import com.tangxg.netlibrary.FileStorageManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tangxg  on 2018/12/20 0020.
 * 邮箱  369516895@QQ.com
 */

public class HttpManager {
    public static final int NETWORK_ERROR_CODE = 100;
    public static final int LENGTH_ERROR_CODE = 110;
    public static final int TASK_REPEAT_ERROR_CODE = 120;
    private Context context;
    private OkHttpClient okClient;
    private static HttpManager httpManager;


    private HttpManager() {
        okClient = new OkHttpClient();
    }

    public static HttpManager getInstance() {
        if (httpManager == null) {
            synchronized (HttpManager.class) {
                if (httpManager == null) {
                    httpManager = new HttpManager();
                }
            }
        }
        return httpManager;
    }

    public void init(Context context) {
        this.context = context;
    }

    /**
     * 同步下载请求
     *
     * @param url
     * @return
     */
    public Response syncRequest(String url) {
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = okClient.newCall(request).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 同步下载请求
     * (根据Range分段下载)
     *
     * @param url
     * @return
     */
    public Response syncRequestByRange(String url, long start, long end) {
        Request request = new Request.Builder().url(url)
                .addHeader("Range", "bytes=" + start + "-" + end)
                .build();
        try {
            Response response = okClient.newCall(request).execute();
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步单线程下载请求
     *
     * @param url
     * @return
     */
    public void asyncRequest(final String url, final DownLoadCallback callback) {
        if (callback == null) {
            return;
        }
        final Request request = new Request.Builder().url(url).build();
        okClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.fail(NETWORK_ERROR_CODE, "网络请求失败！");
                } else {
                    File file = FileStorageManager.getInstance().getFileByUrl(url);
                    byte[] buffer = new byte[1024 * 200];
                    int len;
                    FileOutputStream outputStream = new FileOutputStream(file);
                    InputStream inputStream = response.body().byteStream();
                    while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                        outputStream.write(buffer, 0, len);
                        outputStream.flush();
                    }
                    callback.success(file);
                }
            }
        });
    }

    public void asyncRequest(final String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        okClient.newCall(request).enqueue(callback);
    }


}

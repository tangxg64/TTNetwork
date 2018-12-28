package com.tangxg.netlibrary.download;

import android.content.Context;

import com.tangxg.netlibrary.FileStorageManager;
import com.tangxg.netlibrary.db.DownLoadDao;
import com.tangxg.netlibrary.db.DownLoadEntity;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

/**
 * Created by tangxg  on 2018/12/20 0020.
 * 邮箱  369516895@QQ.com
 */

public class DownLoadRunnable implements Runnable {
    private String url;
    private long startSize;
    private long endSize;
    private DownLoadEntity downLoadEntity;
    private DownLoadCallback callback;
    private Context context;


    public DownLoadRunnable(long startSize, long endSize, String url, DownLoadEntity downLoadEntity, DownLoadCallback callback) {
        this.startSize = startSize;
        this.endSize = endSize;
        this.url = url;
        this.downLoadEntity = downLoadEntity;
        this.callback = callback;
    }


    @Override
    public void run() {
        Response response = HttpManager.getInstance().syncRequestByRange(url, startSize, endSize);
        if (response == null && callback != null) {
            callback.onFail(HttpManager.NETWORK_ERROR_CODE, "网络请求失败！");
            return;
        }
        try {
            int progress = 0;
            File file = FileStorageManager.getInstance().getFileByUrl(url);
            //rwd 可读 ，可写 ， 可追加
            RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
            accessFile.seek(startSize);
            //写入数据
            byte[] buffer = new byte[1024 * 200];
            int len;
            InputStream inputStream = response.body().byteStream();
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                accessFile.write(buffer, 0, len);
                progress += len;
            }
            downLoadEntity.setProgress(progress);
            //下载信息写入到表中
            DownLoadDao dao = new DownLoadDao(context);
            dao.addDownLoadInfo(downLoadEntity);
            accessFile.close();
            callback.onSuccess(file);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}

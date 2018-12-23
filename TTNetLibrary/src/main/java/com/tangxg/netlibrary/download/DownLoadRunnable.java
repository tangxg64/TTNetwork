package com.tangxg.netlibrary.download;

import com.tangxg.netlibrary.FileStorageManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.Response;

/**
 * Created by tangxg  on 2018/12/20 0020.
 * 邮箱  369516895@QQ.com
 */

public class DownLoadRunnable implements Runnable {
    private long startSize;
    private long endSize;
    private String url;
    private DownLoadCallback callback;

    public DownLoadRunnable(long startSize, long endSize, String url, DownLoadCallback callback) {
        this.startSize = startSize;
        this.endSize = endSize;
        this.url = url;
        this.callback = callback;
    }


    @Override
    public void run() {
        Response response = HttpManager.getInstance().syncRequestByRange(url, startSize, endSize);
        if (response == null && callback != null) {
            callback.fail(HttpManager.NETWORK_ERROR_CODE, "网络请求失败！");
            return;
        }
        try {
            File file = FileStorageManager.getInstance().getFileByUrl(url);
            //rwd 可读 ，可写 ， 可追加
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
            randomAccessFile.seek(startSize);
            //写入数据
            byte[] buffer = new byte[1024 * 200];
            int len;
            InputStream inputStream = response.body().byteStream();
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                randomAccessFile.write(buffer, 0, len);
            }
            callback.success(file);
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }
}

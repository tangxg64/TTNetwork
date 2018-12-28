package com.tangxg.ttnet;

import android.app.Application;
import android.app.DownloadManager;

import com.facebook.stetho.Stetho;
import com.tangxg.netlibrary.FileStorageManager;
import com.tangxg.netlibrary.download.DownLoadManager;
import com.tangxg.netlibrary.download.HttpManager;

import java.nio.file.FileStore;

/**
 * Created by tangxg  on 2018/12/19 0019.
 * 邮箱  369516895@QQ.com
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FileStorageManager.getInstance().init(this);
        HttpManager.getInstance().init(this);
        DownLoadManager.getInstance().init(this);

        Stetho.initializeWithDefaults(this);
    }
}

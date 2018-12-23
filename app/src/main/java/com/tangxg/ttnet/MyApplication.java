package com.tangxg.ttnet;

import android.app.Application;

import com.tangxg.netlibrary.FileStorageManager;

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
    }
}

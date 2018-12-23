package com.tangxg.ttnet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;

import com.tangxg.netlibrary.FileStorageManager;
import com.tangxg.netlibrary.download.DownLoadCallback;
import com.tangxg.netlibrary.download.HttpManager;
import com.tangxg.netlibrary.utils.Logger;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void downLoad(View view) {
        HttpManager.getInstance().asyncRequest("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2895986097,3609514076&fm=173&app=25&f=JPEG?w=600&h=400&s=DBACB7475B8662D2062E5B6D0300E068"
                , new DownLoadCallback() {
                    @Override
                    public void success(File file) {
                        Logger.debug("ttnet" , file.getAbsolutePath());
                    }

                    @Override
                    public void fail(int code, String errorMessage) {

                    }

                    @Override
                    public void progress(int progress) {

                    }
                });
    }
}

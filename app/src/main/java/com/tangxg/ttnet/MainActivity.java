package com.tangxg.ttnet;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tangxg.netlibrary.FileStorageManager;
import com.tangxg.netlibrary.download.DownLoadCallback;
import com.tangxg.netlibrary.download.DownLoadManager;
import com.tangxg.netlibrary.download.HttpManager;
import com.tangxg.netlibrary.utils.Logger;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "ttnetwork";
    String downLoadUrl = "http://shouji.360tpcdn.com/170317/4556661a3b927ada1d8cf9f8a233ca36/com.sds.android.ttpod_10000700.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void downLoad(View view) {
        final ProgressBar progressBarView = findViewById(R.id.progressBar);
        DownLoadManager.getInstance().startDownLoad(downLoadUrl, new DownLoadCallback() {
            @Override
            public void onSuccess(File file) {
                Toast.makeText(MainActivity.this, "onSuccess file" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
//                installApk(file);
            }

            @Override
            public void onFail(int code, String errorMessage) {
                Logger.debug(TAG, "code : " + code + "  mesg :" + errorMessage);
                Toast.makeText(MainActivity.this, "code : " + code + "  mesg :" + errorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int progress) {
                progressBarView.setProgress(progress);
            }
        });

    }

    private void installApk(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + file.getAbsoluteFile().toString()), "application/vnd.android.package-archive");
        MainActivity.this.startActivity(intent);
    }
}

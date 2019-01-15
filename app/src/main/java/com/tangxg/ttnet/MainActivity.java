package com.tangxg.ttnet;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.tangxg.netlibrary.FileStorageManager;
import com.tangxg.netlibrary.download.DownLoadCallback;
import com.tangxg.netlibrary.download.DownLoadManager;
import com.tangxg.netlibrary.download.HttpManager;
import com.tangxg.netlibrary.utils.Logger;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "ttnetwork";
    String downLoadUrl = "http://shouji.360tpcdn.com/170317/4556661a3b927ada1d8cf9f8a233ca36/com.sds.android.ttpod_10000700.apk";
    String downLoadUrl2 = "http://shouji.360tpcdn.com/160901/84c090897cbf0158b498da0f42f73308/com.icoolme.android.weather_2016090200.apk";

    private int conut = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void downLoad(View view) {

            XXPermissions.with(this)
                    //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                    //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
                    .permission(Permission.Group.STORAGE) //不指定权限则自动获取清单中的危险权限
                    .request(new OnPermission() {

                        @Override
                        public void hasPermission(List<String> granted, boolean isAll) {
                            downLoadStart();
                        }

                        @Override
                        public void noPermission(List<String> denied, boolean quick) {

                        }
                    });
    }

    private void downLoadStart() {
        final ProgressBar progressBarView = findViewById(R.id.progressBar);
        DownLoadManager.getInstance().startDownLoad(downLoadUrl, new DownLoadCallback() {
            @Override
            public void onSuccess(File file) {
//                Toast.makeText(MainActivity.this, "onSuccess file" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                if (conut == 2) {
                    String path = file.getPath();
                    String absPath = file.getAbsolutePath();
                    installApkFile(MainActivity.this, file.getPath());
                } else {
                    conut++;
                }

            }

            @Override
            public void onFail(int code, String errorMessage) {
                Toast.makeText(MainActivity.this, "code : " + code + "  mesg :" + errorMessage, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProgress(int progress) {
                Logger.info("progress : " + progress);
                progressBarView.setProgress(progress);
            }
        });
    }


    public static void installApkFile(Context context, String filePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.tangxg.ttnet.fileprovider", new File(filePath));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(filePath)), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }
}

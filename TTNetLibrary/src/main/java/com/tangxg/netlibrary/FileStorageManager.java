package com.tangxg.netlibrary;

import android.content.Context;
import android.os.Environment;

import com.tangxg.netlibrary.download.DownLoadManager;
import com.tangxg.netlibrary.utils.MD5Utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by tangxg  on 2018/12/19 0019.
 * 邮箱  369516895@QQ.com
 */

public class FileStorageManager {

    private static FileStorageManager fileStorageManager;
    private Context context;

    private FileStorageManager() {
    }

    public static FileStorageManager getInstance() {
        if (fileStorageManager == null) {
            synchronized (DownLoadManager.class) {
                if (fileStorageManager == null) {
                    fileStorageManager = new FileStorageManager();
                }
            }
        }
        return fileStorageManager;
    }

    public void init(Context context) {
        this.context = context;
    }

    public File getFileByUrl(String url) {
        File fileParent;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //挂载了SD卡
            fileParent = context.getExternalCacheDir();
        } else {
            //指定到系统cache目录
            fileParent = context.getCacheDir();
        }
        String fileName = MD5Utils.generateCode(url);
        File file = new File(fileParent, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }


}

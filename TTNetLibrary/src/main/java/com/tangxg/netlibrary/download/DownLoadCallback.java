package com.tangxg.netlibrary.download;

import java.io.File;

/**
 * Created by tangxg  on 2018/12/19 0019.
 * 邮箱  369516895@QQ.com
 */

public interface DownLoadCallback {
    void onSuccess(File file);

    void onFail(int code , String errorMessage);

    void onProgress(int progress);
}

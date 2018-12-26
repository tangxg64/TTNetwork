package com.tangxg.netlibrary.download;

/**
 * 下载任务对象信息
 * 用于缓存任务
 * Created by tangxg  on 2018/12/23 0023.
 * 邮箱  369516895@QQ.com
 */
public class DownLoadTask {
    private String url;
    private DownLoadCallback downLoadCallback;

    public DownLoadTask(String url, DownLoadCallback downLoadCallback) {
        this.url = url;
        this.downLoadCallback = downLoadCallback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DownLoadTask that = (DownLoadTask) o;

        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return downLoadCallback != null ? downLoadCallback.equals(that.downLoadCallback) : that.downLoadCallback == null;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (downLoadCallback != null ? downLoadCallback.hashCode() : 0);
        return result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DownLoadCallback getDownLoadCallback() {
        return downLoadCallback;
    }

    public void setDownLoadCallback(DownLoadCallback downLoadCallback) {
        this.downLoadCallback = downLoadCallback;
    }
}

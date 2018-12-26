package com.tangxg.netlibrary.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by tangxg  on 2018/12/24 0024.
 * 邮箱  369516895@QQ.com
 */
@DatabaseTable(tableName = "tab_download")
public class DownLoadEntity {
    @DatabaseField(useGetSet=true,generatedId=true,columnName="id")
    private int id;

    @DatabaseField(useGetSet=true, columnName = "url")
    private String url;

    @DatabaseField(useGetSet=true, columnName = "start_size")
    private long startSize;

    @DatabaseField(useGetSet=true, columnName = "end_size")
    private long endSize;

    @DatabaseField(useGetSet=true, columnName = "progress")
    private long progress;

    public DownLoadEntity(){

    }

    public DownLoadEntity(String url, long startSize, long endSize) {
//        this.id = id;
        this.url = url;
        this.startSize = startSize;
        this.endSize = endSize;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getStartSize() {
        return startSize;
    }

    public void setStartSize(long startSize) {
        this.startSize = startSize;
    }

    public long getEndSize() {
        return endSize;
    }

    public void setEndSize(long endSize) {
        this.endSize = endSize;
    }

    @Override
    public String toString() {
        return "DownLoadEntity{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", startSize=" + startSize +
                ", endSize=" + endSize +
                '}';
    }
}

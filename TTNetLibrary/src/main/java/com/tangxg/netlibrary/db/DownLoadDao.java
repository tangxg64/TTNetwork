package com.tangxg.netlibrary.db;

import android.content.Context;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangxg  on 2018/12/25 0025.
 * 邮箱  369516895@QQ.com
 */

public class DownLoadDao {
    private Dao<DownLoadEntity, Integer> downLoadDao;
    private DBHelper helper;

    public DownLoadDao(Context contex) {
        try {
            helper = DBHelper.getInstance(contex);
            downLoadDao = helper.getDao(DownLoadEntity.class);
            if (downLoadDao == null) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 每个线程下载信息存入表中
     * @param dle 存入对象
     */
    public void addDownLoadInfo(DownLoadEntity dle) {
        try {
            downLoadDao.createOrUpdate(dle);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据url查询
     * @param url 下载地址
     */
    public List<DownLoadEntity> queryDownLoadByUrl(String url) {
        ArrayList<DownLoadEntity> results = null;
        try {
            results = (ArrayList<DownLoadEntity>) downLoadDao.queryBuilder().where().eq("url", url).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}

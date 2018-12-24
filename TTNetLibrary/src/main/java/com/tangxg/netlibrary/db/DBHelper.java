package com.tangxg.netlibrary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.ParcelUuid;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * ormlite helper类
 * Created by tangxg  on 2018/12/24 0024.
 * 邮箱  369516895@QQ.com
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String TABLE_NAME = "orm_download_info.db";//默认是在data/data/包名/databases/路径下
    public static final String DATABASE_PATH = Environment.getExternalStorageDirectory() + "/ttnet/" + TABLE_NAME;//指定路径
    private static final int DB_VERSION = 1;
    private Map<String, Dao> daos = new HashMap<String, Dao>();

    private DBHelper(Context context) {
        super(context, DATABASE_PATH, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, DownLoadEntity.class);
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBHelper getInstance(Context context) {
        return Holder.getInstance(context);
    }

    private static class Holder {
        private static DBHelper getInstance(Context context) {
            return new DBHelper(context.getApplicationContext());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (newVersion == 2) {
            //数据库、表名、列名、类型
//            DatabaseUtil.updateColumn(database, "tab_download", "s_phone", "VARCHAR", null);
        }
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}

package com.pyystone.apkintentjump;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by pyysotne on 2016/2/2.
 * email: pyystone@163.com
 * QQ: 862429936
 */
public class DBManager {
    private static final int DB_VERSION = 1;
    private static DBManager instance;
    private SQLiteDatabase mSQLiteDatabase;
    DBManager(Context ctx) {
        MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(ctx,"jumpdb.db",null,DB_VERSION);
        mSQLiteDatabase = dbHelper.getWritableDatabase();
    }
    public static DBManager getInstance() {
        return instance;
    }
    public void execSql(String sql) {
        mSQLiteDatabase.execSQL(sql);
    }

    public static void init(Context context) {
        instance = new DBManager(context);
    }

    public Cursor rawQuery(String sqlstr) {
        return mSQLiteDatabase.rawQuery(sqlstr,null);
    }

}

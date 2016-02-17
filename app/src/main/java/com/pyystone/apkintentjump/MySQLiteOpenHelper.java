package com.pyystone.apkintentjump;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by pyysotne on 2016/2/2.
 * email: pyystone@163.com
 * QQ: 862429936
 * dbmanager
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        generateDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for (int version = oldVersion + 1; version <= newVersion ; version ++) {
            updateDb(db,version);
        }
    }

    private void updateDb(SQLiteDatabase db,int version) {
    }


    // 数据库创建的时候需要的跑的sql
    private void generateDB(SQLiteDatabase db) {
        db.execSQL("create table tbl_scheme(id INTEGER PRIMARY KEY autoincrement, uuid varchar(30),isdelete INTEGER DEFAULT 0,scheme varchar(30),schemedes varchar(200))");
        db.execSQL("create table tbl_host(id INTEGER PRIMARY KEY autoincrement, uuid varchar(30),isdelete INTEGER DEFAULT 0,parentid INTEGER ,host varchar(30),hostdes varchar(200))");
        db.execSQL("create table tbl_param(id INTEGER PRIMARY KEY autoincrement, uuid varchar(30),isdelete INTEGER DEFAULT 0,sourceid INTEGER,key varchar(30),defaultvalue varchar(30),keydes varchar(200))");
    }

}

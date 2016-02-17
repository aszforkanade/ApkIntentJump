package com.pyystone.apkintentjump;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.pyystone.apkintentjump.data.JumpDataManager;
import com.pyystone.apkintentjump.data.JumpHost;
import com.pyystone.apkintentjump.data.JumpParam;
import com.pyystone.apkintentjump.data.JumpScheme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        generateDBData(db);
    }

    private void generateDBData(SQLiteDatabase db) {
        StringBuilder builder = new StringBuilder();
        builder.append("replace into ").append(JumpScheme.DB_TBL_SCHEME).append(" (")
                .append(JumpScheme.DB_TAG_BASE_UUID).append(",")
                .append(JumpScheme.DB_TAG_BASE_ISDELETE).append(",")
                .append(JumpScheme.DB_TAG_SCHEME_SCHEME).append(",")
                .append(JumpScheme.DB_TAG_SCHEME_SCHEMEDES).append(")")
                .append("values(")
                .append(1).append(",")
                .append(0).append(",")
                .append("'pyystone'").append(",")
                .append("'just test insertDB'").append(")");
        db.execSQL(builder.toString());

        builder = new StringBuilder();
        builder.append("replace into ").append(JumpHost.DB_TBL_HOST).append(" (")
                .append(JumpHost.DB_TAG_BASE_UUID).append(",")
                .append(JumpHost.DB_TAG_BASE_ISDELETE).append(",")
                .append(JumpHost.DB_TAG_HOST_PARENTID).append(",")
                .append(JumpHost.DB_TAG_HOST_HOST).append(",")
                .append(JumpHost.DB_TAG_HOST_HOSTDES).append(")")
                .append("values(")
                .append(1).append(",")
                .append(0).append(",")
                .append(1).append(",")
                .append("'hello'").append(",")
                .append("'host test1'").append(")");
        db.execSQL(builder.toString());

        builder = new StringBuilder();
        builder.append("replace into ").append(JumpParam.DB_TBL_PARAM).append(" (")
                .append(JumpParam.DB_TAG_BASE_UUID).append(",")
                .append(JumpParam.DB_TAG_BASE_ISDELETE).append(",")
                .append(JumpParam.DB_TAG_PARAM_KEY).append(",")
                .append(JumpParam.DB_TAG_PARAM_DEFAULT_VALUE).append(",")
                .append(JumpParam.DB_TAG_PARAM_KEYDES).append(",")
                .append(JumpParam.DB_TAG_PARAM_SOURCEID).append(")")
                .append("values(")
                .append(1).append(",")
                .append(0).append(",")
                .append("'testKey'").append(",")
                .append("1").append(",")
                .append("'testDes'").append(",")
                .append(1).append(")");
        db.execSQL(builder.toString());

//        JSONObject data = new JSONObject();
//        try {
//            JSONArray schemes = new JSONArray();
//            schemes.put(getTestData());
//            data.put(JumpDataManager.JSON_TAG_SCHEME,schemes);
//            JumpDataManager.getInstance().updateData(data.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    // 这个方法用于生成需要显示的测试数据
    private JSONObject getTestData() throws JSONException {


        JSONObject scheme1 = new JSONObject();
        scheme1.put(JumpScheme.DB_TAG_BASE_UUID,1);
        scheme1.put(JumpScheme.DB_TAG_BASE_ISDELETE,0);
        scheme1.put(JumpScheme.DB_TAG_SCHEME_SCHEME,"pyystone");
        scheme1.put(JumpScheme.DB_TAG_SCHEME_SCHEMEDES,"just test insertDB");

        JSONArray hosts = new JSONArray();
        JSONObject host1 = new JSONObject();
        host1.put(JumpHost.DB_TAG_BASE_UUID,1);
        host1.put(JumpHost.DB_TAG_BASE_ISDELETE,0);
        host1.put(JumpHost.DB_TAG_HOST_PARENTID,1);
        host1.put(JumpHost.DB_TAG_HOST_HOST,"hello");
        host1.put(JumpHost.DB_TAG_HOST_HOSTDES,"host test1");

        JSONArray params = new JSONArray();
        JSONObject param1 = new JSONObject();
        param1.put(JumpParam.DB_TAG_BASE_UUID,1);
        param1.put(JumpParam.DB_TAG_BASE_ISDELETE,0);
        param1.put(JumpParam.DB_TAG_PARAM_DEFAULT_VALUE,0);
        param1.put(JumpParam.DB_TAG_PARAM_SOURCEID,1);
        param1.put(JumpParam.DB_TAG_PARAM_KEY,"testkey");
        param1.put(JumpParam.DB_TAG_PARAM_KEYDES,"testDes");
        params.put(param1);

        host1.put(JumpDataManager.JSON_TAG_PARAM,params);
        hosts.put(host1);
        scheme1.put(JumpDataManager.JSON_TAG_HOST,hosts);

        return scheme1;
    }

}

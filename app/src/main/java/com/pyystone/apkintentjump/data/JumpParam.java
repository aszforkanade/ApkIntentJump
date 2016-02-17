package com.pyystone.apkintentjump.data;

import android.database.Cursor;

import com.pyystone.apkintentjump.DBManager;

import java.util.ArrayList;

/**
 * Created by pyysotne on 2016/2/2.
 * email: pyystone@163.com
 * QQ: 862429936
 */
public class JumpParam extends JumpData {
    public static final String DB_TBL_PARAM = "tbl_param";
    public static final String DB_TAG_PARAM_KEY = "key";
    public static final String DB_TAG_PARAM_DEFAULT_VALUE = "defaultvalue";
    public static final String DB_TAG_PARAM_KEYDES = "keydes";
    public static final String DB_TAG_PARAM_SOURCEID = "sourceid";
    public String key;
    public String defaultValue;
    public String keyDes;
    public int sourceId;

    public JumpParam(){}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getKeyDes() {
        return keyDes;
    }

    public void setKeyDes(String keyDes) {
        this.keyDes = keyDes;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public void save() {

        int id = JumpDataManager.getSchemeIdByUuid(uuid,"tbl_scheme");
        StringBuilder builder = new StringBuilder();
        builder.append("replace into ").append(DB_TBL_PARAM).append(" (");

        if (id != -1) {
            builder.append(DB_TAG_BASE_ID).append(",");
        }
        builder.append(DB_TAG_BASE_UUID).append(",")
                .append(DB_TAG_BASE_ISDELETE).append(",")
                .append(DB_TAG_PARAM_KEY).append(",")
                .append(DB_TAG_PARAM_DEFAULT_VALUE).append(",")
                .append(DB_TAG_PARAM_KEYDES).append(",")
                .append(DB_TAG_PARAM_SOURCEID).append(")");

        builder.append("values(");

        if (id != -1) {
            builder.append(id).append(",");
        }
        builder.append(uuid).append(",")
                .append(isDelete).append(",")
                .append(key).append(",")
                .append(defaultValue).append(",")
                .append(keyDes).append("，")
                .append(sourceId).append(")");

        DBManager.getInstance().execSql(builder.toString());
    }

    public static JumpParam load(int id) {
        JumpParam param = new JumpParam();
        Cursor c = DBManager.getInstance().rawQuery("select * from " + DB_TBL_PARAM
                + " where id = " + id + " and "  + DB_TAG_BASE_ISDELETE + " = 0");
        try {
            if (c == null || !c.moveToFirst()) {
                return null;
            }
            param.id = c.getInt(c.getColumnIndex(DB_TAG_BASE_ID));
            param.uuid = c.getInt(c.getColumnIndex(DB_TAG_BASE_UUID));
            param.key = c.getString(c.getColumnIndex(DB_TAG_PARAM_KEY));
            param.defaultValue = c.getString(c.getColumnIndex(DB_TAG_PARAM_DEFAULT_VALUE));
            param.keyDes = c.getString(c.getColumnIndex(DB_TAG_PARAM_KEYDES));
            param.sourceId = c.getInt(c.getColumnIndex(DB_TAG_PARAM_SOURCEID));
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return param;
    }

    public static ArrayList<JumpParam> loadParamByHostUuid(int hostUuid) {
        ArrayList<JumpParam> params = new ArrayList<>();
        Cursor c = DBManager.getInstance().rawQuery(
                "select * from " + JumpHost.DB_TBL_HOST
                        + " where " + JumpHost.DB_TAG_HOST_PARENTID + " = " + hostUuid + " and "  + DB_TAG_BASE_ISDELETE + " = 0");
        try {
            if (c == null || !c.moveToFirst()) {
                return null;
            }
            int idIndex = c.getColumnIndex(DB_TAG_BASE_ID);
            do {
                params.add(load(c.getInt(idIndex)));
            } while (c.moveToNext());

        } finally {
            if (c != null) {
                c.close();
            }
        }
        return params;
    }

}
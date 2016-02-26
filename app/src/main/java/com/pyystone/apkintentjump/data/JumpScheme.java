package com.pyystone.apkintentjump.data;

import android.database.Cursor;

import com.pyystone.apkintentjump.DBManager;

import java.util.ArrayList;

/**
 * Created by pyysotne on 2016/2/2.
 * email: pyystone@163.com
 * QQ: 862429936
 *
 */
public class JumpScheme extends JumpData {
    public static final String DB_TBL_SCHEME = "tbl_scheme";
    public static final String DB_TAG_SCHEME_SCHEME = "scheme";
    public static final String DB_TAG_SCHEME_SCHEMEDES = "schemedes";

    private String scheme;
    private String schemeDes;
    private ArrayList<JumpHost> hosts;
    public JumpScheme(){}

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getSchemeDes() {
        return schemeDes;
    }

    public void setSchemeDes(String schemeDes) {
        this.schemeDes = schemeDes;
    }

    public ArrayList<JumpHost> getHosts() {
        return hosts;
    }

    public void setHosts(ArrayList<JumpHost> hosts) {
        this.hosts = hosts;
    }

    public void save() {
        int id = JumpDataManager.getSchemeIdByUuid(uuid,DB_TBL_SCHEME);
        StringBuilder builder = new StringBuilder();
        builder.append("replace into ").append(DB_TBL_SCHEME).append(" (");

        if (id != -1) {
            builder.append(DB_TAG_BASE_ID).append(",");
        }
        builder.append(DB_TAG_BASE_UUID).append(",")
                .append(DB_TAG_BASE_ISDELETE).append(",")
                .append(DB_TAG_SCHEME_SCHEME).append(",")
                .append(DB_TAG_SCHEME_SCHEMEDES).append(")");

        builder.append("values(");

        if (id != -1) {
            builder.append(id).append(",");
        }
        builder.append(uuid).append(",")
                .append(isDelete).append(",")
                .append("'").append(scheme).append("',")
                .append("'").append(schemeDes).append("')");

        DBManager.getInstance().execSql(builder.toString());
    }

    public static JumpScheme load(int id) {

        JumpScheme scheme = new JumpScheme();
        Cursor c = DBManager.getInstance().rawQuery("select * from " + DB_TBL_SCHEME
                + " where "+DB_TAG_BASE_ID+" = " + id + " and "  + DB_TAG_BASE_ISDELETE + " = 0");
        try {
            if (c == null || !c.moveToFirst()) {
                return null;
            }
            scheme.uuid = c.getInt(c.getColumnIndex(DB_TAG_BASE_UUID));
            scheme.scheme = c.getString(c.getColumnIndex(DB_TAG_SCHEME_SCHEME));
            scheme.schemeDes = c.getString(c.getColumnIndex(DB_TAG_SCHEME_SCHEMEDES));
            scheme.hosts = JumpHost.loadHostBySchemeUuid(scheme.uuid);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return scheme;
    }

    public static ArrayList<JumpScheme> loadAllScheme() {
        ArrayList<JumpScheme> schemes = new ArrayList<>();
        Cursor c = DBManager.getInstance().rawQuery("select * from " + DB_TBL_SCHEME + " where " + DB_TAG_BASE_ISDELETE + " = 0 order by uuid asc");
        try {
            if (c == null || !c.moveToFirst()) {
                return null;
            }
            int idIndex = c.getColumnIndex(DB_TAG_BASE_ID);
            do {
                schemes.add(load(c.getInt(idIndex)));
            } while (c.moveToNext());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return schemes;
    }
}

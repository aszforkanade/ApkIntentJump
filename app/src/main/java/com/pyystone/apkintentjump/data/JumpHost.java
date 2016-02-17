package com.pyystone.apkintentjump.data;

import android.database.Cursor;

import com.pyystone.apkintentjump.DBManager;

import java.util.ArrayList;

/**
 * Created by pyysotne on 2016/2/2.
 * email: pyystone@163.com
 * QQ: 862429936
 */
public class JumpHost extends JumpData{
    public static final String DB_TBL_HOST = "tbl_host";
    public static final String DB_TAG_HOST_PARENTID = "parentid";
    public static final String DB_TAG_HOST_HOST = "host";
    public static final String DB_TAG_HOST_HOSTDES = "hostdes";
    public int parentId;
    public String host;
    public String hostDes;
    public ArrayList<JumpParam> params;

    public JumpHost(){}
    public JumpHost(int id, int uuid, int parentId, String host, String hostDes, ArrayList<JumpParam> params) {
        this.id = id;
        this.uuid = uuid;
        this.parentId = parentId;
        this.host = host;
        this.hostDes = hostDes;
        this.params = params;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHostDes() {
        return hostDes;
    }

    public void setHostDes(String hostDes) {
        this.hostDes = hostDes;
    }

    public ArrayList<JumpParam> getParams() {
        return params;
    }

    public void setParams(ArrayList<JumpParam> params) {
        this.params = params;
    }

    public void save() {
        int id = JumpDataManager.getSchemeIdByUuid(uuid,DB_TBL_HOST);
        StringBuilder builder = new StringBuilder();
        builder.append("replace into ").append(DB_TBL_HOST).append(" (");

        if (id != -1) {
            builder.append(DB_TAG_BASE_ID).append(",");
        }
        builder.append(DB_TAG_BASE_UUID).append(",")
                .append(DB_TAG_BASE_ISDELETE).append(",")
                .append(DB_TAG_HOST_PARENTID).append(",")
                .append(DB_TAG_HOST_HOST).append(",")
                .append(DB_TAG_HOST_HOSTDES).append(")");

        builder.append("values(");

        if (id != -1) {
            builder.append(id).append(",");
        }
        builder.append(uuid).append(",")
                .append(isDelete).append(",")
                .append(parentId).append(",")
                .append("'").append(host).append("',")
                .append("'").append(hostDes).append("')");

        DBManager.getInstance().execSql(builder.toString());

    }

    public static JumpHost load(int id) {
        JumpHost host = new JumpHost();
        Cursor c = DBManager.getInstance().rawQuery("select * from " + DB_TBL_HOST
                + " where id = " + id + " and "  + DB_TAG_BASE_ISDELETE + " = 0");
        try {
            if (c == null || !c.moveToFirst()) {
                return null;
            }
            host.id = c.getInt(c.getColumnIndex(DB_TAG_BASE_ID));
            host.uuid = c.getInt(c.getColumnIndex(DB_TAG_BASE_UUID));
            host.host = c.getString(c.getColumnIndex(DB_TAG_HOST_HOST));
            host.hostDes = c.getString(c.getColumnIndex(DB_TAG_HOST_HOSTDES));
            host.parentId = c.getInt(c.getColumnIndex(DB_TAG_HOST_PARENTID));
            host.params = JumpParam.loadParamByHostUuid(host.uuid);
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return host;
    }

    public static ArrayList<JumpHost> loadHostBySchemeUuid(int schemeUuid) {
        ArrayList<JumpHost> hosts = new ArrayList<>();
        Cursor c = DBManager.getInstance().rawQuery(
                "select * from " + DB_TBL_HOST
                        + " where " + DB_TAG_HOST_PARENTID + " = " + schemeUuid + " and "  + DB_TAG_BASE_ISDELETE + " = 0");
        try {
            if (c == null || !c.moveToFirst()) {
                return null;
            }
            int idIndex = c.getColumnIndex(DB_TAG_BASE_ID);
            do {
                int id = c.getInt(idIndex);
                hosts.add(load(id));
            } while (c.moveToNext());
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return  hosts;
    }

}

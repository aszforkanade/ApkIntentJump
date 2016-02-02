package com.pyystone.apkintentjump.data;

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

    public JumpHost(int id, int uuid, int parentId, String host, String hostDes, ArrayList<JumpParam> params) {
        this.id = id;
        this.uuid = uuid;
        this.parentId = parentId;
        this.host = host;
        this.hostDes = hostDes;
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
                .append(DB_TAG_HOST_PARENTID).append(",")
                .append(DB_TAG_HOST_HOST).append(",")
                .append(DB_TAG_HOST_HOSTDES).append(")");

        builder.append("values(");

        if (id != -1) {
            builder.append(id).append(",");
        }
        builder.append(uuid).append(",")
                .append(parentId).append(",")
                .append(host).append(",")
                .append(hostDes).append(")");

        DBManager.getInstance().execSql(builder.toString());

    }
}

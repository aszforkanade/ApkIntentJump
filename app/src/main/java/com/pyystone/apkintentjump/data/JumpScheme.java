package com.pyystone.apkintentjump.data;

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

    public String scheme;
    public String schemeDes;
    public ArrayList<JumpHost> hosts;

    public JumpScheme(int id, int uuid, String scheme, String schemeDes, ArrayList<JumpHost> hosts) {
        this.id = id;
        this.uuid = uuid;
        this.scheme = scheme;
        this.schemeDes = schemeDes;
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
                .append(DB_TAG_SCHEME_SCHEME).append(",")
                .append(DB_TAG_SCHEME_SCHEMEDES).append(")");

        builder.append("values(");

        if (id != -1) {
            builder.append(id).append(",");
        }
        builder.append(uuid).append(",")
                .append(scheme).append(",")
                .append(schemeDes).append(")");

        DBManager.getInstance().execSql(builder.toString());
    }
}

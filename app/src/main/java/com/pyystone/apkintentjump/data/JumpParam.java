package com.pyystone.apkintentjump.data;

import com.pyystone.apkintentjump.DBManager;

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

    public JumpParam(int id, int uuid, String key, String defaultValue, String keyDes,int sourceId) {
        this.id = id;
        this.uuid = uuid;
        this.key = key;
        this.defaultValue = defaultValue;
        this.keyDes = keyDes;
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
                .append(DB_TAG_PARAM_KEY).append(",")
                .append(DB_TAG_PARAM_DEFAULT_VALUE).append(",")
                .append(DB_TAG_PARAM_KEYDES).append(",")
                .append(DB_TAG_PARAM_SOURCEID).append(")");

        builder.append("values(");

        if (id != -1) {
            builder.append(id).append(",");
        }
        builder.append(uuid).append(",")
                .append(key).append(",")
                .append(defaultValue).append(",")
                .append(keyDes).append("，")
                .append(sourceId).append(")");

        DBManager.getInstance().execSql(builder.toString());
    }
}
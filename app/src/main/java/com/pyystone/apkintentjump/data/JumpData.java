package com.pyystone.apkintentjump.data;

/**
 * Created by pyysotne on 2016/2/2.
 * email: pyystone@163.com
 * QQ: 862429936
 */
public abstract class JumpData {
    public static final String DB_TAG_BASE_ID = "id";
    public static final String DB_TAG_BASE_UUID = "uuid";
    public static final String DB_TAG_BASE_ISDELETE = "isdelete";
    public int id;
    public int uuid;
    public int isDelete = 0;
    public abstract void save();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUuid() {
        return uuid;
    }

    public void setUuid(int uuid) {
        this.uuid = uuid;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}

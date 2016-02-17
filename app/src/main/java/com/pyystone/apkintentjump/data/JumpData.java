package com.pyystone.apkintentjump.data;

/**
 * Created by pyysotne on 2016/2/2.
 * email: pyystone@163.com
 * QQ: 862429936
 */
public abstract class JumpData {
    public static final String DB_TAG_BASE_ID = "id";
    public static final String DB_TAG_BASE_UUID = "uuid";
    public int id;
    public int uuid;
    public abstract void save();
}

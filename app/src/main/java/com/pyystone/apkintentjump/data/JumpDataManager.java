package com.pyystone.apkintentjump.data;

import android.database.Cursor;

import com.pyystone.apkintentjump.DBManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by pyysotne on 2016/2/1.
 * email: pyystone@163.com
 * QQ: 862429936
 */
public class JumpDataManager {
    private static JumpDataManager mInstance;
    private ArrayList<JumpScheme> mSchemes;
    public static void init() {
        if (mInstance == null) {
            mInstance = new JumpDataManager();
            mInstance.loadData();
        }
    }
    private void loadData() {
        updateData("");
    }

    // 数据装载刷新
    private void updateData(String jsonStr) {
        try {
            JSONObject object = new JSONObject(jsonStr);
            if (object.optInt("code",-1) != 0) {
                return;
            }
            JSONObject data = object.optJSONObject("data");
            JSONArray schemes = data.optJSONArray("schemes");

            if (!needUpdateData(data)) {
                return;
            }
            if (schemes.length() == 0) {
                return;
            }
            int length = schemes.length();
            for (int i = 0 ; i < length ; i ++) {
                dealScheme((JSONObject) schemes.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //预留刷新机制控制，比如后期增加LastUpdateTime
    private boolean needUpdateData(JSONObject object) {
        return true;
    }

    // 处理scheme信息
    private void dealScheme(JSONObject scheme) throws JSONException {
        int uuid = scheme.optInt(JumpData.DB_TAG_BASE_UUID,-1);
        String schemeName = scheme.optString(JumpScheme.DB_TAG_SCHEME_SCHEME);
        String schemeDes = scheme.optString(JumpScheme.DB_TAG_SCHEME_SCHEMEDES);
        JSONArray hosts = scheme.optJSONArray("hosts");
        int length = hosts.length();
        if (uuid != 0) {
            return;
        }
        for (int i = 0 ; i < length ; i ++) {
            dealHost((JSONObject) hosts.get(i));
        }
        JumpScheme jumpScheme = new JumpScheme(0 , uuid, schemeName, schemeDes, null);
        jumpScheme.save();

    }

    // 处理host信息
    private void dealHost(JSONObject object) throws JSONException {
        int uuid = object.optInt(JumpData.DB_TAG_BASE_UUID,-1);
        int parentId = object.optInt(JumpHost.DB_TAG_HOST_PARENTID);
        String host = object.optString(JumpHost.DB_TAG_HOST_HOST);
        String hostDes = object.optString(JumpHost.DB_TAG_HOST_HOSTDES);
        JSONArray params = object.optJSONArray("params");
        int length = params.length();
        if (uuid != 0) {
            return;
        }
        for (int i = 0 ; i < length ; i ++) {
            dealParams((JSONObject) params.get(i));
        }
        JumpHost jumpHost = new JumpHost(0,uuid,parentId,host,hostDes,null);
        jumpHost.save();
    }

    // 处理params信息
    private void dealParams(JSONObject object) {
        int uuid = object.optInt(JumpData.DB_TAG_BASE_UUID,-1);
        int soureceId = object.optInt(JumpParam.DB_TAG_PARAM_SOURCEID);
        String key = object.optString(JumpParam.DB_TAG_PARAM_KEY);
        String keyDes = object.optString(JumpParam.DB_TAG_PARAM_KEYDES);
        String defaultValue = object.optString(JumpParam.DB_TAG_PARAM_DEFAULT_VALUE);
        if (uuid != 0) {
            return;
        }
        JumpParam jumpParam = new JumpParam(0,uuid,key,defaultValue,keyDes,soureceId);
        jumpParam.save();

    }

    public static int getSchemeIdByUuid(int uuid, String tblName) {
        String builder = "select id from " + tblName + " where uuid = '" +
                uuid +
                "'";
        Cursor c = DBManager.getInstance().rawQuery(builder);
        try {
            if (c == null || !c.moveToFirst()) {
                return -1;
            }
            return c.getInt(c.getColumnIndex("id"));
        } finally {
            if (c != null) {
                c.close();
            }
        }
    }

}

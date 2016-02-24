package com.pyystone.apkintentjump.data;

import android.app.Activity;
import android.database.Cursor;

import com.pyystone.apkintentjump.DBManager;
import com.pyystone.apkintentjump.http.HttpManager;

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
    public static final String JSON_TAG_SCHEME = "schemes";
    public static final String JSON_TAG_HOST = "hosts";
    public static final String JSON_TAG_PARAM = "params";
    private static JumpDataManager mInstance;
    private ArrayList<JumpScheme> mSchemes;
    public static void init() {
        if (mInstance == null) {
            mInstance = new JumpDataManager();
            mInstance.LoadData();
        }
    }

    private void LoadData() {
        mSchemes = JumpScheme.loadAllScheme();
    }

    // 数据装载刷新
    public void updateData(String jsonStr) {
        try {
            JSONObject object = new JSONObject(jsonStr);
            if (object.optInt("code",-1) != 0) {
                return;
            }
            JSONObject data = object.optJSONObject("data");
            JSONArray schemes = data.optJSONArray(JSON_TAG_SCHEME);
            JSONArray hosts = data.optJSONArray(JSON_TAG_HOST);
            JSONArray params = data.optJSONArray(JSON_TAG_PARAM);

            // 如果不需要刷新就直接返回
            if (!needUpdateData(data)) {
                return;
            }
            // 如果scheme的长度为0也就直接返回
            if (schemes.length() == 0) {
                return;
            }
            int length = schemes.length();
            for (int i = 0 ; i < length ; i ++) {
                dealScheme((JSONObject) schemes.get(i));
            }
            length = hosts.length();
            for (int i = 0 ; i < length ; i ++) {
                dealHost((JSONObject) hosts.get(i));
            }
            length = params.length();
            for (int i = 0 ; i < length ; i ++) {
                dealParams((JSONObject) params.get(i));
            }
            mInstance.LoadData();
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
        JumpScheme jumpScheme = new JumpScheme();
        jumpScheme.setUuid(uuid);
        jumpScheme.setScheme(schemeName);
        jumpScheme.setSchemeDes(schemeDes);
        jumpScheme.save();

    }

    // 处理host信息
    private void dealHost(JSONObject object) throws JSONException {
        int uuid = object.optInt(JumpData.DB_TAG_BASE_UUID,-1);
        int parentId = object.optInt(JumpHost.DB_TAG_HOST_PARENTID);
        String host = object.optString(JumpHost.DB_TAG_HOST_HOST);
        String hostDes = object.optString(JumpHost.DB_TAG_HOST_HOSTDES);
        JumpHost jumpHost = new JumpHost();
        jumpHost.setUuid(uuid);
        jumpHost.setParentId(parentId);
        jumpHost.setHost(host);
        jumpHost.setHostDes(hostDes);
        jumpHost.save();
    }

    // 处理params信息
    private void dealParams(JSONObject object) {
        int uuid = object.optInt(JumpData.DB_TAG_BASE_UUID,-1);
        int soureceId = object.optInt(JumpParam.DB_TAG_PARAM_SOURCEID);
        String key = object.optString(JumpParam.DB_TAG_PARAM_KEY);
        String keyDes = object.optString(JumpParam.DB_TAG_PARAM_KEYDES);
        String defaultValue = object.optString(JumpParam.DB_TAG_PARAM_DEFAULT_VALUE);
        JumpParam jumpParam = new JumpParam();
        jumpParam.setUuid(uuid);
        jumpParam.setKey(key);
        jumpParam.setDefaultValue(defaultValue);
        jumpParam.setKeyDes(keyDes);
        jumpParam.setSourceId(soureceId);
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

    public static JumpDataManager getInstance() {
        return mInstance;
    }

    public ArrayList<JumpScheme> getSchemes() {
        return mSchemes;
    }

    // 用于请求数据并刷新
    public void refreshData(Activity activity , final refreshCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpManager.httpGet("http://192.168.2.93:8000/wacai/zmltest/jumpIntent", new HttpManager.HttpCallBack() {
                    @Override
                    public void HttpResult(int resultCode, String response) {
                        if (resultCode == 200) {
                            updateData(response);
                            callBack.finish(true);
                        } else {
                            callBack.finish(false);
                        }
                    }
                });
            }
        }).start();
    }

    public interface refreshCallBack {
        void finish(boolean isSucceed);
    }
}

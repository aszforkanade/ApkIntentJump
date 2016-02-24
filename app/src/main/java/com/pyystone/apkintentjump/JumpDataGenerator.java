package com.pyystone.apkintentjump;

import com.pyystone.apkintentjump.data.JumpDataManager;
import com.pyystone.apkintentjump.data.JumpHost;
import com.pyystone.apkintentjump.data.JumpParam;
import com.pyystone.apkintentjump.data.JumpScheme;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by pyysotne on 2016/2/23.
 * email: pyystone@163.com
 * QQ: 862429936
 * 这个类不会在这个app中使用，可以当做一个参考或者数据生成器，用于对JumpDataManager里面Upadate数据的借鉴
 */
public class JumpDataGenerator {
    public String getJumpData() {
        JSONObject json = new JSONObject();
        try {
            json.put("code",0);
            json.put("error","");
            JSONObject data = new JSONObject();
            data.put(JumpDataManager.JSON_TAG_SCHEME,generatorScheme());
            data.put(JumpDataManager.JSON_TAG_HOST,generatorHost());
            data.put(JumpDataManager.JSON_TAG_PARAM,generatorParam());
            json.put("data",data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
    private JSONArray generatorScheme() throws JSONException {
        JSONArray array = new JSONArray();
        array.put(getSchemeJsonObject(1,"wacai","记账通用scheme"));
        array.put(getSchemeJsonObject(2,"wacaiopen","记账对外scheme"));
        return array;
    }
    private JSONArray generatorHost() throws JSONException {
        JSONArray array = new JSONArray();
        array.put(getHostJsonObject(1,1,"input","跳转到记一笔"));
        return array;
    }
    private JSONArray generatorParam() throws JSONException {
        JSONArray array = new JSONArray();
        array.put(getParamJsonObject(1,"input","跳转到记一笔","",1));
        return array;
    }
    private JSONObject getSchemeJsonObject(int uuid,String scheme,String schemeDes) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JumpScheme.DB_TAG_BASE_UUID,uuid);
        jsonObject.put(JumpScheme.DB_TAG_SCHEME_SCHEME,scheme);
        jsonObject.put(JumpScheme.DB_TAG_SCHEME_SCHEMEDES,schemeDes);
        return jsonObject;
    }
    private JSONObject getHostJsonObject(int uuid,int parentid,String host,String hostDes) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JumpHost.DB_TAG_BASE_UUID,uuid);
        jsonObject.put(JumpHost.DB_TAG_HOST_PARENTID,parentid);
        jsonObject.put(JumpHost.DB_TAG_HOST_HOST,host);
        jsonObject.put(JumpHost.DB_TAG_HOST_HOSTDES,hostDes);
        return jsonObject;
    }
    private JSONObject getParamJsonObject(int uuid,String key,String keydes,String defaultValue,int sourceId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(JumpParam.DB_TAG_BASE_UUID,uuid);
        jsonObject.put(JumpParam.DB_TAG_PARAM_KEY,key);
        jsonObject.put(JumpParam.DB_TAG_PARAM_KEYDES,keydes);
        jsonObject.put(JumpParam.DB_TAG_PARAM_DEFAULT_VALUE,defaultValue);
        jsonObject.put(JumpParam.DB_TAG_PARAM_SOURCEID,sourceId);
        return jsonObject;
    }
}

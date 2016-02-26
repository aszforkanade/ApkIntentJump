package com.pyystone.apkintentjump.ui;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.pyystone.apkintentjump.R;
import com.pyystone.apkintentjump.data.JumpHost;
import com.pyystone.apkintentjump.data.JumpParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pyysotne on 2016/2/22.
 * email: pyystone@163.com
 * QQ: 862429936
 */
public class ParamListAdapter extends BaseAdapter {

    private UrlChangeListener mUrlChangeListener;
    private ArrayList<JumpParam> mJumpParams;
    private LayoutInflater mLayoutInflater;
    private HashMap<String,String> mValues = new HashMap<>();

    public ParamListAdapter(Context context , JumpHost host , UrlChangeListener listener) {
        mLayoutInflater = LayoutInflater.from(context);
        mJumpParams = host.getParams();
        mUrlChangeListener = listener;
        initParamUrl();
    }

    private void initParamUrl() {
        for (JumpParam jumpParam : mJumpParams) {
            mValues.put(jumpParam.getKey(),jumpParam.getDefaultValue());
        }
        mUrlChangeListener.onValueChange(getParamUrl());
    }

    @Override
    public int getCount() {
        if (mJumpParams == null) {
            return 0;
        }
        return mJumpParams.size();
    }

    @Override
    public Object getItem(int position) {
        if (mJumpParams == null) {
            return null;
        }
        return mJumpParams.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (mJumpParams == null) {
            return 0;
        }
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(R.layout.param_list_item,null);
        JumpParam param = (JumpParam) getItem(position);
        TextView tv = (TextView) convertView.findViewById(R.id.paramName);
        tv.setText(String.format("name:%s",param.getKey()));
        EditText et = (EditText) convertView.findViewById(R.id.etParamValue);
        et.setText(mValues.get(param.getKey()));
        et.addTextChangedListener(new ParamChangeListener(param.getKey()));
        tv = (TextView) convertView.findViewById(R.id.tvParamDes);
        tv.setText(param.getKeyDes());
        return convertView;
    }

    private String getParamUrl() {
        StringBuilder stringBuilder = new StringBuilder();
        boolean isFirst = true;
        for (String string : mValues.keySet()) {
            if (TextUtils.isEmpty(mValues.get(string))) {
                continue;
            }
            if (!isFirst) {
                stringBuilder.append("&");
            }
            isFirst = false;
            stringBuilder.append(string).append("=").append(mValues.get(string));
        }
        return stringBuilder.toString();
    }
    class ParamChangeListener implements TextWatcher {

        private String paramName = "";

        public ParamChangeListener(String name) {
            paramName = name;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mValues.put(paramName,s.toString());
            mUrlChangeListener.onValueChange(getParamUrl());
        }
    }

    public interface UrlChangeListener {
        void onValueChange(String url);
    }
}

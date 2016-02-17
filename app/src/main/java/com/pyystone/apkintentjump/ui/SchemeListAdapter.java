package com.pyystone.apkintentjump.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pyystone.apkintentjump.R;
import com.pyystone.apkintentjump.data.JumpScheme;

import java.util.ArrayList;

/**
 * Created by pyysotne on 2016/2/17.
 * email: pyystone@163.com
 * QQ: 862429936
 */
public class SchemeListAdapter extends BaseAdapter {

    private ArrayList<JumpScheme> mSchemes;
    private LayoutInflater mInflater;
    public SchemeListAdapter(Context context, ArrayList<JumpScheme> schemes) {
        mSchemes = schemes;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        if (mSchemes == null) {
            return 0;
        }
        return mSchemes.size();
    }

    @Override
    public Object getItem(int position) {
        if (mSchemes == null) {
            return null;
        }
        return mSchemes.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (mSchemes == null) {
            return 0;
        }
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.scheme_list_item,null);
        }
        JumpScheme scheme = (JumpScheme)getItem(position);
        TextView tv = (TextView) convertView.findViewById(R.id.schemeName);
        tv.setText(scheme.getScheme());
        tv = (TextView) convertView.findViewById(R.id.schemeDes);
        tv.setText(scheme.getSchemeDes());
        return convertView;
    }
}

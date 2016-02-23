package com.pyystone.apkintentjump.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pyystone.apkintentjump.R;
import com.pyystone.apkintentjump.data.JumpHost;
import com.pyystone.apkintentjump.data.JumpScheme;

import java.util.ArrayList;

/**
 * Created by pyysotne on 2016/2/22.
 * email: pyystone@163.com
 * QQ: 862429936
 */
public class HostListAdapter extends BaseAdapter {
    private ArrayList<JumpHost> mJumpHosts;
    private LayoutInflater mLayoutInflater;
    public HostListAdapter(Context context, JumpScheme scheme) {
        mLayoutInflater = LayoutInflater.from(context);
        mJumpHosts = scheme.getHosts();
    }

    @Override
    public int getCount() {
        if (mJumpHosts == null) {
            return 0;
        }
        return mJumpHosts.size();
    }

    @Override
    public Object getItem(int position) {
        if (mJumpHosts == null) {
            return null;
        }
        return mJumpHosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (mJumpHosts == null) {
            return 0;
        }
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.host_list_item,null);
        }
        JumpHost scheme = (JumpHost) getItem(position);
        TextView tv = (TextView) convertView.findViewById(R.id.hostName);
        tv.setText(scheme.getHost());
        tv = (TextView) convertView.findViewById(R.id.hostDes);
        tv.setText(scheme.getHostDes());
        return convertView;
    }

}

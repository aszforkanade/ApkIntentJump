package com.pyystone.apkintentjump;

import android.app.Application;

import com.pyystone.apkintentjump.data.JumpDataManager;

/**
 * Created by pyysotne on 2016/2/1.
 * email: pyystone@163.com
 * QQ: 862429936
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.init(this);
        JumpDataManager.init();

    }
}

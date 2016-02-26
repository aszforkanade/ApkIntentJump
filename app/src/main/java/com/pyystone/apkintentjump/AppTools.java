package com.pyystone.apkintentjump;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by pyysotne on 2016/2/26.
 * email: pyystone@163.com
 * QQ: 862429936
 */
public class AppTools {
    private static AppTools mInstance;
    private Context mContext;
    private Toast mToast;
    private AppTools(Context context) {
        mContext = context;
    }
    public static void init(Context context) {
        mInstance = new AppTools(context);
    }
    public static AppTools getInstance() {
        return mInstance;
    }
    public void toast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext,msg,Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }
}

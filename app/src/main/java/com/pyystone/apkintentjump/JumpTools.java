package com.pyystone.apkintentjump;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.pyystone.apkintentjump.data.JumpScheme;

/**
 * Created by pyysotne on 2016/2/23.
 * email: pyystone@163.com
 * QQ: 862429936
 */
public class JumpTools {

    public static final int JUMP_REQUEST_CODE = 99;
    public static boolean isVaildScheme(Context context, JumpScheme scheme) {
        Uri uri = Uri.parse(scheme.getScheme() + "://test");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(uri);
        return context.getPackageManager().queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS).size() > 0;
    }
    public static void urlJump(Activity activity,String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse(url));
        activity.startActivityForResult(intent,JUMP_REQUEST_CODE);
        AppTools.getInstance().toast("Succeed");
    }
}

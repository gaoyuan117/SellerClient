package com.kaichaohulian.baocms.manager;

import android.content.Context;
import android.content.Intent;

import com.kaichaohulian.baocms.activity.ReloginActivity;
import com.kaichaohulian.baocms.app.AppManager;
import com.kaichaohulian.baocms.app.MyApplication;

/**
 * 页面流转中心
 */
public class UIHelper {

    public static void reloginPage(Context context) {
        AppManager.getAppManager().finishAllActivity();
        Intent reloginIntent = new Intent(context, ReloginActivity.class);
        reloginIntent.putExtra("phonenumber", MyApplication.getInstance().UserInfo.getPhoneNumber());
        reloginIntent.putExtra("avatar", MyApplication.getInstance().UserInfo.getAvatar());
        context.startActivity(reloginIntent);
        com.kaichaohulian.baocms.utils.SPUtils.put(context, "Login_UserId", "0");
        com.kaichaohulian.baocms.utils.SPUtils.put(context, "isLogin", true);
    }

}

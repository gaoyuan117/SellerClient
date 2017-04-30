package com.kaichaohulian.baocms.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.AppManager;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseEcActivity;
import com.kaichaohulian.baocms.ecdemo.common.dialog.ECProgressDialog;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferenceSettings;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferences;
import com.kaichaohulian.baocms.ecdemo.ui.SDKCoreHelper;
import com.kaichaohulian.baocms.utils.SPUtils;

import java.io.InvalidClassException;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeSettingsActivity extends BaseEcActivity {

    //    RelativeLayout idAndSafe;
//    RelativeLayout newMessageNotification;
//    RelativeLayout mPrivate;
//    RelativeLayout mNormal;
//    RelativeLayout adviceFeedback;
//    RelativeLayout aboutBuyer;
    RelativeLayout mLogout;


    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
//        idAndSafe = getId(R.id.settings_relative_id);
//        newMessageNotification = getId(R.id.setting_relative_new_message);
//        mPrivate = getId(R.id.settings_relative_private);
//        mNormal = getId(R.id.settings_relative_normal);
//        adviceFeedback = getId(R.id.settings_relative_advice);
//        aboutBuyer = getId(R.id.settings_relative_about);
        mLogout = getId(R.id.settings_relative_logout);

        setCenterTitle("设置");

        registerReceiver(new String[]{SDKCoreHelper.ACTION_LOGOUT});

    }

    private ECProgressDialog mPostingdialog;

    /**
     * 关闭对话框
     */
    private void dismissPostingDialog() {
        if (mPostingdialog == null || !mPostingdialog.isShowing()) {
            return;
        }
        mPostingdialog.dismiss();
        mPostingdialog = null;
    }

    @Override
    public void initEvent() {
//        idAndSafe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityUtil.next(getActivity(), IdAndSafeActivity.class);
//
//            }
//        });
//        newMessageNotification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityUtil.next(getActivity(), NewMessageNotificationActivity.class);
//
//            }
//        });
//        mPrivate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ActivityUtil.next(getActivity(), PrivateSettingActivity.class);
//            }
//        });
//        mNormal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityUtil.next(getActivity(), MeSettingsNormalActivity.class);
//            }
//        });
//        adviceFeedback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
//                startActivity(intent);
//            }
//        });
//        aboutBuyer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ActivityUtil.next(getActivity(), AboutBuyerActivity.class);
//            }
//        });
        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogout();
            }
        });


    }

    public void handleLogout() {
        SDKCoreHelper.logout(false);
        mPostingdialog = new ECProgressDialog(this, R.string.logout_hint);
        mPostingdialog.show();
    }

    @Override
    protected void handleReceiver(Context context, Intent intent) {
        super.handleReceiver(context, intent);

        if (SDKCoreHelper.ACTION_LOGOUT.equals(intent.getAction())) {
            dismissPostingDialog();
            //TODO 2017-03-11修复登陆db 空指针异常
            try {
                ECPreferences.savePreference(ECPreferenceSettings.SETTINGS_REGIST_AUTO, "", true);
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }

            AppManager.getAppManager().finishAllActivity();
            Intent reloginIntent = new Intent(MeSettingsActivity.this, ReloginActivity.class);
            reloginIntent.putExtra("phonenumber", MyApplication.getInstance().UserInfo.getPhoneNumber());
            reloginIntent.putExtra("avatar", MyApplication.getInstance().UserInfo.getAvatar());
            startActivity(reloginIntent);
            SPUtils.put(getActivity(), "Login_UserId", "0");
            SPUtils.put(getActivity(), "isLogin", false);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.me_settings;
    }

    @Override
    public int getTitleLayout() {
        return -1;
    }


    @OnClick({R.id.setting_change_paypassword, R.id.setting_change_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_change_paypassword:
                //TODO 修改支付密码
                break;
            case R.id.setting_change_password:
                //TODO 修改密码
                break;
        }
    }
}

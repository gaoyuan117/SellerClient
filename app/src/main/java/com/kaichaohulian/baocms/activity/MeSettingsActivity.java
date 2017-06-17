package com.kaichaohulian.baocms.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.AppManager;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseEcActivity;
import com.kaichaohulian.baocms.ecdemo.common.dialog.ECProgressDialog;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferenceSettings;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferences;
import com.kaichaohulian.baocms.ecdemo.ui.SDKCoreHelper;
import com.kaichaohulian.baocms.entity.UserInfoBean;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.utils.SPUtils;

import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeSettingsActivity extends BaseEcActivity {

    TextView tvAdd;

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);

        setCenterTitle("设置");

        registerReceiver(new String[]{SDKCoreHelper.ACTION_LOGOUT});

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserInfoPhone();

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
        tvAdd = (TextView) findViewById(R.id.tv_set_add);

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


    @OnClick({R.id.setting_set_paypassword, R.id.setting_change_paypassword, R.id.setting_forget_paypassword, R.id.setting_change_password, R.id.settings_relative_logout, R.id.settings_addfriend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting_change_paypassword:
                ActivityUtil.next(getActivity(), changPayWordActivity.class);
                break;
            case R.id.setting_forget_paypassword:
                ActivityUtil.next(getActivity(), forgetPayWordActivity.class);
                break;
            case R.id.setting_change_password:
                ActivityUtil.next(getActivity(), changPwdActivity.class);
                break;
            case R.id.settings_relative_logout:
                handleLogout();
                break;
            case R.id.settings_addfriend:
                ActivityUtil.next(getActivity(), AddfriendPay.class);
                break;
            case R.id.setting_set_paypassword:
                ActivityUtil.next(getActivity(), Setpayword.class);
                break;
        }
    }

    /**
     * 加载用户基本信息
     */
    private void loadUserInfoPhone() {
        Map<String, String> userMap = new HashMap<>();
        userMap.put("phoneNumber", MyApplication.getInstance().UserInfo.getPhoneNumber());
        userMap.put("id", MyApplication.getInstance().UserInfo.getUserId() + "");
        RetrofitClient.getInstance().createApi().loadUserInfoPhone(userMap)
                .compose(RxUtils.<HttpResult<UserInfoBean>>io_main())
                .subscribe(new BaseObjObserver<UserInfoBean>(this, "加载中", false) {
                    @Override
                    protected void onHandleSuccess(UserInfoBean userInfoBean) {
                        tvAdd.setText(userInfoBean.getAddPay() + "元");
                    }
                });
    }
}

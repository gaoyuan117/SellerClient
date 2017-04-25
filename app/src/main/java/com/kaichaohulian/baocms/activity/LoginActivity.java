package com.kaichaohulian.baocms.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kaichaohulian.baocms.MainActivity;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseEcActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.dialog.ECProgressDialog;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferenceSettings;
import com.kaichaohulian.baocms.ecdemo.common.utils.PermissionUtils;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.ecdemo.core.ClientUser;
import com.kaichaohulian.baocms.ecdemo.core.ContactsCache;
import com.kaichaohulian.baocms.ecdemo.storage.ContactSqlManager;
import com.kaichaohulian.baocms.ecdemo.ui.SDKCoreHelper;
import com.kaichaohulian.baocms.ecdemo.ui.contact.ContactLogic;
import com.kaichaohulian.baocms.ecdemo.ui.contact.ECContacts;
import com.kaichaohulian.baocms.entity.GroupEntity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.manager.SPContent;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.SPUtils;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.SdkErrorCode;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InvalidClassException;
import java.util.ArrayList;

/**
 * 登录界面
 * Created by ljl on 2016/12/11.
 */
public class LoginActivity extends BaseEcActivity {
    private EditText et_usertel, et_password;
    private Button btn_login;
    private View btn_qtlogin, forgetPwd;
    private TextView iv_back;

    private DataHelper mDataHelper;
    ECInitParams.LoginAuthType mLoginAuthType = ECInitParams.LoginAuthType.NORMAL_AUTH;
    private ECProgressDialog mPostingdialog;

//    @Override
//    public void setContent() {
////        setContentView(R.layout.login_activity);
//    }

    @Override
    public void initData() {
        mDataHelper = new DataHelper(getActivity());

        registerReceiver(new String[]{SDKCoreHelper.ACTION_SDK_CONNECT});

        PermissionUtils.requestMultiPermissions(this, mPermissionGrant);
    }

    @Override
    public void initView() {
        et_usertel = getId(R.id.et_usertel);
        et_password = getId(R.id.et_pwd);
        btn_login = getId(R.id.btn_login);
        btn_qtlogin = getId(R.id.btn_qtlogin);
        forgetPwd = getId(R.id.forgetPwd);
        iv_back = getId(R.id.iv_back);
    }

    @Override
    public void initEvent() {
        // 监听多个输入框
        et_usertel.addTextChangedListener(new TextChange());
        et_password.addTextChangedListener(new TextChange());
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_login.setEnabled(true);

        btn_qtlogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(LoginActivity.this, RegisterActivity.class);
            }
        });
        forgetPwd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(LoginActivity.this, ForgetPwdActivity.class);
            }
        });
        btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog.showDialog(getActivity(), "登录中...", false, null);
                final String phone = et_usertel.getText().toString().trim();
                final String password = et_password.getText().toString().trim();
                RequestParams params = new RequestParams();
                params.put("phoneNumber", phone);
                params.put("password", password);
                params.put("userPositionX", MyApplication.getInstance().BDLocation == null ? "30.67" : MyApplication.getInstance().BDLocation.getLatitude());
                params.put("userPositionY", MyApplication.getInstance().BDLocation == null ? "104.06" : MyApplication.getInstance().BDLocation.getLongitude());
                HttpUtil.post(Url.signIn, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            DBLog.e("登录：", response.toString());
                            if (response.getInt("code") == 0) {
                                getUserInfo(phone);
                            }else{
                                showToastMsg(response.getString("errorDescription"));
                            }
                        } catch (Exception e) {
                            showToastMsg("登录失败");
                            e.printStackTrace();
                        } finally {
                            ShowDialog.dissmiss();
                        }
                    }

                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        showToastMsg("请求服务器失败");
                        DBLog.e("tag", statusCode + ":" + responseString);
                        ShowDialog.dissmiss();
                    }
                });
            }

        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity;
    }

    // EditText监听器
    class TextChange implements TextWatcher {
        @Override
        public void afterTextChanged(Editable arg0) {
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before, int count) {
            boolean Sign2 = et_usertel.getText().length() >= 11;
            boolean Sign3 = et_password.getText().length() >= 6;
            if (Sign2 & Sign3) {
                btn_login.setEnabled(true);
            } else {
                btn_login.setEnabled(false);
            }
        }
    }

    public UserInfo userInfo;

    public void getUserInfo(final String phone) {
        RequestParams params = new RequestParams();
        params.put("phoneNumber", phone);
        HttpUtil.post(Url.dependPhoneGetUserInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("登录：", response.toString());
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
                        UserInfo UserInfo = new UserInfo();
                        String usertoken = response.getString("token");
                        UserInfo.setToken(usertoken);
                        SPUtils.put(getActivity(), SPContent.USER_TOKEN, usertoken);
                        UserInfo.setUserId(response.getInt("id"));
                        UserInfo.setCreatedTime(System.currentTimeMillis() + "");
                        UserInfo.setLocked(false);
                        UserInfo.setLastModifiedTime(System.currentTimeMillis() + "");
                        UserInfo.setLastModifier("LastModifier");
                        UserInfo.setUsername(response.getString("username"));
                        UserInfo.setPassword(response.getString("password"));
                        UserInfo.setAccountNumber(response.getString("accountNumber"));
                        UserInfo.setQrCode(response.getString("qrCode"));
                        UserInfo.setDistrictId(response.getString("districtId"));
                        UserInfo.setSex(response.getString("sex"));
                        UserInfo.setThermalSignatrue(response.getString("thermalSignatrue"));
                        UserInfo.setPhoneNumber(response.getString("phoneNumber"));
                        UserInfo.setUserEmail(response.getString("userEmail"));
                        UserInfo.setBalance(response.getString("balance"));
                        UserInfo.setAvatar(response.getString("avatar"));
                        UserInfo.setBackAvatar(response.getString("backAvatar"));
                        UserInfo.setLoginFailedCount(0);
                        UserInfo.setPayPassword(response.getString("paypassword"));
//                        UserInfo.setAge(response.getInt("age"));
                        UserInfo.setJob(response.getString("job"));
                        UserInfo.setHobby(response.getString("hobby"));

//                      UserInfo.setImages(response.getString("images"));
                        MyApplication.getInstance().UserInfo = UserInfo;
                        mDataHelper.SaveUserInfo(UserInfo);
                        SPUtils.put(getActivity(), "Login_UserId", UserInfo.getUserId() + "");
                        SPUtils.put(getActivity(), "isLogin", true);

                        getData();

                        userInfo = UserInfo;
                        ECPreferenceSettings appKey = ECPreferenceSettings.SETTINGS_APPKEY;
                        ECPreferenceSettings token = ECPreferenceSettings.SETTINGS_TOKEN;
                        ClientUser clientUser = new ClientUser(userInfo.getPhoneNumber());
                        clientUser.setAppKey((String) appKey.getDefaultValue());
                        clientUser.setAppToken((String) token.getDefaultValue());
                        clientUser.setLoginAuthType(mLoginAuthType);
                        clientUser.setPassword("");
                        CCPAppManager.setClientUser(clientUser);

//			if(!PatternUtils.isShuZiYing(mobile)){
//				ToastUtil.showMessage("输入的账号不合法");
//				return;
//			}
                        mPostingdialog = new ECProgressDialog(LoginActivity.this, R.string.login_posting);
                        mPostingdialog.show();

                        SDKCoreHelper.init(LoginActivity.this, ECInitParams.LoginMode.FORCE_LOGIN);
                    } else {
                        showToastMsg(response.getString("errorDescription"));
                    }
                } catch (Exception e) {
                    showToastMsg("登录失败");
                    e.printStackTrace();
                } finally {
                    ShowDialog.dissmiss();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_MULTI_PERMISSION:
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void handleReceiver(Context context, Intent intent) {
        // super.handleReceiver(context, intent);
        int error = intent.getIntExtra("error", -1);
        if (SDKCoreHelper.ACTION_SDK_CONNECT.equals(intent.getAction())) {
            // 初始注册结果，成功或者失败
            if (SDKCoreHelper.getConnectState() == ECDevice.ECConnectState.CONNECT_SUCCESS && error == SdkErrorCode.REQUEST_SUCCESS) {
                dismissPostingDialog();
                try {
                    saveAccount();
                } catch (InvalidClassException e) {
                    e.printStackTrace();
                }
                ContactsCache.getInstance().load();
                ActivityUtil.next(getActivity(), MainActivity.class);
                finish();
                return;
            }
            if (intent.hasExtra("error")) {
                if (SdkErrorCode.CONNECTTING == error) {
                    return;
                }
                if (error == -1) {
                    ToastUtil.showMessage("请检查登陆参数是否正确[" + error + "]");
                } else {
                    dismissPostingDialog();
                }
//                ToastUtil.showMessage("登录失败，请稍后重试[" + error + "]    ");
            }
            dismissPostingDialog();
        }
    }

    private void saveAccount() throws InvalidClassException {
        if (userInfo == null) return;
//        String appKey = appkeyEt.getText().toString().trim();
//        String token = tokenEt.getText().toString().trim();
//        String mobile = mobileEt.getText().toString().trim();
//        String voippass = mVoipEt.getText().toString().trim();
        ClientUser user = CCPAppManager.getClientUser();
        if (user == null) {
            user = new ClientUser(userInfo.getPhoneNumber() + "");
        } else {
            user.setUserId(userInfo.getPhoneNumber() + "");
        }
        ECPreferenceSettings appKey = ECPreferenceSettings.SETTINGS_APPKEY;
        ECPreferenceSettings token = ECPreferenceSettings.SETTINGS_TOKEN;
        user.setAppKey((String) appKey.getDefaultValue());
        user.setAppToken((String) token.getDefaultValue());

        SharedPrefsUtil.putValue(getActivity(), userInfo.getPhoneNumber(), userInfo.getAvatar() + "-x-" + userInfo.getUsername());
        SharedPrefsUtil.putValue(getActivity(), userInfo.getUserId() + "", userInfo.getAvatar() + "-x-" + userInfo.getUsername());

        user.setLoginAuthType(mLoginAuthType);
        CCPAppManager.setClientUser(user);
        Log.d("BUGTRACE", "LoginAty-----saveAccount : " + user.toString());
        SharedPrefsUtil.putValue(getActivity(), ECPreferenceSettings.SETTINGS_REGIST_AUTO.getId(), user.toString());
        // ContactSqlManager.insertContacts(contacts);
        ArrayList<ECContacts> objects = ContactLogic.initContacts();
        objects = ContactLogic.converContacts(objects);
        ContactSqlManager.insertContacts(objects);
    }

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
    protected boolean isEnableSwipe() {
        return false;
    }

    @Override
    public boolean isEnableRightSlideGesture() {
        return false;
    }

    @Override
    public int getTitleLayout() {
        return -1;
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        HttpUtil.post(Url.groups_all, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取我加入和我创建的群", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray jsonArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            GroupEntity GroupEntity = new GroupEntity();
                            JSONArray avatarArray = jsonObject.getJSONArray("images");
                            SharedPrefsUtil.putValue(getActivity(), jsonObject.getString("chatGroupId"), avatarArray.toString()
                                    + "-x-" + jsonObject.getString("name") + "-x-" + jsonObject.getInt("id"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ShowDialog.dissmiss();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        });
    }

}

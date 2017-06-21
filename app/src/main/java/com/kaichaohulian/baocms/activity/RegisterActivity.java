package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.AppManager;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 注册页
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {
    private EditText et_usertel, et_code, et_pwd;
    private Button btn_register;
    private TextView iv_back;
    private TextView mTvGetCode;
    private String phone;
    private Timer mTimer;
    private int mTime = 60;


    @Override
    public void setContent() {
        setContentView(R.layout.register_activity);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        et_usertel = (EditText) findViewById(R.id.et_usertel);
        et_code = (EditText) findViewById(R.id.et_register_code);
        et_pwd = (EditText) findViewById(R.id.et_register_pwd);
        btn_register = (Button) findViewById(R.id.btn_register);
        iv_back = (TextView) findViewById(R.id.iv_back);
        mTvGetCode = (TextView) findViewById(R.id.tv_register_code);

    }

    @Override
    public void initEvent() {
        mTvGetCode.setOnClickListener(this);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_usertel.addTextChangedListener(new TextChange());

        btn_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register_code:
                getCode();
                break;
        }
    }

    private void register() {
        String code = et_code.getText().toString();
        final String pwd = et_pwd.getText().toString();
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showMessage("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtil.showMessage("请输入密码");
            return;
        }
        ShowDialog.showDialog(getActivity(), "注册中...", false, null);
        RequestParams params = new RequestParams();
        params.put("phoneNumber", phone);
        params.put("password", pwd);
        params.put("code", code);
        HttpUtil.post(Url.signUp, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("注册:", response.toString());
                    if (response.getInt("code") == 0) {
                        AppManager.getAppManager().finishAllActivity();
                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                        intent.putExtra("id",phone);
                        intent.putExtra("password",pwd);
                        intent.putExtra("isfirst",true);
                        startActivity(intent);
                        finish();
//                        ActivityUtil.next(getActivity(), LoginActivity.class);
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("注册失败");
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

    private void getCode() {
        if (mTimer == null) {
            mTimer = new Timer();
            mTime = 60;
            mTvGetCode.setText(mTime + "s后重新获取");
            mTvGetCode.setEnabled(false);
        } else {
            return;
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        mTime--;
                        mTvGetCode.setText(mTime + "s后重新获取");
                        if (mTime == 0) {
                            mTimer.cancel();
                            mTimer = null;
                            mTvGetCode.setText("重新验证");
                            mTvGetCode.setEnabled(true);
                        }
                    }
                });
            }
        }, 0, 1000);
        ShowDialog.showDialog(getActivity(), "发送中...", false, null);
        phone = et_usertel.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtil.showMessage("请输入手机号");
            return;
        }
        RequestParams params = new RequestParams();
        params.put("phoneNumber", phone);
        HttpUtil.post(Url.sendMessage, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("验证码", response.toString());
                    if (response.getInt("code") == 0) {
//                        Bundle Bundle = new Bundle();
//                        Bundle.putString("phoneNumber", phone);
//                        ActivityUtil.next(getActivity(), RegisterActivity2.class, Bundle);
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("发送失败");
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

    // EditText监听器
    class TextChange implements TextWatcher {
        @Override
        public void afterTextChanged(Editable arg0) {
        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {
            boolean Sign2 = et_usertel.getText().length() >= 11;
            if (Sign2) {
                btn_register.setEnabled(true);
            } else {
                btn_register.setEnabled(false);
            }
        }

    }

    public void back(View view) {
        finish();
    }

}

package com.kaichaohulian.baocms.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
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
 * 忘记密码界面
 * Created by ljl on 2016/12/11.
 */
public class ForgetPwdActivity extends BaseActivity {
    private EditText et_usertel, et_code, et_pwd, et_newPwd;
    private TextView iv_back;
    private Button btn_register;
    private Timer mTimer = null;
    private int mTime = 60;
    private TextView get_code;

    @Override
    public void setContent() {
        setContentView(R.layout.forgetpwd_activity);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        et_usertel = getId(R.id.et_usertel);
        et_code = getId(R.id.et_code);
        et_pwd = getId(R.id.et_pwd);
        et_newPwd = getId(R.id.et_newPwd);
        iv_back = getId(R.id.iv_back);

        btn_register = getId(R.id.btn_register);
        get_code = getId(R.id.get_code);
    }

    @Override
    public void initEvent() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_usertel.addTextChangedListener(new TextChange());
        et_code.addTextChangedListener(new TextChange());
        et_pwd.addTextChangedListener(new TextChange());
        et_newPwd.addTextChangedListener(new TextChange());

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog.showDialog(getActivity(), "修改中...", false, null);

                String usertel = et_usertel.getText().toString().trim();
                String code = et_code.getText().toString().trim();
                String pwd = et_pwd.getText().toString().trim();
                String newPwd = et_newPwd.getText().toString().trim();

                RequestParams params = new RequestParams();
                params.put("phoneNumber", usertel);
                params.put("code", code);
                params.put("password", pwd);
                params.put("newPassword", newPwd);
                HttpUtil.post(Url.forgetPassword, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {

                            DBLog.e("修改密码：", response.toString());
                            showToastMsg(response.getString("errorDescription"));
                            if(response.getString("code").equals("0")){
                                finish();
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
                        showToastMsg("请求服务器失败");
                        DBLog.e("tag", statusCode + ":" + responseString);
                        ShowDialog.dissmiss();
                    }
                });
            }
        });
        get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTimer == null) {
                    mTimer = new Timer();
                    mTime = 60;
                    get_code.setText(mTime + "s后重新获取");
                    get_code.setEnabled(false);
                } else {
                    return;
                }
                mTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                mTime--;
                                get_code.setText(mTime + "s后重新获取");
                                if (mTime == 0) {
                                    mTimer.cancel();
                                    mTimer = null;
                                    get_code.setText("重新验证");
                                    get_code.setEnabled(true);
                                }
                            }
                        });
                    }
                }, 0, 1000);
                String phone = et_usertel.getText().toString().trim();
                ShowDialog.showDialog(getActivity(), "发送中...", false, null);
                RequestParams params = new RequestParams();
                params.put("phoneNumber", phone);
                HttpUtil.post(Url.sendMessage, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            DBLog.e("发送验证码：", response.toString());
                            showToastMsg(response.getString("errorDescription"));
                        } catch (Exception e) {
                            showToastMsg("发送失败");
                            mTimer.cancel();
                            mTimer = null;
                            get_code.setText("重新验证");
                            get_code.setEnabled(true);
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
            boolean Sign3 = et_pwd.getText().length() >= 6;
            boolean Sign4 = et_code.getText().length() >= 6;
            boolean Sign5 = et_newPwd.getText().length() >= 6;
            if (mTimer == null) {
                if (Sign2) {
                    get_code.setEnabled(true);
                } else {
                    get_code.setEnabled(false);
                }
            }
            if (Sign2 & Sign3 & Sign4 & Sign5) {
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

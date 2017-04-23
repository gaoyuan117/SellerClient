package com.kaichaohulian.baocms.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Editable;
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
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 注册页
 */
public class RegisterActivity3 extends BaseActivity {
    private TextView et_usertel, iv_back;
    private Button btn_register;
    private EditText et_code;
    private String phoneNumber, code;

    @Override
    public void setContent() {
        setContentView(R.layout.register3_activity);
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            Intent intent = getIntent();
            phoneNumber = intent.getStringExtra("phoneNumber");
            code = intent.getStringExtra("code");
        }
    }

    @Override
    public void initView() {
        et_usertel = getId(R.id.et_usertel);
        et_code = getId(R.id.et_code);
        btn_register = getId(R.id.btn_register);
        iv_back = getId(R.id.iv_back);
        et_code.addTextChangedListener(new TextChange());
        et_usertel.setText(phoneNumber + "");
    }

    @Override
    public void initEvent() {
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_register.setOnClickListener(new OnClickListener() {

            @SuppressLint("SdCardPath")
            @Override
            public void onClick(View v) {
                ShowDialog.showDialog(getActivity(), "注册中...", false, null);
                final String pwd = et_code.getText().toString().trim();
                RequestParams params = new RequestParams();
                params.put("phoneNumber", phoneNumber);
                params.put("password", pwd);
                params.put("code", code);
                HttpUtil.post(Url.signUp, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            DBLog.e("注册:", response.toString());
                            if (response.getInt("code") == 0) {
                                AppManager.getAppManager().finishAllActivity();
                                ActivityUtil.next(getActivity(), LoginActivity.class);
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
        });
    }


    // EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {
            boolean Sign2 = et_code.getText().length() >= 4;
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

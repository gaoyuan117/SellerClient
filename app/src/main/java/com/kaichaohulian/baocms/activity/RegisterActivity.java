package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
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
public class RegisterActivity extends BaseActivity {
    private EditText et_usertel;
    private Button btn_register;
    private TextView iv_back;

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
        btn_register = (Button) findViewById(R.id.btn_register);
        iv_back = (TextView) findViewById(R.id.iv_back);
    }

    @Override
    public void initEvent() {
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
                ShowDialog.showDialog(getActivity(), "发送中...", false, null);
                final String phone = et_usertel.getText().toString().trim();
                RequestParams params = new RequestParams();
                params.put("phoneNumber", phone);
                HttpUtil.post(Url.sendMessage, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            DBLog.e("验证码", response.toString());
                            if (response.getInt("code") == 0) {
                                Bundle Bundle = new Bundle();
                                Bundle.putString("phoneNumber", phone);
                                ActivityUtil.next(getActivity(), RegisterActivity2.class, Bundle);
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

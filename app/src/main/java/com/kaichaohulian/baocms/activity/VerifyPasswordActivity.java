package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.View;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.MD5;
import com.jungly.gridpasswordview.GridPasswordView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class VerifyPasswordActivity extends BaseActivity {

    GridPasswordView gridPasswordView;
    public static final int RESULT_FAILED = 12;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_verifypassword);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        gridPasswordView = getId(R.id.pswView);
        setCenterTitle("验证密码");
    }

    @Override
    public void initEvent() {
        gridPasswordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
            }

            @Override
            public void onInputFinish(String psw) {
                String inputPsd = psw;
                MD5 md5 = new MD5();
                String inputMd5 = md5.getMD5ofStr(inputPsd);
                verifyPassword(inputMd5);
            }
        });
    }

    public void verifyPassword(String psd) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("password", psd);
        HttpUtil.post(Url.verificatPassword, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("验证密码", response.toString());
//                    if (response.getInt("code") == 0) {
                    showToastMsg("验证密码成功");
                    setResult(RESULT_OK, new Intent());
                    finish();
//                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("验证密码，解析json失败");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }

    public void back(View view) {
        finish();
    }

}

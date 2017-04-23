package com.kaichaohulian.baocms.activity;

import android.content.Intent;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.MD5;
import com.jungly.gridpasswordview.GridPasswordView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class SetPasswordActivity extends BaseActivity {

    private GridPasswordView passwordView;
    private String userInputPsd;

    private DataHelper mDataHelper;

    @Override
    public void setContent() {
        setContentView(R.layout.set_password_layout);
    }

    @Override
    public void initData() {
        mDataHelper = new DataHelper(getActivity());
    }

    @Override
    public void initView() {
        setCenterTitle("设置密码");
        passwordView = getId(R.id.pswView);
    }

    @Override
    public void initEvent() {
        passwordView.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                userInputPsd = psw;
                MD5 md5 = new MD5();
                String inputMd5 = md5.getMD5ofStr(userInputPsd);
                uploadUserPassword(inputMd5);

            }
        });

    }

    public void uploadUserPassword(final String psw) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("password", psw);
        HttpUtil.post(Url.setPayPassword, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("验证密码", response.toString());
                    if (response.getInt("code") == 0) {
                        showToastMsg("设置密码成功");
                        MyApplication.getInstance().UserInfo.setPayPassword(psw);
                        mDataHelper.UpdateUserInfo(MyApplication.getInstance().UserInfo);
                        setResult(RESULT_OK, new Intent());
                        finish();
                    }
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
}

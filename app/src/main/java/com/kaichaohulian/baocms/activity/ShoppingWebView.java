package com.kaichaohulian.baocms.activity;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.manager.SPContent;
import com.kaichaohulian.baocms.manager.UIHelper;
import com.kaichaohulian.baocms.manager.Validator;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.SPUtils;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class ShoppingWebView extends BaseActivity {

    WebView webView;
    private String storeUrl;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_shopping);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        webView = getId(R.id.webview);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSaveFormData(true);
        updateUserCache(this);
    }

    private void validateUsreInfo(String usertoken) {
        LogUtil.e("TRACE", "update user token : " + usertoken);
        String phoneNumber = MyApplication.getInstance().UserInfo.getPhoneNumber();
        storeUrl = "http://mjw.qdhdn.cn/mobile/passport/applogin.html?username=" + phoneNumber + "&token=" + usertoken;
        Log.e("TRACE", "shopping store url : " + storeUrl);
        webView.loadUrl(storeUrl);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void initEvent() {
    }

    public void updateUserCache(final Context context) {
        RequestParams params = new RequestParams();
        params.put("phoneNumber", MyApplication.getInstance().UserInfo.getPhoneNumber());
        HttpUtil.post(Url.dependPhoneGetUserInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    LogUtil.e("TRACE", " update user info " + response.toString());
                    DBLog.e("update Cache：", response.toString());
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
                        UserInfo UserInfo = new UserInfo();
                        UserInfo.setUserId(response.getInt("id"));
                        String usertoken = response.getString("token");
                        SPUtils.put(context, SPContent.USER_TOKEN, usertoken);
                        validateUsreInfo(usertoken);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }
}

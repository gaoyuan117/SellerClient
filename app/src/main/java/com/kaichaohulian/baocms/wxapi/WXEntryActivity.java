package com.kaichaohulian.baocms.wxapi;

import android.content.Intent;
import android.util.Log;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    public void setContent() {

    }

    @Override
    public void initData() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(getActivity(), Url.WX_APP_ID, false);
        api.registerApp(Url.WX_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//                goToGetMsg();
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//                goToShowMsg((ShowMessageFromWX.Req) req);
                break;
            default:
                break;
        }
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        String codeSendAuth = "";
        int result = 0;
        if (resp instanceof SendMessageToWX.Resp) {
//            String codeSendMessageToWX = resp.toString();
        } else {
            codeSendAuth = ((SendAuth.Resp) resp).code;
        }
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                DBLog.showToast("操作成功", getActivity());
                ShowDialog.showDialog(getActivity(), "登录中...", false, null);
                RequestParams params = new RequestParams();
                params.put("appid", Url.WX_APP_ID);
                params.put("secret", Url.WX_APP_SECRET);
                params.put("code", codeSendAuth);
                params.put("grant_type", "authorization_code");
                HttpUtil.post(Url.WX_TOKEN, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            RequestParams params = new RequestParams();
                            params.put("access_token", response.getString("access_token"));
                            params.put("openid", response.getString("openid"));
                            HttpUtil.post(Url.WE_CHAT_LOGIN, params, new JsonHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                    Log.e("TAG", "ssss:  " + response.toString());
                                    try {
                                        if (response.getInt("state") == 1) {// 成功
                                        } else if (response.getInt("state") == 0) {// 失败原因
                                            JSONObject jsonObject = response.getJSONObject("rep");
                                            String errCode = jsonObject.getString("errMsg");
                                            DBLog.showToast(errCode, getActivity());
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFinish() {
                                    ShowDialog.dissmiss();
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                                    DBLog.showToast("当前无网络连接，请检查网络设置", getActivity());
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFinish() {
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        DBLog.showToast("当前无网络连接，请检查网络设置", getActivity());
                        ShowDialog.dissmiss();
                    }
                });
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                DBLog.showToast("操作取消", getActivity());
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                DBLog.showToast("操作被拒绝", getActivity());
                finish();
                break;
            default:
                result = R.string.errcode_unknown;
                finish();
                break;
        }
//        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        this.finish();
    }

    @Override
    protected void onDestroy() {
        ShowDialog.dissmiss();
        super.onDestroy();
    }
}
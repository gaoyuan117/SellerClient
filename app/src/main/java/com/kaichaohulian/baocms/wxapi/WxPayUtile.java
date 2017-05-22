package com.kaichaohulian.baocms.wxapi;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.manager.UIHelper;
import com.kaichaohulian.baocms.manager.Validator;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class WxPayUtile {

    private Context context;
    PayReq req;
    final IWXAPI msgApi;
    Map<String, String> resultunifiedorder;
    StringBuffer sb;
    private String total_fee;
    private String notify_url;
    private String body;
    private String outTradNo;

    public static Handler chathandler;
    private String actualamount;
    private String entity;
    private String pay_type;

    public WxPayUtile(Context context, String total_fee, String notify_url, String body, String outTradNo, String actualamount,String pay_type) {
        super();
        msgApi = WXAPIFactory.createWXAPI(context, null);
        req = new PayReq();
        msgApi.registerApp(Url.WX_APP_ID);
        sb = new StringBuffer();
        this.context = context;
        this.total_fee = total_fee;
        this.notify_url = notify_url;
        this.body = body;
        this.outTradNo = outTradNo;
        this.actualamount = actualamount;
        this.pay_type = pay_type;
    }

    public static WxPayUtile getInstance(Context context, String total_fee, String notify_url
            , String body, String outTradNo, String actualamount,String pay_type) {
        return new WxPayUtile(context, total_fee, notify_url, body, outTradNo, actualamount,pay_type);
    }

    public void doPay(Handler chathandler) {
        this.chathandler = chathandler;
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("body", body);
        params.put("subject", body);
        params.put("amount", total_fee);
        params.put("payType",pay_type);
        params.put("phoneNumber", MyApplication.getInstance().UserInfo.getPhoneNumber());
        if (actualamount != null && !TextUtils.isEmpty(actualamount)) {
            params.put("actualamount", actualamount);
        }
        params.put("token", MyApplication.getInstance().UserInfo.getToken());
        ToastUtil.showMessage("微信支付："+total_fee);
        HttpUtil.post(Url.weixin_gotopay, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response != null) {
                        if (Validator.isTokenIllegal(response.getString("errorDescription"))) {
                            UIHelper.reloginPage(context);
                            return;
                        }
                    }
                    DBLog.e("微信充值：", response.toString());
                    if (response.getInt("code") == 0) {
                        entity = response.getString("dataObject");
                        Map<String, String> xml = decodeXml(entity);
                        Set<Map.Entry<String, String>> entries = xml.entrySet();
                        for (Map.Entry<String, String> entry : entries) {
                            LogUtil.e("TRACE", "KEY : " + entry.getKey() + "   value : " + entry.getValue());
                        }
                        req.appId = Url.WX_APP_ID;
                        req.partnerId = xml.get("partnerid");
                        req.prepayId = xml.get("prepayid");
                        req.packageValue = "Sign=WXPay";
                        req.nonceStr = xml.get("noncestr");
                        req.timeStamp = xml.get("timestamp");
                        req.sign = xml.get("sign");
                        sendPayReq();
                    }
                } catch (Exception e) {
                    DBLog.showToast("微信充值创建订单失败", context);
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
                DBLog.showToast("请求服务器失败", context);
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }

    private String genAppSign(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        LogUtil.d("TRACE", "orginal content : " + sb.toString());
        sb.append("key=");
        sb.append(Url.WX_APP_KEY);
        LogUtil.d("TRACE", "orginal content whit key: " + sb.toString());

        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        LogUtil.d("TRACE", "MD5 content whit key: " + appSign);

        return appSign;
    }

    public Map<String, String> decodeXml(String content) {
        try {
            Map<String, String> xml = new HashMap<>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }
            return xml;
        } catch (Exception e) {
            Log.e("orion-e--->", e.toString());
        }
        return null;

    }

    private void sendPayReq() {
        msgApi.registerApp(Url.WX_APP_ID);
        msgApi.sendReq(req);
    }

}

package com.kaichaohulian.baocms.paytreasure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.manager.UIHelper;
import com.kaichaohulian.baocms.manager.Validator;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PayTreasureUtils {
    // 商户PID
    public static final String PARTNER = "2016112703389808";
    // 商户收款账号
    public static final String SELLER = "xiaozhuzaixian1@163.com";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "123456789012345678901234567890AB";

    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoG" +
            "BAMXHMJH03G3vvHlwjlZxijvYYqKMzBWD7H97s5TDkOkJCuXkpWLMJ" +
            "av4R1ixebmn4OCICeDJwLM5+UQ2/DZjLo6mrEm7WMgoHmRYBWUvpz8" +
            "GLs3+FpBse8o5yIHpQTazis6o0I7ZC7Q/18kUl0j5F//+M0cUmUGdd" +
            "Jx0NPu7IcmPAgMBAAECgYAOAKxPqSZyIicV8gIh3KgM1KUnHRDV9SE" +
            "pZXixb6cy2JEM9cM/23RPWKS0gtw7aMdjHj6Uy7P4/IHJcpsovpztH" +
            "MMWUK/iyVLomPjqPfR5XVWF/bFwKNFoBg2nawZwK3WAldI1Ry1eJbt" +
            "bN+q3Ay1QgvDb3y6Y0y+Y+pzUs8LXAQJBAOEFTgU4UllDlOeXXml3a" +
            "5TftwpF7zlUgnhnxDKlDSIEfdGA/svlmovL4jHFGhlZYPpcY7aecyz" +
            "zXXKDan9qOC0CQQDhAbzTzGgO+13KY0GKOuXhewtMkE6Jk9Ar4OFtd" +
            "ZYLCEyvbrrJ6cdvjLtzRB43yCDENIas2bPbvJpADId9ggIrAkAwLqO" +
            "bKIkxhfAPevEDYkfN8+1MrNbhjr/1KjrvoTnjq9SJFLb6T+q8vmZrw" +
            "8mn3eSPF1NemiUNXfsHdy5JpCPtAkEAk/pNkkmDQh8kvr7qUpuLpfA" +
            "DLVZWoHpa1RCvkQpdJGJP9KxxIeui4wT+Hr901CDFE+SrkbAXbnMqS" +
            "ucykGNZQwJBAL6IzvUXk6eHozymy4H//jx5VuKcAKSd8e82PqZHOZj" +
            "aM3mHz+hxN+i1rWlPiaO5XqltsVEbgMklo894G8Pca9c=";

    public static final int SDK_PAY_FLAG = 1;

    private Activity mContext;
    // 订单号
    private String key;
    // 商品name
    private String name;
    // 商品详细描述
    private String body;
    // 价格 0.01 无需乘100
    private String money;
    private String actualamount;

    public PayTreasureUtils(Activity mContext, String key, String name, String body, String money, String actualamount) {
        this.mContext = mContext;
        this.key = key;
        this.name = name;
        this.body = body;
        this.money = money;
        this.actualamount = actualamount;
    }

    public static PayTreasureUtils getInstance(Activity mContext, String key, String name, String body, String money, String actualamount) {
        return new PayTreasureUtils(mContext, key, name, body, money, actualamount);
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(final Handler mHandler) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(mContext).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                        }
                    }).show();
            return;
        }

        ShowDialog.showDialog(mContext, "支付中...", false, null);
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("body", body);
        params.put("subject", name);
        params.put("amount", money);
        params.put("phoneNumber", MyApplication.getInstance().UserInfo.getPhoneNumber());
        if (actualamount != null && !TextUtils.isEmpty(actualamount)) {
            params.put("actualamount", actualamount);
        }
        params.put("token", MyApplication.getInstance().UserInfo.getToken());
        HttpUtil.post(Url.alipay_gotopay, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response != null) {
                        if (Validator.isTokenIllegal(response.getString("errorDescription"))) {
                            UIHelper.reloginPage(mContext);
                            return;
                        }
                    }
                    DBLog.e("支付宝充值：", response.toString());
                    if (response.getInt("code") == 0) {
//						String orderInfo = getOrderInfo(name, body, money);
//						String orderInfo = "app_id=2017010204807029&biz_content=%7B%22total_amount%22%3A%229.9%22%2C%22body%22%3A%22iphone5s%22%2C%22timeout_express%22%3A%2230m%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%C3%A4%C2%B8%C2%BB%C3%A9%C2%A2%C2%98%22%2C%22seller_id%22%3A%22%22%2C%22out_trade_no%22%3A%221642101027%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2F115.29.99.167%3A8081%2FSellerNet%2Fapi%2Falipay%2Fcallbacks.do&sign_type=RSA&timestamp=2017-01-20+16%3A42%3A10&version=1.0&sign=PrDyM2JugEjLnnMKt6WhsZ3TnG9i2ht8qZOBVwQDa3lYrrKbL0myxfwZPl%2BDWVTnH2xL9XeqoqZdRGit1uI6TdAdejIhzgpurWBiVSwoGrU72FNhClcYUCQp295K50IzDoAFGyf9yDXcLZ0jV3N2hAs960smO7dhXc60LEDxoPA%3D";
                        String orderInfo = response.getString("dataObject");
                        Log.e("TAG", "orderInfo: " + orderInfo);

                        /**
                         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
                         */
                        String sign = sign(orderInfo);
                        Log.e("TAG", "sign: " + sign);
                        try {
                            /**
                             * 仅需对sign 做URL编码
                             */
                            sign = URLEncoder.encode(sign, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        /**
                         * 完整的符合支付宝参数规范的订单信息
                         */
//						final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();
                        final String payInfo = orderInfo;

                        Runnable payRunnable = new Runnable() {

                            @Override
                            public void run() {
                                // 构造PayTask 对象
                                PayTask alipay = new PayTask(mContext);
                                // 调用支付接口，获取支付结果
                                String result = alipay.pay(payInfo, true);

                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };

                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }
                    DBLog.showToast(response.getString("errorDescription"), mContext);
                } catch (Exception e) {
                    DBLog.showToast("支付宝充值创建订单失败", mContext);
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
                DBLog.showToast("请求服务器失败", mContext);
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });

    }

    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(mContext);
        String version = payTask.getVersion();
        Toast.makeText(mContext, version, Toast.LENGTH_SHORT).show();
    }

    /**
     * 原生的H5（手机网页版支付切natvie支付） 【对应页面网页支付按钮】
     *
     * @param v
     */
    public void h5Pay(View v) {
        Intent intent = new Intent(mContext, H5PayDemoActivity.class);
        Bundle extras = new Bundle();
        /**
         * url是测试的网站，在app内部打开页面是基于webview打开的，demo中的webview是H5PayDemoActivity，
         * demo中拦截url进行支付的逻辑是在H5PayDemoActivity中shouldOverrideUrlLoading方法实现，
         * 商户可以根据自己的需求来实现
         */
        String url = "http://m.meituan.com";
        // url可以是一号店或者美团等第三方的购物wap站点，在该网站的支付过程中，支付宝sdk完成拦截支付
        extras.putString("url", url);
        intent.putExtras(extras);
        mContext.startActivity(intent);

    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + Url.BASE_URL + "/alipay/gotopay.do" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
//		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
//		Date date = new Date();
//		String key = format.format(date);
//
//		Random r = new Random();
//		key = key + r.nextInt();
//		key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

}

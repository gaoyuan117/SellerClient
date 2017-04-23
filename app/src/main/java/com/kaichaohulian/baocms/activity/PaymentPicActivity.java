package com.kaichaohulian.baocms.activity;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

public class PaymentPicActivity extends BaseActivity {


    @Override
    public void setContent() {
        setContentView(R.layout.payment_pic);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        String qrpic = getIntent().getStringExtra("qrpic");
        ImageView qrIV = (ImageView) findViewById(R.id.qr_pic);
        Glide.with(getActivity()).load(qrpic).into(qrIV);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initEvent() {
    }


//    public void orderRequest(final String amount) {
//        RequestParams params = new RequestParams();
//        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
//        params.put("money", amount);
//        params.put("bankName", BankCardEntity.getCardNo());
//        HttpUtil.post(Url.withdraw, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//                    DBLog.e("生成订单", response.toString());
//                    if (response.getInt("code") == 0) {
//                        JSONObject object = response.getJSONObject("dataObject");
//                        String orderId = object.getString("orderId");
//
//                    }
//                    showToastMsg(response.getString("errorDescription"));
//                } catch (Exception e) {
//                    showToastMsg("生成订单，解析json失败");
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFinish() {
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                showToastMsg("请求服务器失败");
//                DBLog.e("tag", statusCode + ":" + responseString);
//            }
//        });
//    }

}



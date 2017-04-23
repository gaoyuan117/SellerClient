package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.math.BigDecimal;

public class TransferDetailActivity extends BaseActivity {

    private BigDecimal bd;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_transfer_detail);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setTitle("转账详情");

        final String amount = getIntent().getStringExtra("amount");
        final String id = getIntent().getStringExtra("id");

        TextView amountTV = (TextView) findViewById(R.id.tv_transfer_amount);
        Button confirmBtn = (Button) findViewById(R.id.btn_transfer_confirm);
        TextView cancelTV = (TextView) findViewById(R.id.btn_transfer_cancel);

        bd = new BigDecimal(amount);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        amountTV.setText("¥" + bd.toString());

        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tansferSure(id);
            }
        });
    }

    @Override
    public void initEvent() {

    }

    public void tansferSure(final String transferId) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("transferId", transferId);
        HttpUtil.post(Url.transferSure, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Intent intent = new Intent(getActivity(), TransferSuccessActivity.class);
                    intent.putExtra("amount", bd.toString());
                    getActivity().startActivity(intent);
                    finish();
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

package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class MyChangeActivity extends BaseActivity {

    Button withdraw;
    Button chongzhi;
    TextView myRestMoney;
    String money;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_my_change);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("我的零钱");
        setRightTitle("零钱明细").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyChangeActivity.this, SmallMoneyDetailActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
            }
        });
        withdraw = getId(R.id.btn_withdraw);
        chongzhi = getId(R.id.btn_chongzhi);
        myRestMoney = getId(R.id.my_change_number);
    }

    @Override
    public void initEvent() {
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), WithdrawApplyActivity.class);
            }
        });
        chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), ChangeRechargeActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo(MyApplication.getInstance().UserInfo.getPhoneNumber());
    }

    public void back(View view) {
        finish();
    }

    public void getUserInfo(final String phone) {
        RequestParams params = new RequestParams();
        params.put("phoneNumber", phone);
        HttpUtil.post(Url.dependPhoneGetUserInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
                        money = response.optString("balance");
                        myRestMoney.setText(money);


                    } else {
                        showToastMsg(response.getString("errorDescription"));
                    }
                } catch (Exception e) {
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
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }
}



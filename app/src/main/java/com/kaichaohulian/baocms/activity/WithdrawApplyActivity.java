package com.kaichaohulian.baocms.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
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

public class WithdrawApplyActivity extends BaseActivity {

    Button btnWechat, btnAlipay, btnBank, btnNext;
    LinearLayout linear_bankpay;
    LinearLayout linear_wechatAndAlipay;
    TextView tvWechatAlipay;
    EditText edtInputNumber;
    EditText edtWechatId;
    EditText ed_kaihuyinhanag;
    EditText ed_kaihuzhanghao;
    EditText ed_kaihuxingming;

    private DataHelper mDataHelper;

    BtnListener listener;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_withdraw_apply);
    }

    @Override
    public void initData() {
        mDataHelper = new DataHelper(getActivity());
        listener = new BtnListener();
    }

    @Override
    public void initView() {
        setCenterTitle("提现申请");
        setRightTitle("提现记录").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(getActivity(), WithDrawalsHistoryActivity.class);
            }
        });
        linear_bankpay = getId(R.id.linear_bankpay);
        linear_wechatAndAlipay = getId(R.id.ln_wechat_alipay);
        tvWechatAlipay = getId(R.id.tv_wechatand_alipay_id);
        btnWechat = getId(R.id.btn_wechat);
        btnAlipay = getId(R.id.btn_alipay);
        btnBank = getId(R.id.btn_bankpay);
        btnNext = getId(R.id.withdraw_cash_btn);
        edtInputNumber = getId(R.id.edt_input_number);
        edtWechatId = getId(R.id.edt_we_ali_id);
        ed_kaihuyinhanag = getId(R.id.ed_kaihuyinhanag);
        ed_kaihuzhanghao = getId(R.id.ed_kaihuzhanghao);
        ed_kaihuxingming = getId(R.id.ed_kaihuxingming);

        linear_bankpay.setVisibility(View.GONE);
        linear_wechatAndAlipay.setVisibility(View.VISIBLE);


        btnBank.setTextColor(getResources().getColor(R.color.gray));
        btnAlipay.setTextColor(getResources().getColor(R.color.gray));
        btnWechat.setTextColor(getResources().getColor(R.color.white));

    }

    @Override
    public void initEvent() {
        btnAlipay.setOnClickListener(listener);
        btnBank.setOnClickListener(listener);
        btnWechat.setOnClickListener(listener);
        btnNext.setOnClickListener(listener);
    }

    private String typeTitle = "微信提现";

    class BtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_alipay:
                    typeTitle = "支付宝提现";
                    btnAlipay.setBackgroundResource(R.mipmap.deepgreen_bar);
                    btnBank.setBackgroundResource(R.mipmap.lightgreen_bar);
                    btnWechat.setBackgroundResource(R.mipmap.lightgreen_bar);

                    btnBank.setTextColor(getResources().getColor(R.color.gray));
                    btnWechat.setTextColor(getResources().getColor(R.color.gray));
                    btnAlipay.setTextColor(getResources().getColor(R.color.white));

                    linear_bankpay.setVisibility(View.GONE);
                    linear_wechatAndAlipay.setVisibility(View.VISIBLE);
                    tvWechatAlipay.setText("支付宝号");
                    break;
                case R.id.btn_wechat:
                    typeTitle = "微信提现";
                    btnWechat.setBackgroundResource(R.mipmap.deepgreen_bar);
                    btnAlipay.setBackgroundResource(R.mipmap.lightgreen_bar);
                    btnBank.setBackgroundResource(R.mipmap.lightgreen_bar);

                    btnBank.setTextColor(getResources().getColor(R.color.gray));
                    btnAlipay.setTextColor(getResources().getColor(R.color.gray));
                    btnWechat.setTextColor(getResources().getColor(R.color.white));

                    linear_bankpay.setVisibility(View.GONE);
                    linear_wechatAndAlipay.setVisibility(View.VISIBLE);
                    tvWechatAlipay.setText("微信账号");
                    break;
                case R.id.btn_bankpay:
                    ActivityUtil.next(WithdrawApplyActivity.this, WithdrawActivity.class);
//                    typeTitle = "银行卡提现";
//                    btnWechat.setBackgroundResource(R.mipmap.lightgreen_bar);
//                    btnAlipay.setBackgroundResource(R.mipmap.lightgreen_bar);
//                    btnBank.setBackgroundResource(R.mipmap.deepgreen_bar);
//
//                    btnWechat.setTextColor(getResources().getColor(R.color.gray));
//                    btnAlipay.setTextColor(getResources().getColor(R.color.gray));
//                    btnBank.setTextColor(getResources().getColor(R.color.white));
//
//                    linear_bankpay.setVisibility(View.VISIBLE);
//                    linear_wechatAndAlipay.setVisibility(View.GONE);
                    break;
                case R.id.withdraw_cash_btn:

                    RequestParams params = new RequestParams();
                    switch (typeTitle) {
                        case "支付宝提现":
                            if (!TextUtils.isEmpty(edtWechatId.getText().toString().trim()) && !TextUtils.isEmpty(edtInputNumber.getText().toString().trim())) {
                                params.put("zfbAccount", edtWechatId.getText().toString());
                                tiXian(params);
                            } else {
                                showToastMsg("请输入完整！");
                            }
                            break;
                        case "微信提现":
                            if (!TextUtils.isEmpty(edtWechatId.getText().toString().trim()) && !TextUtils.isEmpty(edtInputNumber.getText().toString().trim())) {
                                params.put("weixinAccount", edtWechatId.getText().toString());
                                tiXian(params);
                            } else {
                                showToastMsg("请输入完整！");
                            }
                            break;
                        case "银行卡提现":
                            if (!TextUtils.isEmpty(ed_kaihuyinhanag.getText().toString().trim()) && !TextUtils.isEmpty(ed_kaihuzhanghao.getText().toString().trim()) && !TextUtils.isEmpty(ed_kaihuxingming.getText().toString().trim()) && !TextUtils.isEmpty(edtInputNumber.getText().toString().trim())) {
                                params.put("bankName", ed_kaihuyinhanag.getText().toString());
                                params.put("bankNum", ed_kaihuzhanghao.getText().toString());
                                params.put("bankRealname", ed_kaihuxingming.getText().toString());
                                tiXian(params);
                            } else {
                                showToastMsg("请输入完整！");
                            }
                            break;
                    }

                    break;
            }
        }
    }

    public void tiXian(RequestParams params) {
        ShowDialog.showDialog(getActivity(), "提现申请中...", false, null);
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        double money = Double.parseDouble(edtInputNumber.getText().toString());
        params.put("money", (int) (money * 100));
        params.put("token", MyApplication.getInstance().UserInfo.getToken());
        HttpUtil.post(Url.users_banks_withdrawals, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response != null) {
                        if (Validator.isTokenIllegal(response.getString("errorDescription"))) {
                            UIHelper.reloginPage(getActivity());
                            return;
                        }
                    }
                    DBLog.e("提现：", response.toString());
                    if (response.getInt("code") == 0) {
                        MyApplication.getInstance().UserInfo.setBalance((Double.valueOf(MyApplication.getInstance().UserInfo.getBalance()) - Double.valueOf(edtInputNumber.getText().toString())) + "");
                        mDataHelper.UpdateUserInfo(MyApplication.getInstance().UserInfo);
                        showToastMsg("提现成功");
                        finish();
                        ActivityUtil.next(WithdrawApplyActivity.this, WithDrawalsHistoryActivity.class);
                    }
                    DBLog.showToast(response.getString("errorDescription"), getApplication());
                } catch (Exception e) {
                    DBLog.showToast("提现失败", getApplication());
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
                DBLog.showToast("请求服务器失败", getActivity());
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }

    public void back(View view) {
        finish();
    }

}

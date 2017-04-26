package com.kaichaohulian.baocms.activity;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawApplyActivity extends BaseActivity {

    @BindView(R.id.tv_withdrawApply_zhanghao)
    TextView Apply_Id;
    @BindView(R.id.ed_kaihuyinhanag)
    EditText edKaihuyinhanag;
    @BindView(R.id.ed_kaihuzhanghao)
    EditText edKaihuzhanghao;
    @BindView(R.id.ed_kaihuxingming)
    EditText edKaihuxingming;
    @BindView(R.id.edt_input_number)
    EditText edtInputNumber;
    @BindView(R.id.withdraw_cash_rest_money)
    TextView withdrawCashRestMoney;
    @BindView(R.id.ln_wechat_alipay)
    LinearLayout WechatAlipay;
    @BindView(R.id.linear_bankpay)
    LinearLayout Bankpay;
    @BindView(R.id.btn_wechat)
    Button btnWechat;
    @BindView(R.id.btn_alipay)
    Button btnAlipay;
    @BindView(R.id.btn_bankpay)
    Button btnBankpay;
    @BindView(R.id.tv_wechatand_alipay_id)
    TextView tv_aliorwechat;
    @BindView(R.id.withdraw_cash_btn)
    Button btnNext;
    private final int WECHAT=0,ALIPAY=1,BANKPAY=2;
    private String str_apply_id;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_withdraw_apply);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
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
        btnAlipay.setSelected(false);
        btnWechat.setSelected(true);
        btnBankpay.setSelected(false);
        WechatAlipay.setVisibility(View.VISIBLE);
        Bankpay.setVisibility(View.GONE);

    }

    @Override
    public void initEvent() {

    }



    private String typeTitle = "微信提现";

    @OnClick({R.id.btn_wechat, R.id.btn_alipay, R.id.btn_bankpay, R.id.txt_withdraw_all, R.id.withdraw_cash_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_wechat:
                typeTitle = "微信提现";
                tv_aliorwechat.setText("微信账号");
//                if (WechatAlipay.getVisibility() == View.GONE && Bankpay.getVisibility() == View.VISIBLE) {
//                    WechatAlipay.setVisibility(View.VISIBLE);
//                    Bankpay.setVisibility(View.GONE);
//                }
                btnAlipay.setSelected(false);
                btnWechat.setSelected(true);
                btnBankpay.setSelected(false);
                btnBankpay.setTextColor(getResources().getColor(R.color.gray));
                btnWechat.setTextColor(getResources().getColor(R.color.white));
                btnAlipay.setTextColor(getResources().getColor(R.color.gray));


                Apply_Id.setText(getApply_Id());
                break;
            case R.id.btn_alipay:
                typeTitle = "支付宝提现";
                tv_aliorwechat.setText("支付宝号");
//                if (WechatAlipay.getVisibility() == View.GONE && Bankpay.getVisibility() == View.VISIBLE) {
//                    WechatAlipay.setVisibility(View.VISIBLE);
//                    Bankpay.setVisibility(View.GONE);
//                }
                btnAlipay.setSelected(true);
                btnWechat.setSelected(false);
                btnBankpay.setSelected(false);
                btnBankpay.setTextColor(getResources().getColor(R.color.gray));
                btnWechat.setTextColor(getResources().getColor(R.color.gray));
                btnAlipay.setTextColor(getResources().getColor(R.color.white));
                Apply_Id.setText(getApply_Id());
                break;
            case R.id.btn_bankpay:
//                ActivityUtil.next(WithdrawApplyActivity.this, WithdrawActivity.class);
//                if (WechatAlipay.getVisibility() == View.VISIBLE && Bankpay.getVisibility() == View.GONE) {
//                    WechatAlipay.setVisibility(View.GONE);
//                    Bankpay.setVisibility(View.VISIBLE);
//                }
                typeTitle = "银行卡提现";
                tv_aliorwechat.setText("银行卡号");
                btnAlipay.setSelected(false);
                btnWechat.setSelected(false);
                btnBankpay.setSelected(true);
                btnBankpay.setTextColor(getResources().getColor(R.color.white));
                btnWechat.setTextColor(getResources().getColor(R.color.gray));
                btnAlipay.setTextColor(getResources().getColor(R.color.gray));
                Apply_Id.setText(getApply_Id());
                break;
            case R.id.txt_withdraw_all:
                edtInputNumber.setText(withdrawCashRestMoney.getText());
                break;
            case R.id.withdraw_cash_btn:


//                RequestParams params = new RequestParams();
//                switch (typeTitle) {
//                    case "支付宝提现":
//                        if (!TextUtils.isEmpty(Apply_Id.getText().toString().trim()) && !TextUtils.isEmpty(edtInputNumber.getText().toString().trim())) {
//                            params.put("zfbAccount", Apply_Id.getText().toString());
//                            tiXian(params);
//                        } else {
//                            showToastMsg("请输入完整！");
//                        }
//                        break;
//                    case "微信提现":
//                        if (!TextUtils.isEmpty(Apply_Id.getText().toString().trim()) && !TextUtils.isEmpty(edtInputNumber.getText().toString().trim())) {
//                            params.put("weixinAccount", Apply_Id.getText().toString());
//                            tiXian(params);
//                        } else {
//                            showToastMsg("请输入完整！");
//                        }
//                        break;
//                    case "银行卡提现":
//                        if (!TextUtils.isEmpty(edKaihuyinhanag.getText().toString().trim()) && !TextUtils.isEmpty(edKaihuzhanghao.getText().toString().trim()) && !TextUtils.isEmpty(edKaihuxingming.getText().toString().trim()) && !TextUtils.isEmpty(edtInputNumber.getText().toString().trim())) {
//                            params.put("bankName", edKaihuyinhanag.getText().toString());
//                            params.put("bankNum", edKaihuzhanghao.getText().toString());
//                            params.put("bankRealname", edKaihuxingming.getText().toString());
//                            tiXian(params);
//                        } else {
//                            showToastMsg("请输入完整！");
//                        }
//                        break;
//                }
//                break;
        }
    }

    private String getApply_Id(){
        str_apply_id = "";
        switch (typeTitle){
            case "支付宝提现":
                //TODO 获取支付宝帐号
                break;
            case "微信提现":
                //TODO 获取微信帐号
                break;
            case "银行卡提现":
                //TODO 获取银行卡帐号
                break;
        }
        if(str_apply_id.equals("")){
            str_apply_id ="尚未绑定";
        }
        return str_apply_id;
    }

    @Override
    protected void onResume() {
        super.onResume();
        withdrawCashRestMoney.setText(MyApplication.getInstance().UserInfo.getBalance() + "");
    }

    class BtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_alipay:

//                    btnAlipay.setBackgroundResource(R.mipmap.deepblue_bar);
//                    btnBank.setBackgroundResource(R.mipmap.deepwhite_bar);
//                    btnWechat.setBackgroundResource(R.mipmap.deepwhite_bar);
//
//                    btnBank.setTextColor(getResources().getColor(R.color.gray));
//                    btnWechat.setTextColor(getResources().getColor(R.color.gray));
//                    btnAlipay.setTextColor(getResources().getColor(R.color.white));


                    break;
                case R.id.btn_wechat:

//                    btnWechat.setBackgroundResource(R.mipmap.deepblue_bar);
//                    btnAlipay.setBackgroundResource(R.mipmap.deepwhite_bar);
//                    btnBank.setBackgroundResource(R.mipmap.deepwhite_bar);
//                    btnBank.setTextColor(getResources().getColor(R.color.gray));
//                    btnAlipay.setTextColor(getResources().getColor(R.color.gray));
//                    btnWechat.setTextColor(getResources().getColor(R.color.white));


                    break;
                case R.id.btn_bankpay:

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


                    break;
            }
        }
    }

    private DataHelper mDataHelper;

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

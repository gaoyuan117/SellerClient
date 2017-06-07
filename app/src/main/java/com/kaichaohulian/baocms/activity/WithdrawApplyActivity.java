package com.kaichaohulian.baocms.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.entity.BankCardEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.manager.UIHelper;
import com.kaichaohulian.baocms.manager.Validator;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.PasswordEdittext;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class WithdrawApplyActivity extends BaseActivity {

    @BindView(R.id.tv_withdrawApply_zhanghao)
    EditText Apply_Id;
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
    private View SignPassword;
    private PasswordEdittext paywordEdt;
    private PopupWindow PopSignPassword;
    private DataHelper mDataHelper;
    private BankCardEntity bankcard;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_withdraw_apply);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mDataHelper = new DataHelper(this);
        getBindBankCard();

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
//        Apply_Id.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(!Apply_Id.getText().toString().trim().equals(""))
//                {
//                    int i=0;
//                    if((i= Integer.parseInt(edtInputNumber.getText().toString().trim()))!=0){
//                        btnNext.setBackgroundResource(R.mipmap.deeporange_bar_part);
//                    }
//                }else{
//                    btnNext.setBackgroundResource(R.mipmap.deeporange_bar_normal);
//                }
//            }
//        });
        edtInputNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!Apply_Id.getText().toString().trim().equals(""))
                {
                    int i=0;
                    if((i= Integer.parseInt(edtInputNumber.getText().toString().trim()))>=0){
                        btnNext.setBackgroundResource(R.mipmap.deeporange_bar_part);
                        btnNext.setClickable(true);

                    }
                }else{
                    btnNext.setBackgroundResource(R.mipmap.deeporange_bar_normal);
                    btnNext.setClickable(false);
                }
            }
        });
    }

    private String typeTitle = "微信提现";

    @OnClick({R.id.btn_wechat, R.id.btn_alipay, R.id.btn_bankpay, R.id.txt_withdraw_all, R.id.withdraw_cash_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_wechat:
                typeTitle = "微信提现";
                tv_aliorwechat.setText("微信账号");
                btnAlipay.setSelected(false);
                btnWechat.setSelected(true);
                btnBankpay.setSelected(false);
                btnBankpay.setTextColor(getResources().getColor(R.color.gray));
                btnWechat.setTextColor(getResources().getColor(R.color.white));
                btnAlipay.setTextColor(getResources().getColor(R.color.gray));
                Apply_Id.setFocusableInTouchMode(true);
                Apply_Id.setFocusable(true);
                Apply_Id.requestFocus();
                Apply_Id.setOnClickListener(null);
                Apply_Id.setText("");
                break;
            case R.id.btn_alipay:
                typeTitle = "支付宝提现";
                tv_aliorwechat.setText("支付宝号");
                btnAlipay.setSelected(true);
                btnWechat.setSelected(false);
                btnBankpay.setSelected(false);
                btnBankpay.setTextColor(getResources().getColor(R.color.gray));
                btnWechat.setTextColor(getResources().getColor(R.color.gray));
                btnAlipay.setTextColor(getResources().getColor(R.color.white));
                Apply_Id.setFocusableInTouchMode(true);
                Apply_Id.setFocusable(true);
                Apply_Id.requestFocus();
                Apply_Id.setOnClickListener(null);
                Apply_Id.setText("");

                break;
            case R.id.btn_bankpay:

                typeTitle = "银行卡提现";
                tv_aliorwechat.setText("银行卡号");
                btnAlipay.setSelected(false);
                btnWechat.setSelected(false);
                btnBankpay.setSelected(true);
                btnBankpay.setTextColor(getResources().getColor(R.color.white));
                btnWechat.setTextColor(getResources().getColor(R.color.gray));
                btnAlipay.setTextColor(getResources().getColor(R.color.gray));
                Apply_Id.setFocusable(false);
                Apply_Id.setFocusableInTouchMode(false);
                Log.d("WithdrawApplyActivity", "bankcard:" + bankcard);
                if(bankcard==null){
                    Apply_Id.setText("添加银行卡");
                    Apply_Id.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityUtil.next(WithdrawApplyActivity.this,AddBankCardActivity.class);
                        }
                    });
                }else{
                    String i=bankcard.getCardNo();
                    i=i.substring(i.length()-4,i.length());
                    String j=bankcard.getBankName()+"("+i+")";
                    Apply_Id.setText(j);
                    Apply_Id.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent=new Intent(WithdrawApplyActivity.this,MyBankCardListActivity.class);
                            intent.putExtra("IsChoose",true);
                            startActivityForResult(intent,10);
                        }
                    });
                }
                break;
            case R.id.txt_withdraw_all:
                edtInputNumber.setText(withdrawCashRestMoney.getText());
                break;
            case R.id.withdraw_cash_btn:
                ShowPayWord();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            BankCardEntity cache= (BankCardEntity) data.getSerializableExtra("bankcard");
            String i=cache.getCardNo();
            i=i.substring(i.length()-4,i.length());
            String j=cache.getBankName()+"("+i+")";
            Apply_Id.setText(j);
        }
    }

    //显示支付密码的验证框
    private void ShowPayWord(){
        if(SignPassword==null){
            SignPassword=View.inflate(this,R.layout.sign_paypassword,null);
            paywordEdt= (PasswordEdittext) SignPassword.findViewById(R.id.paypassword_edt);
            ImageView iv= (ImageView) SignPassword.findViewById(R.id.img_exit_signpassword);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopSignPassword.dismiss();
                }
            });
            paywordEdt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(editable.length()==6){
                        SignPayPassWord(editable.toString().trim());
                    }
                }
            });
        }
        if(PopSignPassword==null){
            int H;
            H = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            SignPassword.measure(0, H);
            H = SignPassword.getMeasuredHeight();
            PopSignPassword=new PopupWindow(SignPassword, ViewGroup.LayoutParams.MATCH_PARENT,H);
            PopSignPassword.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            PopSignPassword.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            PopSignPassword.setTouchable(true); // 设置popupwindow可点击
            PopSignPassword.setFocusable(true); // 获取焦点
            ColorDrawable dw = new ColorDrawable(Color.WHITE);
            //设置SelectPicPopupWindow弹出窗体的背景
            PopSignPassword.setBackgroundDrawable(dw);
            PopSignPassword.setAnimationStyle(R.style.popPassword_animation);
            PopSignPassword.showAtLocation(findViewById(R.id.main_activity_withdraw_apply),
                    Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
            paywordEdt.setFocusable(true);
        }else{
            PopSignPassword.showAtLocation(findViewById(R.id.main_activity_withdraw_apply),
                    Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
            paywordEdt.setFocusable(true);

        }

        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //这里给它设置了弹出的时间，
        imm.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
    }
    //网络请求验证支付密码
    private void SignPayPassWord(String payword){
        if(map==null){
            map=new HashMap<>();
        }else{
            map.clear();
        }
        map.put("id", String.valueOf(MyApplication.getInstance().UserInfo.getUserId()));
        map.put("password",payword);
        RetrofitClient.getInstance().createApi().verificatPassword(map)
                .compose(RxUtils.<HttpResult>io_main())
                .subscribe(new Observer<HttpResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult value) {
                        PopSignPassword.dismiss();
                        if(value.errorDescription.contains("重新输入")){
                            Toast.makeText(WithdrawApplyActivity.this, "密码错误请重新输入", Toast.LENGTH_SHORT).show();
                            paywordEdt.getText().clear();
                        }else {
                            RequestParams params = new RequestParams();
                            switch (typeTitle) {
                                case "支付宝提现":
                                    if (!TextUtils.isEmpty(Apply_Id.getText().toString().trim()) && !TextUtils.isEmpty(edtInputNumber.getText().toString().trim())) {
                                        params.put("zfbAccount", Apply_Id.getText().toString());
                                        tiXian(params);
                                    } else {
                                        showToastMsg("请输入完整！");
                                    }
                                    break;
                                case "微信提现":
                                    if (!TextUtils.isEmpty(Apply_Id.getText().toString().trim()) && !TextUtils.isEmpty(edtInputNumber.getText().toString().trim())) {
                                        params.put("weixinAccount", Apply_Id.getText().toString());
                                        tiXian(params);
                                    } else {
                                        showToastMsg("请输入完整！");
                                    }
                                    break;
                                case "银行卡提现":
                                    if (!TextUtils.isEmpty(edKaihuyinhanag.getText().toString().trim()) && !TextUtils.isEmpty(edKaihuzhanghao.getText().toString().trim()) && !TextUtils.isEmpty(edKaihuxingming.getText().toString().trim()) && !TextUtils.isEmpty(edtInputNumber.getText().toString().trim())) {
                                        params.put("bankName", edKaihuyinhanag.getText().toString());
                                        params.put("bankNum", edKaihuzhanghao.getText().toString());
                                        params.put("bankRealname", edKaihuxingming.getText().toString());
                                        tiXian(params);
                                    } else {
                                        showToastMsg("请输入完整！");
                                    }
                                    break;
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

    public void getBindBankCard() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        HttpUtil.post(Url.getBindCard, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    ArrayList<BankCardEntity> list=new ArrayList<>();
                    DBLog.e("获取银行卡:", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray array = response.getJSONArray("dataObject");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            BankCardEntity entity = new BankCardEntity();
                            entity.setId(object.getInt("id"));
                            entity.setCreatedTime(object.getString("createdTime"));
                            entity.setCreator(object.getInt("creator"));
                            entity.setIsLocked(object.getBoolean("isLocked"));
                            entity.setLastModifiedTime(object.getString("lastModifiedTime"));
                            entity.setLastModifier(object.getInt("lastModifier"));
                            entity.setTimeStamp(object.getString("timeStamp"));
                            entity.setCardNo(object.getString("cardNo"));
                            entity.setCardType(object.getString("cardType"));
                            entity.setIdcard(object.getString("idcard"));
                            entity.setUsername(object.getString("username"));
                            entity.setOneOuota(object.getString("oneOuota"));
                            entity.setDayusername(object.getString("dayusername"));
                            entity.setBankName(object.getString("bankName"));
//                            entity.setNumberAll(object.getString("cardNo"));
                            list.add(entity);
                        }
                        if(list.size()!=0){
                            bankcard=list.get(0);
                        }
                    }
                } catch (Exception e) {
                    showToastMsg("获取银行卡，解析json异常");
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
                ShowDialog.dissmiss();
            }
        });
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

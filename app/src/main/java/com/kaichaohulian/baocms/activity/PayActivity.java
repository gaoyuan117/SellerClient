package com.kaichaohulian.baocms.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.AppManager;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.util.TitleUtils;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.PasswordEdittext;
import com.kaichaohulian.baocms.wxapi.WxPayUtile;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class PayActivity extends BaseActivity {

    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.cb_pay_wx)
    CheckBox cbPayWx;
    @BindView(R.id.rl_pay_wx)
    RelativeLayout rlPayWx;
    @BindView(R.id.cb_pay_yue)
    CheckBox cbPayYue;
    @BindView(R.id.rl_pay_yue)
    RelativeLayout rlPayYue;
    @BindView(R.id.bt_pay_pay)
    Button btPayPay;

    private EditText paywordEdt;
    private PopupWindow PopSignPassword;

    private String payType = "1";//1 微信  2 余额
    private String payMoney = "-1元";
    private View SignPassword;
    private String message, friendId, type;
    private DataHelper mDataHelper;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_pay2);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        payMoney = getIntent().getStringExtra("pay_money");
        message = getIntent().getStringExtra("message");
        friendId = getIntent().getStringExtra("friend_id");
        type = getIntent().getStringExtra("type");
        tvPayMoney.setText(payMoney + "元");
    }

    @Override
    public void initView() {
        new TitleUtils(this).setTitle("支付");
        mDataHelper = new DataHelper(this);

    }


    @Override
    public void initEvent() {

    }

    @OnClick({R.id.rl_pay_wx, R.id.rl_pay_yue, R.id.bt_pay_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_pay_wx:
                cbPayWx.setChecked(true);
                cbPayYue.setChecked(false);
                payType = "1";
                break;
            case R.id.rl_pay_yue:
                cbPayWx.setChecked(false);
                cbPayYue.setChecked(true);
                payType = "2";
                break;
            case R.id.bt_pay_pay:
                ShowPayWord();
                break;
        }
    }


    /**
     * 显示支付密码的验证框
     */
    private void ShowPayWord() {
        if(MyApplication.getInstance().UserInfo.getPayPassword()==null){
            Toast.makeText(this, "请到设置-设置支付密码页面设置支付密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (SignPassword == null) {
            SignPassword = View.inflate(this, R.layout.sign_paypassword, null);
            paywordEdt = (PasswordEdittext) SignPassword.findViewById(R.id.paypassword_edt);
            ImageView iv = (ImageView) SignPassword.findViewById(R.id.img_exit_signpassword);
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
                    if (editable.length() == 6) {
                        SignPayPassWord(editable.toString().trim());
                    }
                }
            });
        }
        if (PopSignPassword == null) {
            int H;
            H = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            SignPassword.measure(0, H);
            H = SignPassword.getMeasuredHeight();
            PopSignPassword = new PopupWindow(SignPassword, ViewGroup.LayoutParams.MATCH_PARENT, H);
            PopSignPassword.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            PopSignPassword.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            PopSignPassword.setTouchable(true); // 设置popupwindow可点击
            PopSignPassword.setFocusable(true); // 获取焦点
            ColorDrawable dw = new ColorDrawable(Color.WHITE);
            //设置SelectPicPopupWindow弹出窗体的背景
            PopSignPassword.setBackgroundDrawable(dw);
            PopSignPassword.setAnimationStyle(R.style.popPassword_animation);
            PopSignPassword.showAtLocation(findViewById(R.id.ll_pay),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            paywordEdt.setFocusable(true);
        } else {
            PopSignPassword.showAtLocation(findViewById(R.id.ll_pay),
                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            paywordEdt.setFocusable(true);

        }
        InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //这里给它设置了弹出的时间，
        imm.toggleSoftInput(1000, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 网络请求验证支付密码
     *
     * @param payword
     */
    private void SignPayPassWord(String payword) {
        map.clear();
        map.put("id", String.valueOf(MyApplication.getInstance().UserInfo.getUserId()));
        map.put("password", payword);
        RetrofitClient.getInstance().createApi().verificatPayword(map)
                .compose(RxUtils.<HttpResult>io_main())
                .subscribe(new Observer<HttpResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(HttpResult value) {
                        if (value.errorDescription.contains("重新输入")) {
                            Toast.makeText(PayActivity.this, "密码错误请重新输入", Toast.LENGTH_SHORT).show();
                            paywordEdt.getText().clear();
                        } else {
                            if (payType.equals("1")) {//微信支付
                                wxPay();
                                paywordEdt.getText().clear();
                            } else {
                                yuePay();//余额支付
                                paywordEdt.getText().clear();
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

    /**
     * 添加好友
     */
    private void addFriendByMoney() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", MyApplication.getInstance().UserInfo.getUserId());
        map.put("friendId", friendId);
        RetrofitClient.getInstance().createApi().addFriendByMoney(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(this) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        ToastUtil.showMessage("添加成功");
                        finish();
                        AppManager.getAppManager().finishActivity(AddFriendsFinalActivity.class);
                    }
                });
    }

    /**
     * 余额支付
     */
    private void yuePay() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", MyApplication.getInstance().UserInfo.getUserId());
        map.put("amount", payMoney);
        map.put("payType", type);
        map.put("token", MyApplication.getInstance().UserInfo.getToken());
        RetrofitClient.getInstance().createApi().yuePay(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(this, "支付中...") {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        PopSignPassword.dismiss();
                        if (type.equals("1")) {
                            addFriendByMoney();//添加好友
                        } else {
                            setResult(RESULT_OK);
                            finish();
                        }
                    }
                });
    }

    /**
     * 微信支付
     */
    private void wxPay() {
        WxPayUtile.getInstance(getActivity(), String.valueOf(Double.valueOf(payMoney)), Url.BASE_URL + "/api/Order/notify_wxpay"
                , "微信支付", "订单号", "", type).doPay(chatHandler); // 微信支付
    }

    /**
     * 微信支付
     */
    public Handler chatHandler = new Handler(new Handler.Callback() {
        //		msg.what== 0 ：表示支付成功
//		msg.what== -1 ：表示支付失败
//		msg.what== -2 ：表示取消支付
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 800://商户订单号重复或生成错误
                    DBLog.e("TAG", "支付结果: 商户订单号重复或生成错误 " + msg.what);
                    break;
                case 0://支付成功
                    DBLog.e("TAG", "支付结果: 支付成功 " + msg.what);
                    MyApplication.getInstance().UserInfo.setBalance((Double.valueOf(payMoney) + Double.valueOf(MyApplication.getInstance().UserInfo.getBalance())) + "");
                    mDataHelper.UpdateUserInfo(MyApplication.getInstance().UserInfo);
                    finish();

                    break;
                case -1://支付失败
                    DBLog.e("TAG", "支付结果: 支付失败 " + msg.what);
                    break;
                case -2://取消支付
                    DBLog.e("TAG", "支付结果: 取消支付 " + msg.what);
                    break;
                default:
                    break;
            }
            return false;
        }
    });
}

package com.kaichaohulian.baocms.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.paytreasure.PayResult;
import com.kaichaohulian.baocms.paytreasure.PayTreasureUtils;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.wxapi.WxPayUtile;

/**
 * Created by HuaSao1024 on 2017/2/3.
 */

public class ChangeRechargeActivity extends BaseActivity {

    private Button next_step;
    private EditText edtInputNumber;
    private ImageView weixin_ok, zhifubao_ok;
    private LinearLayout Linear_weixin, Linear_zhifubao;

    private DataHelper mDataHelper;
    private String actualamount;
    private String amount;
    private String typeTitle = "支付宝充值";

    @Override
    public void setContent() {
        setContentView(R.layout.changerecharge_activity);
    }

    @Override
    public void initData() {
        mDataHelper = new DataHelper(getActivity());
        actualamount = getIntent().getStringExtra("actualamount");
        amount = getIntent().getStringExtra("amount");
    }

    @Override
    public void initView() {
        setCenterTitle("充值");
        edtInputNumber = getId(R.id.edtInputNumber);
        next_step = getId(R.id.next_step);
        weixin_ok = getId(R.id.weixin_check);
        zhifubao_ok = getId(R.id.zhifubao_check);
        Linear_weixin = getId(R.id.Linear_weixin);
        Linear_zhifubao = getId(R.id.Linear_zhifubao);
        if (!TextUtils.isEmpty(amount)) {
            edtInputNumber.setText(amount);
        }
    }

    @Override
    public void initEvent() {
        Linear_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeTitle = "微信充值";
                weixin_ok.setBackground(getResources().getDrawable(R.drawable.check_sel));
                zhifubao_ok.setBackground(getResources().getDrawable(R.drawable.check));
            }
        });
        Linear_zhifubao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeTitle = "支付宝充值";
                weixin_ok.setBackground(getResources().getDrawable(R.drawable.check));
                zhifubao_ok.setBackground(getResources().getDrawable(R.drawable.check_sel));
            }
        });
        next_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtInputNumber.getText().toString().equals("")) {
                    showToastMsg("请输入金额");
                    return;
                }
                switch (typeTitle) {
                    case "支付宝充值":
                        PayTreasureUtils.getInstance(getActivity(), null, typeTitle, typeTitle + ":"
                                + edtInputNumber.getText().toString() + "元", edtInputNumber.getText()
                                .toString(), actualamount).pay(payHandler);
                        break;
                    case "微信充值":
                        WxPayUtile.getInstance(getActivity(), String.valueOf(Double.valueOf(edtInputNumber
                                        .getText().toString())), Url.BASE_URL + "/api/Order/notify_wxpay"
                                , typeTitle, "订单号", actualamount).doPay(chatHandler); // 微信支付
                        break;
                }
            }
        });
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
                    MyApplication.getInstance().UserInfo.setBalance((Double.valueOf(edtInputNumber.getText().toString()) + Double.valueOf(MyApplication.getInstance().UserInfo.getBalance())) + "");
                    mDataHelper.UpdateUserInfo(MyApplication.getInstance().UserInfo);
                    finish();
                    ActivityUtil.next(ChangeRechargeActivity.this, WithdrawSuccessActivity.class);
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


    /**
     * 支付宝支付
     */
    private Handler payHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PayTreasureUtils.SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        MyApplication.getInstance().UserInfo.setBalance((Double.valueOf(edtInputNumber.getText().toString()) + Double.valueOf(MyApplication.getInstance().UserInfo.getBalance())) + "");
                        mDataHelper.UpdateUserInfo(MyApplication.getInstance().UserInfo);
                        finish();
                        ActivityUtil.next(ChangeRechargeActivity.this, WithdrawSuccessActivity.class);
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            DBLog.showToast("支付结果确认中", getActivity());
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            DBLog.showToast("支付失败", getActivity());
                        }
                    }
                    break;
                }
                default:
                    break;
            }


        }
    };
}

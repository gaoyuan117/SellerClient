package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.BankCardEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 钱包界面
 */
public class PocketActivity extends BaseActivity {


    private LinearLayout transfer, phoneRecharge, buyerRedbag, smallMoney, mAddBankCard;
    public static final int SET_PASSWORD_REQUEST_CODE = 6;
    public static final int NextAddMember = 998;
    private TextView myChange;
    private TextView bankCardNumber;
    private List<BankCardEntity> data;

    @Override
    public void setContent() {
        setContentView(R.layout.pocket);
    }

    @Override
    public void initData() {
        data = new ArrayList<BankCardEntity>();
        getBindBankCard();
    }

    @Override
    public void initView() {
        transfer = getId(R.id.transfer_money);
        phoneRecharge = getId(R.id.phone_recharge);
        buyerRedbag = getId(R.id.buyer_red_bag);
        smallMoney = getId(R.id.small_money_recharge);
        mAddBankCard = getId(R.id.bank_card);
        myChange = getId(R.id.pocket_small_money_accout);
        bankCardNumber = getId(R.id.pocket_bankcard_number);
        setCenterTitle("钱包");
    }

    @Override
    public void initEvent() {
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ActivityUtil.next(PocketActivity.this, TransferSelectContactActivity.class, bundle, 2);
            }
        });
        phoneRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PocketActivity.this, RechargeActivity.class);
                startActivityForResult(intent, SET_PASSWORD_REQUEST_CODE);
            }
        });
        buyerRedbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(PocketActivity.this, RedBagActivity.class);
            }
        });
        smallMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(PocketActivity.this, MyChangeActivity.class);
            }
        });
        mAddBankCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(PocketActivity.this, MyBankCardListActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.getInstance().UserInfo != null) {
            BigDecimal bd = new BigDecimal(MyApplication.getInstance().UserInfo.getBalance());
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            myChange.setText("￥" + bd.toString());
            bankCardNumber.setText(data.size() + "张");
        }
    }

    public void back(View view) {
        finish();
    }

    public void getBindBankCard() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        HttpUtil.post(Url.getBindCard, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取银行卡:", response.toString());
                    if (response.getInt("code") == 0) {
                        data.clear();
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
                            data.add(entity);
                            bankCardNumber.setText(data.size() + "张");
                        }
                    }
                    showToastMsg(response.getString("errorDescription"));
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
}

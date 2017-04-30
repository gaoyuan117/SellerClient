package com.kaichaohulian.baocms.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 钱包界面
 */
public class PocketActivity extends BaseActivity {


    @BindView(R.id.pocket_small_money_accout)
    TextView SmallMoneyAccout;
    @BindView(R.id.small_money_recharge)
    LinearLayout smallMoneyLinear;
    @BindView(R.id.pocket_bankcard_number)
    TextView BankcardNumber;
    @BindView(R.id.bank_card)
    LinearLayout BankCardLinear;
    @BindView(R.id.phone_recharge)
    RelativeLayout RechargeLinear;
    @BindView(R.id.transfer_money)
    RelativeLayout TransferLinear;
//    private LinearLayout transfer, phoneRecharge, buyerRedbag, smallMoney, mAddBankCard;

    private List<BankCardEntity> data;
    public static final int SET_PASSWORD_REQUEST_CODE = 6;
    public static final int NextAddMember = 998;

    @Override
    public void setContent() {
        setContentView(R.layout.pocket);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        setCenterTitle("钱包");
    }

    @Override
    public void initView() {
    }


    @Override
    public void initEvent() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO 获取余额和银行卡数
//      transfer.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Bundle bundle = new Bundle();
//            ActivityUtil.next(PocketActivity.this, TransferSelectContactActivity.class, bundle, 2);
//        }
//    });
    }

    public void back(View view) {
        finish();
    }





    @OnClick({R.id.small_money_recharge, R.id.bank_card, R.id.phone_recharge, R.id.transfer_money})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.small_money_recharge:
                ActivityUtil.next(PocketActivity.this, MyChangeActivity.class);
                break;
            case R.id.bank_card:
                ActivityUtil.next(PocketActivity.this, MyBankCardListActivity.class);
                break;
            case R.id.phone_recharge:
                //为手机充值
//                Intent intent = new Intent();
//                intent.setClass(PocketActivity.this, RechargeActivity.class);
//                startActivityForResult(intent, SET_PASSWORD_REQUEST_CODE);
                //充值
                ActivityUtil.next(getActivity(), ChangeRechargeActivity.class);
                break;
            case R.id.transfer_money:
//                Bundle bundle = new Bundle();
//                ActivityUtil.next(PocketActivity.this, TransferSelectContactActivity.class, bundle, 2);
                //提现
                ActivityUtil.next(getActivity(), WithdrawApplyActivity.class);
                break;
        }
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
                            BankcardNumber.setText(data.size() + "张");
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



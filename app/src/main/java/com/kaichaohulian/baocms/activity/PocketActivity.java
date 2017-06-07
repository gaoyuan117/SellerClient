package com.kaichaohulian.baocms.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.dialog.ECProgressDialog;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferenceSettings;
import com.kaichaohulian.baocms.ecdemo.core.ClientUser;
import com.kaichaohulian.baocms.ecdemo.ui.SDKCoreHelper;
import com.kaichaohulian.baocms.entity.BankCardEntity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.manager.SPContent;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.SPUtils;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yuntongxun.ecsdk.ECInitParams;

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
        SmallMoneyAccout.setText(MyApplication.getInstance().UserInfo.getBalance());
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
        getUserInfo(MyApplication.getInstance().UserInfo.getPhoneNumber());
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
                //充值
                ActivityUtil.next(getActivity(), ChangeRechargeActivity.class);
                break;
            case R.id.transfer_money:
                ActivityUtil.next(getActivity(), WithdrawApplyActivity.class);
                break;
        }
    }

    public void getUserInfo(final String phone) {
        RequestParams params = new RequestParams();
        params.put("phoneNumber", phone);
        HttpUtil.post(Url.dependPhoneGetUserInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("登录：", response.toString());
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
                        String accountNumber = response.getString("accountNumber");
                        BankcardNumber.setText(accountNumber);


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
                ShowDialog.dissmiss();
            }
        });
    }
}



package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.MyChange;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 零钱充值界面
 */
public class MoneyRechargeActivity extends BaseActivity {
    private static final int SET_PASS_WORD_REQUEST = 13;
    private static final int VERIFY_PASSWORD_REQUEST = 14;
    Button    btnNext;
    EditText  inputNumber;
    TextView  tvMaxTransfer;
    String    maxTransfer;
    String    inputS;
    @Override
    public void setContent() {
        setContentView(R.layout.money_recharge);
    }

    @Override
    public void initData() {

    }
    @Override
    public void initView() {

        setCenterTitle("充值");
        btnNext = getId(R.id.next_step);
        inputNumber = getId(R.id.change_address_phone_number_edit);
        tvMaxTransfer = getId(R.id.max_transfer_account);

        maxTransfer = tvMaxTransfer.getText().toString().trim();
    }

    @Override
    public void initEvent() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputS = inputNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(inputS)){
                    double max = Double.valueOf(maxTransfer);
                    double inputD = Double.valueOf(inputS);
                    if (inputD > max){
                        showToastMsg("输入金额不能大于每日限额");
                        return;
                    }
                    verifyPassword();
                }
            }
        });

    }
    public void verifyPassword(){
        if (MyApplication.getInstance().UserInfo.getPayPassword() == null){
            Intent intent = new Intent(MoneyRechargeActivity.this, SetPasswordActivity.class);
            startActivityForResult(intent, SET_PASS_WORD_REQUEST);
        }else {
            Intent intent = new Intent(MoneyRechargeActivity.this, VerifyPasswordActivity.class);
            startActivityForResult(intent, VERIFY_PASSWORD_REQUEST);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent){
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case  SET_PASS_WORD_REQUEST:
                    DBLog.e("password", MyApplication.getInstance().UserInfo.getPayPassword());
                    Intent intent1 = new Intent(MoneyRechargeActivity.this, SetPasswordActivity.class);
                    startActivityForResult(intent1, VERIFY_PASSWORD_REQUEST);
                    break;
                case  VERIFY_PASSWORD_REQUEST:
                    orderRequest(inputS);
                    break;
            }
        }
    }

    public void orderRequest(final String amount){
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().UserInfo.getUserId());
        params.put("amount", amount);

        HttpUtil.post(Url.phoneAndSystemRecharge,params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("生成订单", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONObject object = response.getJSONObject("dataObject");
                        String orderId = object.getString("orderId");
                        recharge(orderId, amount);
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("生成订单，解析json失败");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFinish() {}
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }
    public void recharge(String orderId, final String amount){
        RequestParams params = new RequestParams();
        params.put("orderId", orderId);
        params.put("userId", MyApplication.getInstance().UserInfo.getUserId());
        HttpUtil.post(Url.recharge,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("正式充值", response.toString());
                    if (response.getInt("code") == 0) {
                        double input = Double.valueOf(amount);
                        double rest = MyChange.getInstance().getMyMoney(getApplicationContext()) + input;
                        MyChange.getInstance().setMyMoney(getApplicationContext(),rest);
                        showToastMsg("已充值成功");
                        startActivity(new Intent(MoneyRechargeActivity.this, RechargeSuccedActicity.class));
                        finish();
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("正式充值，解析json失败");
                    e.printStackTrace();
                }
            }
            @Override
            public void onFinish() {}
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });

    }
}

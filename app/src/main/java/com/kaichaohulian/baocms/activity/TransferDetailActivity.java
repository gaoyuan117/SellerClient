package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.CustomerServiceHelper;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.IMChattingHelper;
import com.kaichaohulian.baocms.ecdemo.ui.contact.ContactLogic;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;

import org.apache.http.Header;
import org.json.JSONObject;

import java.math.BigDecimal;

import de.greenrobot.event.EventBus;

public class TransferDetailActivity extends BaseActivity {

    private BigDecimal bd;
    private String uid;
    private String phoneNumber;
    private String username;
    private boolean mCustomerService = false;
    private String amount;
    private String id;


    @Override
    public void setContent() {
        setContentView(R.layout.activity_transfer_detail);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("转账详情");

        amount = getIntent().getStringExtra("amount");
        id = getIntent().getStringExtra("id");
        uid = getIntent().getStringExtra("uid");

        TextView amountTV = (TextView) findViewById(R.id.tv_transfer_amount);
        Button confirmBtn = (Button) findViewById(R.id.btn_transfer_confirm);
        TextView cancelTV = (TextView) findViewById(R.id.btn_transfer_cancel);

        bd = new BigDecimal(amount);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        amountTV.setText("¥" + bd.toString());

        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tansferSure(id);
            }
        });
    }

    @Override
    public void initEvent() {

    }

    public void tansferSure(final String transferId) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("transferId", transferId);
        HttpUtil.post(Url.transferSure, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    Log.e("gy", "确认收款：" + response.toString());
                    if (response.getString("code").equals("0")) {
                        Intent intent = new Intent(getActivity(), TransferSuccessActivity.class);
                        intent.putExtra("amount", bd.toString());
                        intent.putExtra("uid", uid);
                        getActivity().startActivity(intent);
                        searchUser(uid);
                        finish();
                    } else {
                        ToastUtil.showMessage("确认失败");
                    }
                } catch (Exception e) {
                    showToastMsg("验证密码，解析json失败");
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
            }
        });
    }


    /**
     * 发送转账信息
     */
    private void sendTransMsg(String receiverId, String phoneNum, String amount) {
        if (!TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(amount)) {
            try {
                // 组建一个待发送的ECMessage
                ECMessage msg = ECMessage.createECMessage(ECMessage.Type.TXT);
                //如果需要跨应用发送消息，需通过appkey+英文井号+用户帐号的方式拼接，发送录音、发送群组消息等与此方式一致。

                //TODO 设置消息接收者 和sendText一样
                msg.setTo(phoneNum);
                msg.setNickName(MyApplication.getInstance().UserInfo.getUsername());

                // 创建一个文本消息体，并添加到消息对象中
                com.alibaba.fastjson.JSONObject transferJson = new com.alibaba.fastjson.JSONObject();
                transferJson.put("uid", MyApplication.getInstance().UserInfo.getUserId());
                transferJson.put("type", "zhuantyp2e");
                transferJson.put("touid", receiverId);
                transferJson.put("money", amount);

                ECTextMessageBody msgBody = new ECTextMessageBody(transferJson.toString());
                msg.setBody(msgBody);
                msg.setSessionId(phoneNum);
                msg.setType(ECMessage.Type.TXT);

                msg.setUserData(transferJson.toString());
                // 将消息体存放到ECMessage中

                mCustomerService = ContactLogic.isCustomService(phoneNum);
                try {
                    // 发送消息，该函数见上
                    long rowId = -1;
                    if (mCustomerService) {
                        rowId = CustomerServiceHelper.sendMCMessage(msg);
                    } else {
                        rowId = IMChattingHelper.sendECMessage(msg);
                    }
                    // 通知列表刷新
                    msg.setId(rowId);

                    EventBus.getDefault().post(msg);
//                    CCPAppManager.startChattingAction(TransferSuccessActivity.this, phoneNumber, username,true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                // 处理发送异常
                DBLog.e("ECSDK_Demo", "send message fail , e=" + e.getMessage());
            }
        }
    }


    private void searchUser(final String id) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("friendId", id);
        HttpUtil.post(Url.dependIDGetUserInfo, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                try {
                    Log.e("gy", "哈哈哈：" + response.toString());
                    if (response.getInt("code") == 0) {
                        org.json.JSONObject jsonObject = response.getJSONObject("dataObject");
                        DBLog.e("转账", response.toString());

                        phoneNumber = jsonObject.optString("phoneNumber");
                        username = jsonObject.optString("username");

                        Log.e("gy", "uid" + uid);
                        Log.e("gy", "phone" + phoneNumber);
                        sendTransMsg(uid, phoneNumber, amount);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
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

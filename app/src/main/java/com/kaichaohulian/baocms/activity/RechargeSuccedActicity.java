package com.kaichaohulian.baocms.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.CustomerServiceHelper;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.IMChattingHelper;
import com.kaichaohulian.baocms.ecdemo.ui.contact.ContactLogic;
import com.kaichaohulian.baocms.entity.MessageEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yuntongxun.ecsdk.ECChatManager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;

import org.apache.http.Header;

import java.util.List;

import de.greenrobot.event.EventBus;

public class RechargeSuccedActicity extends Activity {
    TextView tvAccount;
    Button btnSure;

    public static final String RECEIVER_ID = "RECEIVER_ID";
    public static final String PHONE_NUM = "PHONE_NUM";
    public static final String AMOUNT = "AMOUNT";
    public static final String TRANSFER_ID = "TRANSFER_ID";
    public static final String IS_TRANSFER = "IS_TRANSFER";
    private String mReceiverID;
    private String mPhone;
    private String mAmount;
    private String mTransferId;
    private boolean mIsTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_ok);
        mReceiverID = getIntent().getStringExtra(RECEIVER_ID);
        mPhone = getIntent().getStringExtra(PHONE_NUM);
        mAmount = getIntent().getStringExtra(AMOUNT);
        mIsTransfer = getIntent().getBooleanExtra(IS_TRANSFER, false);
        mTransferId = getIntent().getStringExtra(TRANSFER_ID);
        initView();
        initEvent();
    }


    public void initView() {
        TextView titleTV = (TextView) findViewById(R.id.center_title_tv);
        TextView tipTV = (TextView) findViewById(R.id.recharge_finish_text);
        if (mIsTransfer) {
            titleTV.setText("转账成功");
            tipTV.setText("转账成功");
        } else {
            titleTV.setText("充值成功");
            tipTV.setText("充值成功");
        }


        mDataHelper = new DataHelper(this);
        conversation = mDataHelper.GetUserMessageList(mReceiverID);

        btnSure = (Button) findViewById(R.id.btn_sure);
        tvAccount = (TextView) findViewById(R.id.pocket_business_account);

    }

    public void initEvent() {
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendTransMsg(mReceiverID, mPhone, mAmount);
                finish();
            }
        });
    }


    private List<MessageEntity> conversation;
    private DataHelper mDataHelper;
    private boolean mCustomerService = false;

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
                msg.setTo(mPhone);
                msg.setNickName(MyApplication.getInstance().UserInfo.getUsername());

                // 创建一个文本消息体，并添加到消息对象中
                JSONObject transferJson = new JSONObject();
                transferJson.put("uid", MyApplication.getInstance().UserInfo.getUserId());
                transferJson.put("content", "转账");
                transferJson.put("txt_msgType", "transfetype");
                transferJson.put("touid", receiverId);
                transferJson.put("type", "转账");
                transferJson.put("money", amount);
                transferJson.put("id", mTransferId);

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

                    searchUser(receiverId);

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
                    if (response.getInt("code") == 0) {
                        org.json.JSONObject jsonObject = response.getJSONObject("dataObject");
                        DBLog.e("转账", response.toString());
                        if (response.getInt("code") == 0) {
                            CCPAppManager.startChattingAction(RechargeSuccedActicity.this, jsonObject.getString("phoneNumber"), jsonObject.getString("username"));
                        }
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

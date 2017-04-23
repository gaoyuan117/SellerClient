package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.RedpacketManager;
import com.kaichaohulian.baocms.UserInfoManager;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.IMChattingHelper;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.RedPacketConstant;
import com.kaichaohulian.baocms.entity.GroupDetail;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.manager.UIHelper;
import com.kaichaohulian.baocms.manager.Validator;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;
import com.yunzhanghu.redpacketsdk.bean.RedPacketInfo;
import com.yunzhanghu.redpacketsdk.constant.RPConstant;

import org.apache.http.Header;
import org.json.JSONObject;

import java.math.BigDecimal;

public class SendingRedBagActivity extends BaseActivity {
    private int sendingRedBagType = 0;
    private ImageView type;
    private EditText num_redbag;
    private EditText amountET;
    private EditText message_redbag;
    private TextView amountTV;
    private TextView title;
    private TextView account_tip;
    private TextView tishi_tip;
    private Button send_redbag;
    private View back;
    String sessionId;
    private boolean fromChat = false;
    RedPacketInfo redPacketInfo;

    private int redbagNum = 1;
    String chatGroupId;
    String isGroup;

    private static final int VERIFY_PASSWORD_REQUEST = 7;
    private static final int SET_PASSWORD_REQUEST = 8;
    private RelativeLayout layout_num;

    public static final String IS_FROM_PCK = "IS_FROM_PCK";
    public static final String IS_SINGLE_SESSION = "IS_SINGLE_SESSION";
    private boolean mIsFromPck;
    private boolean mIsSingleSession;
    private String mGroupSelectName;
    private String mGroupSelectId;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_sending_red_bag);
    }

    @Override
    public void initData() {
        mIsFromPck = getIntent().getBooleanExtra(SendingRedBagActivity.IS_FROM_PCK, false);
        mIsSingleSession = getIntent().getBooleanExtra(SendingRedBagActivity.IS_SINGLE_SESSION, false);
        chatGroupId = getIntent().getStringExtra("sessionid");
        mGroupSelectName = getIntent().getStringExtra("groupname");
        mGroupSelectId = getIntent().getStringExtra("groupid");
        sessionId = chatGroupId;

        sendingRedBagType = getIntent().getIntExtra("type", 0);
        fromChat = getIntent().getBooleanExtra("fromchat", false);
        redPacketInfo = getIntent().getParcelableExtra(RPConstant.EXTRA_RED_PACKET_INFO);
        if (TextUtils.isEmpty(chatGroupId)) {
            chatGroupId = getIntent().getStringExtra(RedPacketConstant.KEY_GROUP_ID);
        }
        if (TextUtils.isEmpty(chatGroupId)) {
            sessionId = getIntent().getStringExtra(RedPacketConstant.KEY_GROUP_ID);
        }
        isGroup = getIntent().getStringExtra("isGroup");
    }

    @Override
    public void initView() {
        type = getId(R.id.type);
        title = getId(R.id.tv_title);
        num_redbag = getId(R.id.num_redbag);
        amountET = getId(R.id.all_redbag);
        message_redbag = getId(R.id.message_redbag);
        num_redbag = getId(R.id.num_redbag);
        amountTV = getId(R.id.acount_all);
        send_redbag = getId(R.id.send_redbag);
        back = getId(R.id.iv_back);
        account_tip = getId(R.id.account_tip);
        tishi_tip = getId(R.id.tishi_tip);
        layout_num = getId(R.id.layout_num);
    }

    @Override
    public void initEvent() {
        //普通红包
        if (sendingRedBagType == 0) {
            layout_num.setVisibility(View.GONE);
            title.setText("普通红包");
            type.setVisibility(View.INVISIBLE);
            account_tip.setText("金额");
            tishi_tip.setVisibility(View.GONE);
            redbagNum = 1;
        } else {
            layout_num.setVisibility(View.VISIBLE);
            title.setText("拼手气红包");
            tishi_tip.setVisibility(View.VISIBLE);
            tishi_tip.setText("每人可领1个，金额相同且不超过200元");
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        amountET.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
        amountET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String msgbody = amountET.getText().toString().trim();
                amountTV.setText("¥" + msgbody);
                if (TextUtils.isEmpty(msgbody)) {
                    return;
                }
                if (msgbody.startsWith("0")) {
                    amountET.setText("");
                    return;
                }
            }
        });

        num_redbag.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(num_redbag.getText())) {
                    redbagNum = Integer.valueOf(num_redbag.getText().toString());
                }
            }
        });

        send_redbag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(amountET.getText())) {
                    showToastMsg("请输入红包总额");
                    return;
                }

                //TODO 跳过支付码，正式请打开下方验证逻辑
                sendRedPacket();

//                if (MyApplication.getInstance().UserInfo.getPayPassword() == null) {
//                    Intent intent = new Intent();
//                    intent.setClass(getActivity(), SetPasswordActivity.class);
//                    startActivityForResult(intent, SET_PASSWORD_REQUEST);
//                } else {
//                    double price = Double.valueOf(amountET.getText().toString());
//                    Intent intent = new Intent();
//                    intent.setClass(getActivity(), VerifyPasswordActivity.class);
//                    intent.putExtra("price", price);
//                    startActivityForResult(intent, VERIFY_PASSWORD_REQUEST);
//                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case VERIFY_PASSWORD_REQUEST:
//                    showToastMsg("充值成功！");
                    if (fromChat) {
                        if (sendingRedBagType == 0) {
                            sendRedPacket();
                        } else {
                            getData();
                        }
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("mount", amountET.getText().toString());
                        ActivityUtil.next(SendingRedBagActivity.this, SendingRedBagNextActivity.class, bundle, 2);
                    }
                    break;
                case SET_PASSWORD_REQUEST:
                    DBLog.e("RechargeActivity", "设置密码成功");
                    break;
                case 2:
                    sessionId = intent.getStringExtra("mRecipients");
                    if (sessionId.toLowerCase().startsWith("g")) {
                        sendingRedBagType = 1;
                        chatGroupId = sessionId;
                        isGroup = "1";
                        getData();
                    } else {
                        sendingRedBagType = 0;
                        isGroup = "0";
                        sendRedPacket();
                    }
                    break;

            }

        } else {
            showToastMsg("密码错误！发红包失败！");
        }
    }

    /**
     * TODO 临时绕开支付验证环节
     */
    private void sendRedPacket() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("redCount", redbagNum);
        params.put("sum", new BigDecimal(amountET.getText().toString()));
        params.put("type", sendingRedBagType);
        params.put("token", MyApplication.getInstance().UserInfo.getToken());

        if (!TextUtils.isEmpty(chatGroupId)) {
            params.put("chatGroupId", chatGroupId);
        }
        params.put("isGroup", isGroup);
        if (!TextUtils.isEmpty(message_redbag.getText())) {
            params.put("message", message_redbag.getText().toString());
        } else {
            params.put("message", "恭喜发财，大吉大利!");
        }
        params.put("moneyType", "SMALLREDUCE");
        HttpUtil.post(Url.moneyreds_add, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                UserInfoManager.getInstance().updateUserCache(getActivity());
                try {
                    DBLog.e("获取红包信息：", response.toString());
                    if (response != null) {
                        if (Validator.isTokenIllegal(response.getString("errorDescription"))) {
                            UIHelper.reloginPage(getActivity());
                            return;
                        }
                    }
                    if (response.getInt("code") == 0) {
                        JSONObject jsonObject = response.getJSONObject("dataObject");
                        int redId = jsonObject.getInt("redId");

                        Intent intent = new Intent();
                        intent.putExtra("type", sendingRedBagType);
                        if (redPacketInfo != null) {
                            redPacketInfo.totalMoney = amountET.getText().toString();
                            redPacketInfo.redPacketGreeting = message_redbag.getText().toString();
                            intent.putExtra(RPConstant.EXTRA_RED_PACKET_INFO, redPacketInfo);
                            intent.putExtra(RedPacketConstant.KEY_FROM_AVATAR_URL, redPacketInfo.fromAvatarUrl);
                            intent.putExtra(RedPacketConstant.KEY_FROM_NICK_NAME, redPacketInfo.fromNickName);
                        }
                        if (TextUtils.isEmpty(message_redbag.getText())) {
                            intent.putExtra(RedPacketConstant.EXTRA_RED_PACKET_GREETING, "恭喜发财，大吉大利!");
                            RedpacketManager.greeting = "恭喜发财，大吉大利!";
                        } else {
                            intent.putExtra(RedPacketConstant.EXTRA_RED_PACKET_GREETING, message_redbag.getText());
                            RedpacketManager.greeting = message_redbag.getText().toString();
                        }
                        intent.putExtra(RedPacketConstant.EXTRA_RED_PACKET_ID, redId);
                        RedpacketManager.redid = redId;

                        //TODO modify
                        if (fromChat) {
                            RedpacketManager.redpacketCacheData = intent;
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            handlesendRedPacketMessage(intent, sessionId);
                            finish();
                        }

                        if (mIsSingleSession) {
                            getUserInfo("" + sessionId);
                            return;
                        }
                        if (mIsFromPck) {
                            CCPAppManager.startGroupChattingAction(SendingRedBagActivity.this, sessionId,
                                    mGroupSelectName, Integer.parseInt(mGroupSelectId), false);
                        }
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
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }

    public void getUserInfo(final String phone) {
        RequestParams params = new RequestParams();
        params.put("phoneNumber", phone);
        HttpUtil.post(Url.dependPhoneGetUserInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
                        int id = response.getInt("id");
                        String name = response.getString("username");
                        CCPAppManager.startChattingAction(SendingRedBagActivity.this, phone, name);
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
                ToastUtil.showMessage("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void handlesendRedPacketMessage(Intent data, String mRecipients) {
        String greetings = RedpacketManager.greeting;
        int moneyID = data.getIntExtra(RedPacketConstant.EXTRA_RED_PACKET_ID, -1);
        String specialReceiveId = data.getStringExtra(RedPacketConstant.EXTRA_RED_PACKET_RECEIVER_ID);
        String redPacketType = data.getStringExtra(RedPacketConstant.EXTRA_RED_PACKET_TYPE);
        String text = "[买家网]" + greetings;

        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        jsonObject.put(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, true);//是否是红包消息
        jsonObject.put(RedPacketConstant.EXTRA_SPONSOR_NAME, "买家红包");//红包sponsor name
        jsonObject.put(RedPacketConstant.EXTRA_RED_PACKET_GREETING, greetings);//祝福语
        jsonObject.put(RedPacketConstant.EXTRA_RED_PACKET_ID, moneyID);//红包id
        jsonObject.put(RedPacketConstant.MESSAGE_ATTR_RED_PACKET_TYPE, redPacketType);//红包类型，是否是专属红包
        jsonObject.put(RedPacketConstant.MESSAGE_ATTR_SPECIAL_RECEIVER_ID, specialReceiveId);//指定接收者
        // 组建一个待发送的ECMessage
        ECMessage msg = ECMessage.createECMessage(ECMessage.Type.TXT);
        // 设置消息接收者
        msg.setTo(mRecipients);
        msg.setUserData(jsonObject.toJSONString());
        // 创建一个文本消息体，并添加到消息对象中
        ECTextMessageBody msgBody = new ECTextMessageBody(text.toString());
        msg.setBody(msgBody);
//        String[] at = mChattingFooter.getAtSomeBody();
//        msgBody.setAtMembers(at);
//        mChattingFooter.clearSomeBody();
        try {
            // 发送消息，该函数见上
            long rowId = IMChattingHelper.sendECMessage(msg);

            // 通知列表刷新
            msg.setId(rowId);
//            notifyIMessageListView(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("chatGroupId", sessionId);
        HttpUtil.post(Url.detailByChatId, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("群详情", response.toString());
                    GroupDetail detail = JSON.parseObject(response.toString(), GroupDetail.class);
                    if (detail.code.equals("0")) {
                        GroupDetail.DataObject dataObject = detail.dataObject;
                        chatGroupId = dataObject.id + "";
                        sendRedPacket();
                    } else {
                        showToastMsg("获取失败");
                    }
                } catch (Exception e) {
                    showToastMsg("获取失败");
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
}

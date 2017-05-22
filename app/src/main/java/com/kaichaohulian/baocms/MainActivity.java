package com.kaichaohulian.baocms;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.activity.ChatActivity;
import com.kaichaohulian.baocms.activity.LoginActivity;
import com.kaichaohulian.baocms.app.AppManager;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseEcActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.dialog.ECAlertDialog;
import com.kaichaohulian.baocms.ecdemo.common.dialog.ECProgressDialog;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECNotificationManager;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferenceSettings;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.ecdemo.core.ClientUser;
import com.kaichaohulian.baocms.ecdemo.core.ContactsCache;
import com.kaichaohulian.baocms.ecdemo.storage.ContactSqlManager;
import com.kaichaohulian.baocms.ecdemo.storage.ConversationSqlManager;
import com.kaichaohulian.baocms.ecdemo.storage.GroupNoticeSqlManager;
import com.kaichaohulian.baocms.ecdemo.storage.GroupSqlManager;
import com.kaichaohulian.baocms.ecdemo.storage.IMessageSqlManager;
import com.kaichaohulian.baocms.ecdemo.ui.SDKCoreHelper;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.CustomerServiceHelper;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.IMChattingHelper;
import com.kaichaohulian.baocms.ecdemo.ui.contact.ContactLogic;
import com.kaichaohulian.baocms.ecdemo.ui.contact.ECContacts;
import com.kaichaohulian.baocms.ecdemo.ui.group.GroupNoticeActivity;
import com.kaichaohulian.baocms.ecdemo.ui.group.GroupService;
import com.kaichaohulian.baocms.ecdemo.ui.settings.SettingPersionInfoActivity;
import com.kaichaohulian.baocms.entity.GroupEntity;
import com.kaichaohulian.baocms.entity.MessageEntity;
import com.kaichaohulian.baocms.entity.QiNiuConfigEntity;
import com.kaichaohulian.baocms.fragment.ContactListFragment;
import com.kaichaohulian.baocms.fragment.DiscoverFragment;
import com.kaichaohulian.baocms.fragment.FindFragment;
import com.kaichaohulian.baocms.fragment.HomeFragment;
import com.kaichaohulian.baocms.fragment.ProFileFragment;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.kaichaohulian.baocms.view.AddPopWindow;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yuntongxun.ecsdk.ECChatManager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.ECVoIPCallManager;
import com.yuntongxun.ecsdk.OnChatReceiveListener;
import com.yuntongxun.ecsdk.OnMeetingListener;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.ecsdk.VideoRatio;
import com.yuntongxun.ecsdk.VoipMediaChangedInfo;
import com.yuntongxun.ecsdk.im.ECGroup;
import com.yuntongxun.ecsdk.im.ECMessageNotify;
import com.yuntongxun.ecsdk.im.group.ECGroupNoticeMessage;
import com.yuntongxun.ecsdk.meeting.intercom.ECInterPhoneMeetingMsg;
import com.yuntongxun.ecsdk.meeting.video.ECVideoMeetingMsg;
import com.yuntongxun.ecsdk.meeting.voice.ECVoiceMeetingMsg;
import com.yuntongxun.ecsdk.platformtools.ECHandlerHelper;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 主界面
 * Created by ljl on 2016/12/11.
 */
@SuppressLint("ValidFragment")
public class MainActivity extends BaseEcActivity implements HomeFragment.OnUpdateMsgUnreadCountsListener, GroupService.Callback {
    private Fragment[] fragments;
    private HomeFragment homefragment;
    private ContactListFragment contactlistfragment;
    private DiscoverFragment findfragment;
    private ProFileFragment profilefragment;

    private ImageView[] imagebuttons;
    private TextView[] textviews;

    private int index;
    // 当前fragment的index
    private int currentTabIndex;

    private long mExitTime;
    private MyApplication myApplication;

    // 未读消息textview
    public TextView unreadLabel;
    // 未读通讯录textview
    public TextView unreadAddressLable;


    // 账号在别处登录
    public static boolean isConflict = false;
    private DataHelper mDataHelper;
    private boolean sync = false;
    private ImageView iv_add;

//  on

    @Override
    public void initData() {
//        long expireddate = (long) SPUtils.get(getActivity(), "expireddate", 0l);
//        long current = new Date().getTime();
//        double duration = (current - expireddate) / 1000 / 60 / 60 / 24;
//        if (duration > 10) {
//            throw new RuntimeException("date expired !!! pls regist");
//        }
//        Log.e("TRACE", "expired first : " + expireddate);
//        Log.e("TRACE", "expired curr : " + current);
//        Log.e("TRACE", "expired duration : " + duration);
        startService(new Intent(this,UpdateLocationService.class));
        mDataHelper = new DataHelper(getActivity());
        myApplication = (MyApplication) getApplication();
        if (MyApplication.getInstance() != null && MyApplication.getInstance().UserInfo != null) {
            String account = getAutoRegistAccount();//账号信息，包括id，appkey，token等
            // 注册第一次登陆同步消息
            registeReceiver(new String[]{
                    IMChattingHelper.INTENT_ACTION_SYNC_MESSAGE,
                    SDKCoreHelper.ACTION_SDK_CONNECT});
            ClientUser user = new ClientUser("").from(account);
            CCPAppManager.setClientUser(user);
            if (!ContactSqlManager.hasContact(user.getUserId())) {
                ECContacts contacts = new ECContacts();
                contacts.setClientUser(user);
                ContactSqlManager.insertContact(contacts);
            }

            if (SDKCoreHelper.getConnectState() != ECDevice.ECConnectState.CONNECT_SUCCESS
                    && !SDKCoreHelper.isKickOff()) {

                ContactsCache.getInstance().load();

                SDKCoreHelper.init(this);
            }

            registerReceiver(new String[]{getActivity().getPackageName() + ".inited", IMessageSqlManager.ACTION_GROUP_DEL});
//        initYTX();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        RetrofitClient.getInstance().createApi().GetQiNiuConfig().compose(RxUtils.<HttpResult<QiNiuConfigEntity>>io_main())
                .subscribe(new BaseObjObserver<QiNiuConfigEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(QiNiuConfigEntity qiNiuConfigEntity) {
//                        Log.e(TAG, "onHandleSuccess: "+qiNiuConfigEntity.toString() );
//                            QiNiuConfig.QINIU_DOMAIN=qiNiuConfigEntity.domain;
//                            QiNiuConfig.QINIU_BUCKET=qiNiuConfigEntity.bucket;

                    }
                });


    }

//    /**
//     * 支付宝支付
//     */
//    private static Handler payHandler = new Handler() {
//        @SuppressWarnings("unused")
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case PayTreasureUtils.SDK_PAY_FLAG: {
//                    PayResult payResult = new PayResult((String) msg.obj);
//                    /**
//                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
//                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
//                     * docType=1) 建议商户依赖异步通知
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        ActivityUtil.next(paymentOrderActivity, PaySuccessActivity.class);
//                    } else {
//                        // 判断resultStatus 为非"9000"则代表可能支付失败
//                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
//                        if (TextUtils.equals(resultStatus, "8000")) {
//                            DBLog.showToast("支付结果确认中", paymentOrderActivity);
//                        } else {
//                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            DBLog.showToast("支付失败", paymentOrderActivity);
//                        }
//                    }
//                    break;
//                }
//                default:
//                    break;
//            }
//        }
//    };

    @Override
    public void initView() {

        setCenterTitle("消息");
        visibilityExit();
        iv_add = setIm1_view(R.mipmap.add_friend);
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPopWindow addPopWindow = new AddPopWindow(MainActivity.this);
                addPopWindow.showPopupWindow(iv_add);
            }
        });

        unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);

        homefragment = new HomeFragment(myApplication, MainActivity.this, MainActivity.this);
        contactlistfragment = new ContactListFragment(myApplication, MainActivity.this, MainActivity.this);
        findfragment = new DiscoverFragment(myApplication, MainActivity.this, MainActivity.this);
        profilefragment = new ProFileFragment(myApplication, MainActivity.this, MainActivity.this);

        fragments = new Fragment[]{homefragment, contactlistfragment, findfragment, profilefragment};
        imagebuttons = new ImageView[4];
        imagebuttons[0] = getId(R.id.ib_weixin);
        imagebuttons[1] = getId(R.id.ib_contact_list);
        imagebuttons[2] = getId(R.id.ib_find);
        imagebuttons[3] = getId(R.id.ib_profile);

        imagebuttons[0].setSelected(true);
        textviews = new TextView[4];
        textviews[0] = (TextView) findViewById(R.id.tv_weixin);
        textviews[1] = (TextView) findViewById(R.id.tv_contact_list);
        textviews[2] = (TextView) findViewById(R.id.tv_find);
        textviews[3] = (TextView) findViewById(R.id.tv_profile);
        textviews[0].setTextColor(0xFFfb7b12);
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homefragment)
                .add(R.id.fragment_container, contactlistfragment)
                .add(R.id.fragment_container, profilefragment)
                .add(R.id.fragment_container, findfragment)
                .hide(contactlistfragment).hide(profilefragment)
                .hide(findfragment).show(homefragment).commit();
    }

    @Override
    public void initEvent() {

    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.re_weixin:
                index = 0;
                iv_add.setVisibility(View.VISIBLE);
                setCenterTitle("消息");
                break;
            case R.id.re_contact_list:
                index = 1;
                iv_add.setVisibility(View.VISIBLE);
                setCenterTitle("通讯录");
                break;
            case R.id.re_find:
                index = 2;
                iv_add.setVisibility(View.VISIBLE);
                setCenterTitle("发现");
                break;
            case R.id.re_profile:
                UserInfoManager.getInstance().updateUserCache(getActivity());
                index = 3;
                iv_add.setVisibility(View.INVISIBLE);
                setCenterTitle("我的");
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        imagebuttons[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        imagebuttons[index].setSelected(true);
        textviews[currentTabIndex].setTextColor(0xFF646464);
        textviews[index].setTextColor(0xFFfb7b12);
        currentTabIndex = index;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2500) {
                DBLog.showToast("再按一次退出程序", MainActivity.this);
                mExitTime = System.currentTimeMillis();
                return false;
            } else {
                AppManager.getAppManager().finishAllActivity();
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initYTX() {
//        DBLog.i(TAG, MyApplication.getInstance().UserInfo.toString());
        ECInitParams params = ECInitParams.createParams();
        params.setUserid(MyApplication.getInstance().UserInfo.getPhoneNumber() + "");
        params.setAppKey("8a216da858ce0b3c0158d8b1e2a00840");
        params.setToken("0c54dbad19d314dd37fb4b4872a41529");
        //设置登陆验证模式：自定义登录方式
        params.setAuthType(ECInitParams.LoginAuthType.NORMAL_AUTH);
        //LoginMode（强制上线：FORCE_LOGIN  默认登录：AUTO。使用方式详见注意事项）
        params.setMode(ECInitParams.LoginMode.FORCE_LOGIN);

        //设置登录回调监听
        ECDevice.setOnDeviceConnectListener(new ECDevice.OnECDeviceConnectListener() {
            public void onConnect() {
                //兼容旧版本的方法，不必处理
            }

            @Override
            public void onDisconnect(ECError error) {
                //兼容旧版本的方法，不必处理
            }

            @Override
            public void onConnectState(ECDevice.ECConnectState state, ECError error) {
                if (state == ECDevice.ECConnectState.CONNECT_FAILED) {
                    if (error.errorCode == SdkErrorCode.SDK_KICKED_OFF) {
                        DBLog.i(TAG, "==帐号异地登陆");
                        isConflict = false;
                    } else {
                        DBLog.i(TAG, "==其他登录失败,错误码：" + error.errorCode);
                        isConflict = false;
                    }
                    return;
                } else if (state == ECDevice.ECConnectState.CONNECT_SUCCESS) {
                    DBLog.i(TAG, "==登陆成功");
                    isConflict = true;
                }
            }
        });

        //IM接收消息监听，使用IM功能的开发者需要设置。
        ECDevice.setOnChatReceiveListener(new OnChatReceiveListener() {
            @Override
            public void OnReceivedMessage(ECMessage msg) {
                DBLog.i("TRACE", "==收到新消息===" + msg.toString());
                MessageEntity Message = new MessageEntity();
                Message.setMsgId(msg.getMsgId());
                Message.setToId(msg.getForm());
                Message.setName(msg.getNickName());
                Message.setAvatar("");
                Message.setCreatedTime(msg.getMsgTime() + "");
                Message.setIsRend(msg.isRead() ? 0 : 1);
                Message.setIsSend(msg.getMsgStatus().name());
                Message.setIsTop(1);
                Message.setMessage(msg.getBody().toString());
                Message.setType(msg.getType().name());
                Message.setDirection(msg.getDirection().name());
                Message.setMyUserId(MyApplication.getInstance().UserInfo.getUserId() + "");
                Intent Intent = new Intent();
                Intent.setAction(ChatActivity.BROADCAST_ACTION);
                Intent.putExtra("message", Message);
                sendBroadcast(Intent);
                mDataHelper.SaveMessage(Message);
            }

            @Override
            public void onReceiveMessageNotify(ECMessageNotify ecMessageNotify) {

            }

            @Override
            public void OnReceiveGroupNoticeMessage(ECGroupNoticeMessage notice) {
                //收到群组通知消息,可以根据ECGroupNoticeMessage.ECGroupMessageType类型区分不同消息类型
                DBLog.i(TAG, "==收到群组通知消息（有人加入、退出...）");
            }

            @Override
            public void onOfflineMessageCount(int count) {
                // 登陆成功之后SDK回调该接口通知帐号离线消息数
            }

            @Override
            public int onGetOfflineMessage() {
                return 0;
            }

            @Override
            public void onReceiveOfflineMessage(List msgs) {
                // SDK根据应用设置的离线消息拉取规则通知应用离线消息
            }

            @Override
            public void onReceiveOfflineMessageCompletion() {
                // SDK通知应用离线消息拉取完成
            }

            @Override
            public void onServicePersonVersion(int version) {
                // SDK通知应用当前帐号的个人信息版本号
            }

            @Override
            public void onReceiveDeskMessage(ECMessage ecMessage) {

            }

            @Override
            public void onSoftVersion(String s, int i) {

            }
        });

        // 语音通话状态监听，使用语音通话功能的开发者需要设置。
        ECVoIPCallManager callInterface = ECDevice.getECVoIPCallManager();
        if (callInterface != null) {
            callInterface.setOnVoIPCallListener(new ECVoIPCallManager.OnVoIPListener() {
                @Override
                public void onVideoRatioChanged(VideoRatio videoRatio) {

                }

                @Override
                public void onSwitchCallMediaTypeRequest(String s, ECVoIPCallManager.CallType callType) {

                }

                @Override
                public void onSwitchCallMediaTypeResponse(String s, ECVoIPCallManager.CallType callType) {

                }

                @Override
                public void onDtmfReceived(String s, char c) {

                }

                @Override
                public void onCallEvents(ECVoIPCallManager.VoIPCall voipCall) {
                    // 处理呼叫事件回调
                    if (voipCall == null) {
                        DBLog.e("SDKCoreHelper" + TAG, "handle call event error , voipCall null");
                        return;
                    }
                    // 根据不同的事件通知类型来处理不同的业务
                    ECVoIPCallManager.ECCallState callState = voipCall.callState;
                    switch (callState) {
                        case ECCALL_PROCEEDING:
                            DBLog.i(TAG, "正在连接服务器处理呼叫请求，callid：" + voipCall.callId);
                            break;
                        case ECCALL_ALERTING:
                            DBLog.i(TAG, "呼叫到达对方，正在振铃，callid：" + voipCall.callId);
                            break;
                        case ECCALL_ANSWERED:
                            DBLog.i(TAG, "对方接听本次呼叫,callid：" + voipCall.callId);
                            break;
                        case ECCALL_FAILED:
                            // 本次呼叫失败，根据失败原因进行业务处理或跳转
                            DBLog.i(TAG, "called:" + voipCall.callId + ",reason:" + voipCall.reason);
                            break;
                        case ECCALL_RELEASED:
                            // 通话释放[完成一次呼叫]
                            break;
                        default:
                            DBLog.e("SDKCoreHelper" + TAG, "handle call event error , callState " + callState);
                            break;
                    }
                }

                @Override
                public void onMediaDestinationChanged(VoipMediaChangedInfo voipMediaChangedInfo) {

                }
            });
        }

        //音视频会议回调监听，使用音视频会议功能的开发者需要设置。
        if (ECDevice.getECMeetingManager() != null) {
            ECDevice.getECMeetingManager().setOnMeetingListener(new OnMeetingListener() {
                @Override
                public void onVideoRatioChanged(VideoRatio videoRatio) {

                }

                @Override
                public void onReceiveInterPhoneMeetingMsg(ECInterPhoneMeetingMsg msg) {
                    // 处理实时对讲消息Push
                }

                @Override
                public void onReceiveVoiceMeetingMsg(ECVoiceMeetingMsg msg) {
                    // 处理语音会议消息push
                }

                @Override
                public void onReceiveVideoMeetingMsg(ECVideoMeetingMsg msg) {
                    // 处理视频会议消息Push（暂未提供）
                }

                @Override
                public void onMeetingPermission(String s) {

                }
            });
        }

        //验证参数是否正确
        if (params.validate()) {
            // 登录函数
            ECDevice.login(params);
        }
    }

    /**
     * 处理一些初始化操作
     */
    private void doInitAction() {
        if (SDKCoreHelper.getConnectState() == ECDevice.ECConnectState.CONNECT_SUCCESS
                ) {

            String account = getAutoRegistAccount();
            if (!TextUtils.isEmpty(account)) {
                ClientUser user = new ClientUser("").from(account);
                CCPAppManager.setClientUser(user);
            }
//            settingPersonInfo();
            IMChattingHelper.getInstance().getPersonInfo();
            // 检测离线消息
            checkOffineMessage();
            mInitActionFlag = true;

            GroupService.syncGroup(this);
        }
    }

    private void settingPersonInfo() {
        if (isUpSetPersonInfo()) {
            Intent settingAction = new Intent(this, SettingPersionInfoActivity.class);
            settingAction.putExtra("from_regist", true);
            startActivityForResult(settingAction, 0x2a);
        }
    }

    public static boolean isUpSetPersonInfo() {
        ClientUser user = CCPAppManager.getClientUser();
        return (user != null && user.getpVersion() == 0);
    }

    /**
     * 检测离线消息
     */
    private void checkOffineMessage() {
        if (SDKCoreHelper.getConnectState() != ECDevice.ECConnectState.CONNECT_SUCCESS) {
            return;
        }
        ECHandlerHelper handlerHelper = new ECHandlerHelper();
        handlerHelper.postDelayedRunnOnThead(new Runnable() {
            @Override
            public void run() {
                boolean result = IMChattingHelper.isSyncOffline();
                if (!result) {
                    ECHandlerHelper.postRunnOnUI(new Runnable() {
                        @Override
                        public void run() {
                            disPostingLoading();
                        }
                    });
                    IMChattingHelper.checkDownFailMsg();
                }
            }
        }, 1000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (internalReceiver != null) {
            unregisterReceiver(internalReceiver);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intentGroup = new Intent();
        intentGroup.setAction("com.kaichaohulian.baocms.inited");
        sendBroadcast(intentGroup);

    }

    @Override
    protected void onResume() {
        LogUtil.i(LogUtil.getLogUtilsTag(MainActivity.class),
                "onResume start");
        super.onResume();
        if (MyApplication.getInstance() == null || MyApplication.getInstance().UserInfo == null)
            return;

        Intent intentGroup = new Intent();
        intentGroup.setAction("com.kaichaohulian.baocms.inited");
        sendBroadcast(intentGroup);

        // 统计时长
//		MobclickAgent.onResume(this);

        OnUpdateMsgUnreadCounts();
        getTopContacts();

        getData();

        if (!sync) {
            GroupService.syncGroup(this);
            sync = true;
        }


    }

    private void getTopContacts() {
        final ArrayList<String> arrayList = ConversationSqlManager.getInstance().qureyAllSession();
        ECChatManager chatManager = ECDevice.getECChatManager();
        if (chatManager == null) {
            return;
        }
        chatManager.getSessionsOfTop(new ECChatManager.OnGetSessionsOfTopListener() {
            @Override
            public void onGetSessionsOfTopResult(ECError error, String[] sessionsArr) {
                if (error.errorCode == SdkErrorCode.REQUEST_SUCCESS) {
                    for (String item : sessionsArr) {
                        ConversationSqlManager.updateSessionToTop(item, true);
                    }
                    List<String> list = Arrays.asList(sessionsArr);
                    for (String a : arrayList) {
                        if (!list.contains(a)) {
                            ConversationSqlManager.updateSessionToTop(a, false);
                        }
                    }
                }
            }
        });
    }

    public void handlerKickOff(String kickoffText) {
        if (isFinishing()) {
            return;
        }
        ECAlertDialog buildAlert = ECAlertDialog.buildAlert(this, kickoffText,
                getString(R.string.dialog_btn_confim),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ECNotificationManager.getInstance()
                                .forceCancelNotification();
                        restartAPP();
                    }
                });
        buildAlert.setTitle("异地登陆");
        buildAlert.setCanceledOnTouchOutside(false);
        buildAlert.setCancelable(false);
        buildAlert.show();
    }

    public void restartAPP() {

        ECDevice.unInitial();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 检查是否需要自动登录
     *
     * @return
     */
    private String getAutoRegistAccount() {

        String registAccount = SharedPrefsUtil.getValue(getActivity(), ECPreferenceSettings.SETTINGS_REGIST_AUTO.getId(), null);

        return registAccount;
    }


    @Override
    protected void onPause() {
        LogUtil.d(LogUtil.getLogUtilsTag(getClass()), "KEVIN Launcher onPause");
        super.onPause();
        // 友盟统计API
//		MobclickAgent.onPause(this);
    }

    /**
     * 返回隐藏到后台
     */
    public void doTaskToBackEvent() {
        moveTaskToBack(true);

    }

    @Override
    public void OnUpdateMsgUnreadCounts() {
        int unreadCount = IMessageSqlManager.qureyAllSessionUnreadCount();
        int notifyUnreadCount = IMessageSqlManager.getUnNotifyUnreadCount();
        int count = unreadCount;
        if (unreadCount >= notifyUnreadCount) {
            count = unreadCount - notifyUnreadCount;
        }
//        if (mLauncherUITabView != null) {
//            mLauncherUITabView.updateMainTabUnread(count);
//        }
    }


    /**
     * 网络注册状态改变
     *
     * @param connect
     */
    public void onNetWorkNotify(ECDevice.ECConnectState connect) {
        if (homefragment != null && homefragment.isAdded()) {
            homefragment.updateConnectState();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Intent actionIntent = intent;
        String userName = actionIntent.getStringExtra("Main_FromUserName");
        String mSession = actionIntent.getStringExtra("Main_Session");
        ECContacts contacts = ContactSqlManager.getContactLikeUsername(userName);
        if (contacts != null) {
            LogUtil.d(LogUtil.getLogUtilsTag(getClass()), "[onNewIntent] userName = " + userName + " , contact_id " + contacts.getContactid());

            if (GroupNoticeSqlManager.CONTACT_ID.equals(contacts.getContactid())) {
                Intent noticeintent = new Intent(this, GroupNoticeActivity.class);
                startActivity(noticeintent);
                return;
            }

            Intent chatIntent = new Intent(this, ChattingActivity.class);
            String recipinets;
            String username;
            if (!TextUtils.isEmpty(mSession) && mSession.startsWith("g")) {
                ECGroup ecGroup = GroupSqlManager.getECGroup(mSession);
                if (ecGroup == null) {
                    return;
                }
                recipinets = mSession;
                username = ecGroup.getName();
            } else {
                recipinets = contacts.getContactid();
                username = contacts.getNickname();
            }
            startActivity(chatIntent);
            CCPAppManager.startChattingAction(this, recipinets, username);
            return;
        }
    }


    /**
     * 在线客服
     */
    private void handleStartServiceEvent() {
        showProcessDialog();
        CustomerServiceHelper.startService(ContactLogic.CUSTOM_SERVICE,
                new CustomerServiceHelper.OnStartCustomerServiceListener() {
                    @Override
                    public void onError(ECError error) {
                        dismissPostingDialog();
                    }

                    @Override
                    public void onServiceStart(String event) {
                        dismissPostingDialog();
                        CCPAppManager.startCustomerServiceAction(
                                MainActivity.this, event);
                    }
                });
    }

    private InternalReceiver internalReceiver;

    /**
     * 注册广播
     *
     * @param actionArray
     */
    protected final void registeReceiver(String[] actionArray) {
        if (actionArray == null) {
            return;
        }
        IntentFilter intentfilter = new IntentFilter();
        for (String action : actionArray) {
            intentfilter.addAction(action);
        }
        if (internalReceiver == null) {
            internalReceiver = new InternalReceiver();
        }
        registerReceiver(internalReceiver, intentfilter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void handleReceiver(Context context, Intent intent) {
        super.handleReceiver(context, intent);
        if (intent.getAction().equals(new String[]{getActivity().getPackageName() + ".inited"})) {
            GroupService.syncGroup(this);
        } else if (IMessageSqlManager.ACTION_GROUP_DEL.equals(intent.getAction())) {
            onSyncGroup();
        }
    }


    @Override
    public void onSyncGroup() {

    }

    @Override
    public void onSyncGroupInfo(String groupId) {

    }

    @Override
    public void onGroupDel(String groupId) {
        onSyncGroup();
    }

    @Override
    public void onError(ECError error) {

    }

    @Override
    public void onUpdateGroupAnonymitySuccess(String groupId, boolean isAnonymity) {

    }

    private class InternalReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || TextUtils.isEmpty(intent.getAction())) {
                return;
            }
            LogUtil.d(TAG, "[onReceive] action:" + intent.getAction());
            if (IMChattingHelper.INTENT_ACTION_SYNC_MESSAGE.equals(intent.getAction())) {
                disPostingLoading();
            } else if (SDKCoreHelper.ACTION_SDK_CONNECT.equals(intent.getAction())) {
                doInitAction();
                // tetstMesge();
                if (homefragment != null && !homefragment.isFinish()) {
                    homefragment.updateConnectState();
                }
            } else if (SDKCoreHelper.ACTION_KICK_OFF.equals(intent.getAction())) {
                String kickoffText = intent.getStringExtra("kickoffText");
//                handlerKickOff(kickoffText);
            }
        }
    }

    private boolean mInitActionFlag;

    private void disPostingLoading() {
        if (mPostingdialog != null && mPostingdialog.isShowing()) {
            mPostingdialog.dismiss();
        }
    }

    ECAlertDialog showUpdaterTipsDialog = null;

    private ECProgressDialog mPostingdialog;

    void showProcessDialog() {
        mPostingdialog = new ECProgressDialog(MainActivity.this, R.string.login_posting_submit);
        mPostingdialog.show();
    }

    /**
     * 关闭对话框
     */
    private void dismissPostingDialog() {
        if (mPostingdialog == null || !mPostingdialog.isShowing()) {
            return;
        }
        mPostingdialog.dismiss();
        mPostingdialog = null;
    }

    @Override
    public int getTitleLayout() {
        return -1;
    }

    @Override
    protected boolean isEnableSwipe() {
        return false;
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        HttpUtil.post(Url.groups_all, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取我加入和我创建的群", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray jsonArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            GroupEntity GroupEntity = new GroupEntity();
                            JSONArray avatarArray = jsonObject.getJSONArray("images");
                            SharedPrefsUtil.putValue(getActivity(), jsonObject.getString("chatGroupId"), avatarArray.toString()
                                    + "-x-" + jsonObject.getString("name") + "-x-" + jsonObject.getInt("id"));
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
            }
        });
    }
}

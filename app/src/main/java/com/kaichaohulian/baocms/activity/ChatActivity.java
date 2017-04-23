package com.kaichaohulian.baocms.activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.baidu.mapapi.map.Text;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.ExpressionAdapter;
import com.kaichaohulian.baocms.adapter.ExpressionPagerAdapter;
import com.kaichaohulian.baocms.adapter.MessageAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.circledemo.utils.CommonUtils;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.entity.MessageEntity;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.SmileUtils;
import com.kaichaohulian.baocms.utils.VoiceRecorder;
import com.kaichaohulian.baocms.view.ExpandGridView;
import com.kaichaohulian.baocms.view.PasteEditText;
import com.yuntongxun.ecsdk.ECChatManager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.im.ECFileMessageBody;
import com.yuntongxun.ecsdk.im.ECImageMessageBody;
import com.yuntongxun.ecsdk.im.ECLocationMessageBody;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;
import com.yuntongxun.ecsdk.im.ECVoiceMessageBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 聊天
 * Created by ljl on 2016/12/28.
 */

public class ChatActivity extends BaseActivity implements View.OnClickListener {

    public static final String BROADCAST_ACTION = "ChatActivity";

    private static final int REQUEST_CODE_EMPTY_HISTORY = 2;
    public static final int REQUEST_CODE_CONTEXT_MENU = 3;
    private static final int REQUEST_CODE_MAP = 4;
    public static final int REQUEST_CODE_TEXT = 5;
    public static final int REQUEST_CODE_VOICE = 6;
    public static final int REQUEST_CODE_PICTURE = 7;
    public static final int REQUEST_CODE_LOCATION = 8;
    public static final int REQUEST_CODE_NET_DISK = 9;
    public static final int REQUEST_CODE_FILE = 10;
    public static final int REQUEST_CODE_COPY_AND_PASTE = 11;
    public static final int REQUEST_CODE_PICK_VIDEO = 12;
    public static final int REQUEST_CODE_DOWNLOAD_VIDEO = 13;
    public static final int REQUEST_CODE_VIDEO = 14;
    public static final int REQUEST_CODE_DOWNLOAD_VOICE = 15;
    public static final int REQUEST_CODE_SELECT_USER_CARD = 16;
    public static final int REQUEST_CODE_SEND_USER_CARD = 17;
    public static final int REQUEST_CODE_CAMERA = 18;
    public static final int REQUEST_CODE_LOCAL = 19;
    public static final int REQUEST_CODE_CLICK_DESTORY_IMG = 20;
    public static final int REQUEST_CODE_GROUP_DETAIL = 21;
    public static final int REQUEST_CODE_SELECT_VIDEO = 23;
    public static final int REQUEST_CODE_SELECT_FILE = 24;
    public static final int REQUEST_CODE_ADD_TO_BLACKLIST = 25;

    public static final int RESULT_CODE_COPY = 1;
    public static final int RESULT_CODE_DELETE = 2;
    public static final int RESULT_CODE_FORWARD = 3;
    public static final int RESULT_CODE_OPEN = 4;
    public static final int RESULT_CODE_DWONLOAD = 5;
    public static final int RESULT_CODE_TO_CLOUD = 6;
    public static final int RESULT_CODE_EXIT_GROUP = 7;

    public static final int CHATTYPE_SINGLE = 1;
    public static final int CHATTYPE_GROUP = 2;

    public static final String COPY_IMAGE = "EASEMOBIMG";
    private View recordingContainer;
    private ImageView micImage;
    private TextView recordingHint;
    private ListView listView;
    private PasteEditText mEditTextContent;
    private View buttonSetModeKeyboard;
    private View buttonSetModeVoice;
    private View buttonSend;
    private View buttonPressToSpeak;
    // private ViewPager expressionViewpager;
    private LinearLayout emojiIconContainer;
    private LinearLayout btnContainer;

    private View more;
    private ClipboardManager clipboard;
    private ViewPager expressionViewpager;
    private InputMethodManager manager;
    private List<String> reslist;
    private Drawable[] micImages;
    private int chatType;
    public static ChatActivity activityInstance = null;

    private VoiceRecorder voiceRecorder;
    private MessageAdapter adapter;
    private File cameraFile;
    public static int resendPos;

    private ImageView iv_emoticons_normal;
    private ImageView iv_emoticons_checked;
    private RelativeLayout edittext_layout;
    private ProgressBar loadmorePB;
    private boolean isloading;
    private final int pagesize = 20;
    private boolean haveMoreData = true;
    private Button btnMore;
    public String playMsgId;

    // 给谁发送消息
    private String toChatUsername;
    String myUserNick = "";
    String myUserAvatar = "";
    String toUserNick = "";
    String toUserAvatar = "";
    // 分享的照片
    String iamge_path = null;
    // 设置按钮
    private ImageView iv_setting;
    private ImageView iv_setting_group;

    private List<MessageEntity> conversation;

    private DataHelper mDataHelper;
    private ReceiveBroadCast mReceiveBroadCast;

    @SuppressLint("HandlerLeak")
    private Handler micImageHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            // 切换msg切换图片
            micImage.setImageDrawable(micImages[msg.what]);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        mReceiveBroadCast = new ReceiveBroadCast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_ACTION);
        registerReceiver(mReceiveBroadCast, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiveBroadCast);
    }


    @Override
    public void setContent() {
        setContentView(R.layout.activity_chat);
    }

    @Override
    public void initData() {
        mDataHelper = new DataHelper(getActivity());
        // 读取本地自己的头像和昵称
        myUserNick = MyApplication.getInstance().UserInfo.getUsername();
        myUserAvatar = MyApplication.getInstance().UserInfo.getAvatar();

        iamge_path = this.getIntent().getStringExtra("iamge_path");
        if (iamge_path != null && !iamge_path.equals("")) {
            sendPicture(iamge_path, true);
        }
    }

    private PowerManager.WakeLock wakeLock;

    @Override
    public void initView() {
        recordingContainer = findViewById(R.id.recording_container);
        micImage = (ImageView) findViewById(R.id.mic_image);
        recordingHint = (TextView) findViewById(R.id.recording_hint);
        listView = (ListView) findViewById(R.id.list);
        mEditTextContent = (PasteEditText) findViewById(R.id.et_sendmessage);
        buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
        edittext_layout = (RelativeLayout) findViewById(R.id.edittext_layout);
        buttonSetModeVoice = findViewById(R.id.btn_set_mode_voice);
        buttonSend = findViewById(R.id.btn_send);
        buttonPressToSpeak = findViewById(R.id.btn_press_to_speak);
        expressionViewpager = (ViewPager) findViewById(R.id.vPager);
        emojiIconContainer = (LinearLayout) findViewById(R.id.ll_face_container);
        btnContainer = (LinearLayout) findViewById(R.id.ll_btn_container);
        iv_emoticons_normal = (ImageView) findViewById(R.id.iv_emoticons_normal);
        iv_emoticons_checked = (ImageView) findViewById(R.id.iv_emoticons_checked);
        loadmorePB = (ProgressBar) findViewById(R.id.pb_load_more);
        btnMore = (Button) findViewById(R.id.btn_more);
        iv_emoticons_normal.setVisibility(View.VISIBLE);
        iv_emoticons_checked.setVisibility(View.INVISIBLE);
        more = findViewById(R.id.more);
        edittext_layout.setBackgroundResource(R.mipmap.input_bar_bg_normal);

        // 动画资源文件,用于录制语音时
        micImages = new Drawable[]{
                getResources().getDrawable(R.mipmap.record_animate_01),
                getResources().getDrawable(R.mipmap.record_animate_02),
                getResources().getDrawable(R.mipmap.record_animate_03),
                getResources().getDrawable(R.mipmap.record_animate_04),
                getResources().getDrawable(R.mipmap.record_animate_05),
                getResources().getDrawable(R.mipmap.record_animate_06),
                getResources().getDrawable(R.mipmap.record_animate_07),
                getResources().getDrawable(R.mipmap.record_animate_08),
                getResources().getDrawable(R.mipmap.record_animate_09),
                getResources().getDrawable(R.mipmap.record_animate_10),
                getResources().getDrawable(R.mipmap.record_animate_11),
                getResources().getDrawable(R.mipmap.record_animate_12),
                getResources().getDrawable(R.mipmap.record_animate_13),
                getResources().getDrawable(R.mipmap.record_animate_14),};

        // 表情list
        reslist = getExpressionRes(35);
        // 初始化表情viewpager
        List<View> views = new ArrayList<View>();
        View gv1 = getGridChildView(1);
        View gv2 = getGridChildView(2);
        views.add(gv1);
        views.add(gv2);
        expressionViewpager.setAdapter(new ExpressionPagerAdapter(views));
        edittext_layout.requestFocus();
        voiceRecorder = new VoiceRecorder(micImageHandler);
        buttonPressToSpeak.setOnTouchListener(new PressToSpeakListen());
        mEditTextContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edittext_layout.setBackgroundResource(R.mipmap.input_bar_bg_active);
                } else {
                    edittext_layout.setBackgroundResource(R.mipmap.input_bar_bg_normal);
                }

            }
        });
        mEditTextContent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                edittext_layout.setBackgroundResource(R.mipmap.input_bar_bg_active);
                more.setVisibility(View.GONE);
                iv_emoticons_normal.setVisibility(View.VISIBLE);
                iv_emoticons_checked.setVisibility(View.INVISIBLE);
                emojiIconContainer.setVisibility(View.GONE);
                btnContainer.setVisibility(View.GONE);
            }
        });
        // 监听文字框
        mEditTextContent.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (!TextUtils.isEmpty(s)) {
                    btnMore.setVisibility(View.GONE);
                    buttonSend.setVisibility(View.VISIBLE);
                } else {
                    btnMore.setVisibility(View.VISIBLE);
                    buttonSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        activityInstance = this;
        iv_emoticons_normal.setOnClickListener(this);
        iv_emoticons_checked.setOnClickListener(this);
        // position = getIntent().getIntExtra("position", -1);
        clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        wakeLock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "demo");
        // 判断单聊还是群聊
        chatType = getIntent().getIntExtra("chatType", CHATTYPE_SINGLE);
        // type=getIntent().getIntExtra("type", 0);

        if (chatType == CHATTYPE_SINGLE) { // 单聊
            toChatUsername = getIntent().getStringExtra("userId");
            String toChatUserNick = getIntent().getStringExtra("userNick");
            ((TextView) findViewById(R.id.name)).setText(toChatUserNick);
            toUserNick = getIntent().getStringExtra("userNick");
            toUserAvatar = getIntent().getStringExtra("userAvatar");
        } else {

            findViewById(R.id.container_voice_call).setVisibility(View.GONE);
            toChatUsername = getIntent().getStringExtra("groupId");
            String groupName = getIntent().getStringExtra("groupName");
            ((TextView) findViewById(R.id.name)).setText(groupName);
        }
        conversation = mDataHelper.GetUserMessageList(toChatUsername);
        adapter = new MessageAdapter(this, toChatUsername, chatType, conversation);
        // 显示消息
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new ListScrollListener());
        int count = listView.getCount();
        if (count > 0) {
            listView.setSelection(count - 1);
        }

        listView.setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboard();
                more.setVisibility(View.GONE);
                iv_emoticons_normal.setVisibility(View.VISIBLE);
                iv_emoticons_checked.setVisibility(View.INVISIBLE);
                emojiIconContainer.setVisibility(View.GONE);
                btnContainer.setVisibility(View.GONE);
                return false;
            }
        });
        iv_setting = getId(R.id.iv_setting);
        iv_setting_group = getId(R.id.iv_setting_group);
        LogUtil.e("TRACE", "chat type is :" + chatType);
        if (chatType == CHATTYPE_SINGLE) {
            iv_setting.setVisibility(View.VISIBLE);
            iv_setting_group.setVisibility(View.GONE);
//            iv_setting.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    startActivity(
//                            new Intent(ChatActivity.this,
//                                    ChatSingleSettingActivity.class).putExtra("userId",
//                                    toChatUsername));
//
//                }
//
//            });
        } else {
            iv_setting.setVisibility(View.GONE);
            iv_setting_group.setVisibility(View.VISIBLE);
            iv_setting_group.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
//                    startActivityForResult((new Intent(ChatActivity.this, ChatRoomSettingActivity.class).putExtra("groupId", toChatUsername)), REQUEST_CODE_GROUP_DETAIL);
//                    startActivity(new Intent(getActivity(), BangPaiInfoActivity.class));
                }

            });
        }

    }

    @Override
    public void initEvent() {

    }

    /**
     * 发送文本消息
     *
     * @param text message text
     */
    private void sendText(String text) {
        if (text.length() > 0) {
            try {
                // 组建一个待发送的ECMessage
                ECMessage msg = ECMessage.createECMessage(ECMessage.Type.TXT);
                //如果需要跨应用发送消息，需通过appkey+英文井号+用户帐号的方式拼接，发送录音、发送群组消息等与此方式一致。

                // 设置消息接收者
                msg.setTo(toChatUsername);
                msg.setNickName(myUserNick);

                // 创建一个文本消息体，并添加到消息对象中
                ECTextMessageBody msgBody = new ECTextMessageBody(text.toString());
                // 将消息体存放到ECMessage中
                msg.setBody(msgBody);

                // 调用SDK发送接口发送消息到服务器
                ECChatManager manager = ECDevice.getECChatManager();
                manager.sendMessage(msg, new ECChatManager.OnSendMessageListener() {
                    @Override
                    public void onSendMessageComplete(ECError error, ECMessage message) {
                        // 处理消息发送结果
                        if (message == null) {
                            return;
                        }
                        // 将发送的消息更新到本地数据库并刷新UI
                        conversation.add(mDataHelper.SaveMessage(sendMessageEntity(message)));
                        // 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
                        adapter.refresh();
                        listView.setSelection(listView.getCount() - 1);
                        mEditTextContent.setText("");
                    }

                    @Override
                    public void onProgress(String msgId, int totalByte, int progressByte) {
                        // 处理文件发送上传进度（尽上传文件、图片时候SDK回调该方法）
                    }
                });
            } catch (Exception e) {
                // 处理发送异常
                DBLog.e("ECSDK_Demo", "send message fail , e=" + e.getMessage());
            }
        }
    }


    /**
     * 发送语音
     *
     * @param filePath
     * @param fileName
     * @param length
     * @param isResend
     */
    private void sendVoice(String filePath, String fileName, String length, boolean isResend) {
        try {
            // 组建一个待发送的ECMessage
            ECMessage message = ECMessage.createECMessage(ECMessage.Type.VOICE);
            // 设置消息接收者
            message.setTo(toChatUsername);
            message.setNickName(myUserNick);
            // 扩张字段，应用可以自定义规则
            message.setUserData("msgExt://amr");
            // 设置语音包体,语音录制文件需要保存的目录
            ECVoiceMessageBody messageBody = new ECVoiceMessageBody(new File(filePath + fileName), 0);
            message.setBody(messageBody);
            // 仅录制语音消息，录制完成后需要调用发送接口发送消息
            ECChatManager manager = ECDevice.getECChatManager();
            manager.startVoiceRecording(message,
                    new ECChatManager.OnRecordTimeoutListener() {

                        @Override
                        public void onRecordingTimeOut(long duration) {
                            // 如果语音录制超过最大60s长度,则发送
                            // 同时SDK停止当前语音录制
                        }

                        @Override
                        public void onRecordingAmplitude(
                                double amplitude) {
                            // 显示声音振幅

                        }

                    });
            // 调用SDk接口停止当前录音
            manager.stopVoiceRecording(
                    new ECChatManager.OnStopVoiceRecordingListener() {
                        @Override
                        public void onRecordingComplete() {
                            // SDK停止录音完成，调用SDK接口发送语音消息
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
//将ECMessage对象传入sendMessage方法进行发送。
    }

    /**
     * 发送图片
     *
     * @param filePath
     */
    private void sendPicture(final String filePath, boolean is_share) {
        try {
            // 组建一个待发送的ECMessage
            ECMessage msg = ECMessage.createECMessage(ECMessage.Type.IMAGE);
            //如果需要跨应用发送消息，需通过appkey+英文井号+用户帐号的方式拼接，发送录音、发送群组消息等与此方式一致。
            //例如：appkey=20150314000000110000000000000010
            //msg.setTo(""appkey#John的账号");
            // 设置消息接收者
            msg.setTo(toChatUsername);
            msg.setNickName(myUserNick);

            // 或者创建一个图片消息体 并且设置附件包体（其实图片也是相当于附件）
            // 比如我们发送SD卡里面的一张Tony_2015.jpg图片
            ECImageMessageBody msgBody = new ECImageMessageBody();
            // 设置附件名
            msgBody.setFileName("Tony_2015.jpg");
            // 设置附件扩展名
            msgBody.setFileExt("jpg");
            // 设置附件本地路径
            msgBody.setLocalUrl(filePath);

            // 将消息体存放到ECMessage中
            msg.setBody(msgBody);

            // 调用SDK发送接口发送消息到服务器
            ECChatManager manager = ECDevice.getECChatManager();
            manager.sendMessage(msg, new ECChatManager.OnSendMessageListener() {
                @Override
                public void onSendMessageComplete(ECError error, ECMessage message) {
                    // 处理消息发送结果
                    if (message == null) {
                        return;
                    }
                    // 将发送的消息更新到本地数据库并刷新UI
                    conversation.add(mDataHelper.SaveMessage(sendMessageEntity(message)));
                    // 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
                    adapter.refresh();
                    listView.setSelection(listView.getCount() - 1);
                    mEditTextContent.setText("");
                }

                @Override
                public void onProgress(String msgId, int totalByte, int progressByte) {
                    // 处理文件发送上传进度（尽上传文件、图片时候SDK回调该方法）
                }
            });
        } catch (Exception e) {
            // 处理发送异常
            DBLog.e("ECSDK_Demo", "send message fail , e=" + e.getMessage());
        }
    }

    /**
     * 根据图库图片uri发送图片
     *
     * @param selectedImage
     */
    private void sendPicByUri(Uri selectedImage) {
        // String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage, null, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex("_data");
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                Toast toast = Toast.makeText(this, "找不到图片", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            sendPicture(picturePath, false);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(this, "找不到图片", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            sendPicture(file.getAbsolutePath(), false);
        }
    }

    /**
     * 发送视频消息
     */
    private void sendVideo(final String filePath, final String thumbPath, final int length) {
        try {
            // 组建一个待发送的ECMessage
            ECMessage msg = ECMessage.createECMessage(ECMessage.Type.VIDEO);
            //如果需要跨应用发送消息，需通过appkey+英文井号+用户帐号的方式拼接，发送录音、发送群组消息等与此方式一致。
            //例如：appkey=20150314000000110000000000000010
            //msg.setTo(""appkey#John的账号");
            // 设置消息接收者
            msg.setTo(toChatUsername);

            // 或者创建一个创建附件消息体
            // 比如我们发送SD卡里面的一个Tony_2015.zip文件
            ECFileMessageBody msgBody = new ECFileMessageBody();
            // 设置附件名
            msgBody.setFileName("Tony_2015.zip");
            // 设置附件扩展名
            msgBody.setFileExt("view");
            // 设置附件本地路径
            msgBody.setLocalUrl(filePath);
            // 设置附件长度
            msgBody.setLength(5);

            // 将消息体存放到ECMessage中
            msg.setBody(msgBody);
            // 调用SDK发送接口发送消息到服务器
            ECChatManager manager = ECDevice.getECChatManager();
            manager.sendMessage(msg, new ECChatManager.OnSendMessageListener() {
                @Override
                public void onSendMessageComplete(ECError error, ECMessage message) {
                    // 处理消息发送结果
                    if (message == null) {
                        return;
                    }
                    // 将发送的消息更新到本地数据库并刷新UI
                    conversation.add(mDataHelper.SaveMessage(sendMessageEntity(message)));
                    // 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
                    adapter.refresh();
                    listView.setSelection(listView.getCount() - 1);
                    mEditTextContent.setText("");
                }

                @Override
                public void onProgress(String msgId, int totalByte, int progressByte) {
                    // 处理文件发送上传进度（尽上传文件、图片时候SDK回调该方法）
                }
            });
        } catch (Exception e) {
            // 处理发送异常
            Log.e("ECSDK_Demo", "send message fail , e=" + e.getMessage());
        }
    }

    /**
     * 发送位置信息
     *
     * @param latitude
     * @param longitude
     * @param imagePath
     * @param locationAddress
     */
    private void sendLocationMsg(double latitude, double longitude,
                                 String imagePath, String locationAddress) {
        try {
            //经纬度
            ECMessage msg = ECMessage.createECMessage(ECMessage.Type.LOCATION);
            // 设置消息接收者
            msg.setTo(toChatUsername);
            // 创建一个消息体，并添加到位置消息对象中
            ECLocationMessageBody msgBody = new ECLocationMessageBody(104.1, 104.1);
            msgBody.setTitle("位置信息");
            msg.setBody(msgBody);

            ECChatManager manager = ECDevice.getECChatManager();
            manager.sendMessage(msg, new ECChatManager.OnSendMessageListener() {
                @Override
                public void onSendMessageComplete(ECError error, ECMessage message) {
                    // 处理消息发送结果
                    if (message == null) {
                        return;
                    }
                    // 将发送的消息更新到本地数据库并刷新UI
                    conversation.add(mDataHelper.SaveMessage(sendMessageEntity(message)));
                    // 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
                    adapter.refresh();
                    listView.setSelection(listView.getCount() - 1);
                    mEditTextContent.setText("");
                }

                @Override
                public void onProgress(String msgId, int totalByte, int progressByte) {
                }
            });
        } catch (Exception e) {
            // 处理发送异常
            Log.e("ECSDK_Demo", "send message fail , e=" + e.getMessage());
        }

//将ECMessage对象传入sendMessage方法进行发送
    }

    /**
     * 发送文件
     *
     * @param uri
     */
    private void sendFile(Uri uri) {
        String filePath = null;
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = getContentResolver().query(uri, projection, null,
                        null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    filePath = cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            filePath = uri.getPath();
        }
        File file = new File(filePath);
        if (file == null || !file.exists()) {
            Toast.makeText(getApplicationContext(), "文件不存在", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        if (file.length() > 10 * 1024 * 1024) {
            Toast.makeText(getApplicationContext(), "文件不能大于10M",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // 组建一个待发送的ECMessage
            ECMessage msg = ECMessage.createECMessage(ECMessage.Type.FILE);
            //如果需要跨应用发送消息，需通过appkey+英文井号+用户帐号的方式拼接，发送录音、发送群组消息等与此方式一致。
            //例如：appkey=20150314000000110000000000000010
            //msg.setTo(""appkey#John的账号");
            // 设置消息接收者
            msg.setTo(toChatUsername);


            // 或者创建一个创建附件消息体
            // 比如我们发送SD卡里面的一个Tony_2015.zip文件
            ECFileMessageBody msgBody = new ECFileMessageBody();
            // 设置附件名
            msgBody.setFileName("Tony_2015.zip");
            // 设置附件扩展名
            msgBody.setFileExt("zip");
            // 设置附件本地路径
            msgBody.setLocalUrl(filePath);
            // 设置附件长度
            msgBody.setLength(file.length());

            // 将消息体存放到ECMessage中
            msg.setBody(msgBody);
            // 调用SDK发送接口发送消息到服务器
            ECChatManager manager = ECDevice.getECChatManager();
            manager.sendMessage(msg, new ECChatManager.OnSendMessageListener() {
                @Override
                public void onSendMessageComplete(ECError error, ECMessage message) {
                    // 处理消息发送结果
                    if (message == null) {
                        return;
                    }
                    // 将发送的消息更新到本地数据库并刷新UI
                    conversation.add(mDataHelper.SaveMessage(sendMessageEntity(message)));
                    // 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
                    adapter.refresh();
                    listView.setSelection(listView.getCount() - 1);
                    mEditTextContent.setText("");
                }

                @Override
                public void onProgress(String msgId, int totalByte, int progressByte) {
                    // 处理文件发送上传进度（尽上传文件、图片时候SDK回调该方法）
                }
            });
        } catch (Exception e) {
            // 处理发送异常
            Log.e("ECSDK_Demo", "send message fail , e=" + e.getMessage());
        }
    }

    /**
     * 选择文件
     */
    private void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);

        } else {
            intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }


    /**
     * 照相获取图片
     */
    public void selectPicFromCamera() {
        if (!CommonUtils.isExitsSdcard()) {
            Toast.makeText(getApplicationContext(), "SD卡不存在，不能拍照",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        cameraFile = new File(MyApplication.getInstance().SAVE_PIC_PATH, System.currentTimeMillis() + ".jpg");
        cameraFile.getParentFile().mkdirs();
        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)), REQUEST_CODE_CAMERA);
    }

    /**
     * 从图库获取图片
     */
    public void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }

    /**
     * 重发消息
     */
    private void resendMessage() {

    }

    private MessageEntity sendMessageEntity(ECMessage Message) {
        MessageEntity MessageEntity = new MessageEntity();
        MessageEntity.setMsgId(Message.getMsgId());
        MessageEntity.setToId(toChatUsername);
        MessageEntity.setName(myUserNick);
        MessageEntity.setAvatar(myUserAvatar);
        MessageEntity.setCreatedTime(String.valueOf(Message.getMsgTime()));
//        MessageEntity.setMessage(Message.getBody().toString().replace("ECTextMessageBody:\"", "").replace("\"", ""));
        MessageEntity.setMessage(Message.getBody().toString());
        MessageEntity.setIsSend(Message.getMsgStatus().name());
        MessageEntity.setIsRend(Message.isRead() ? 0 : 1);
        MessageEntity.setIsTop(0);
        MessageEntity.setType(Message.getType().name());
        MessageEntity.setDirection(Message.getDirection().name());
        MessageEntity.setMyUserId(String.valueOf(MyApplication.getInstance().UserInfo.getUserId()));
        return MessageEntity;
    }

    /**
     * 显示语音图标按钮
     *
     * @param view
     */
    public void setModeVoice(View view) {
        hideKeyboard();
        edittext_layout.setVisibility(View.GONE);
        more.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        buttonSetModeKeyboard.setVisibility(View.VISIBLE);
        buttonSend.setVisibility(View.GONE);
        btnMore.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.VISIBLE);
        iv_emoticons_normal.setVisibility(View.VISIBLE);
        iv_emoticons_checked.setVisibility(View.INVISIBLE);
        btnContainer.setVisibility(View.VISIBLE);
        emojiIconContainer.setVisibility(View.GONE);
    }

    /**
     * 按住说话listener
     */
    class PressToSpeakListen implements View.OnTouchListener {
        @SuppressLint({"ClickableViewAccessibility", "Wakelock"})
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!CommonUtils.isExitsSdcard()) {
                        Toast.makeText(ChatActivity.this, "发送语音需要sdcard支持！",
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    try {
                        v.setPressed(true);
                        wakeLock.acquire();
//                        if (VoicePlayClickListener.isPlaying)
//                            VoicePlayClickListener.currentPlayListener
//                                    .stopPlayVoice();
                        recordingContainer.setVisibility(View.VISIBLE);
                        recordingHint.setText(getString(R.string.move_up_to_cancel));
                        recordingHint.setBackgroundColor(Color.TRANSPARENT);
                        voiceRecorder.startRecording(null, toChatUsername,
                                getApplicationContext());
                    } catch (Exception e) {
                        e.printStackTrace();
                        v.setPressed(false);
                        if (wakeLock.isHeld())
                            wakeLock.release();
                        if (voiceRecorder != null)
                            voiceRecorder.discardRecording();
                        recordingContainer.setVisibility(View.INVISIBLE);
                        Toast.makeText(ChatActivity.this, R.string.recoding_fail,
                                Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    return true;
                case MotionEvent.ACTION_MOVE: {
                    if (event.getY() < 0) {
                        recordingHint
                                .setText(getString(R.string.release_to_cancel));
                        recordingHint
                                .setBackgroundResource(R.drawable.recording_text_hint_bg);
                    } else {
                        recordingHint
                                .setText(getString(R.string.move_up_to_cancel));
                        recordingHint.setBackgroundColor(Color.TRANSPARENT);
                    }
                    return true;
                }
                case MotionEvent.ACTION_UP:
                    v.setPressed(false);
                    recordingContainer.setVisibility(View.INVISIBLE);
                    if (wakeLock.isHeld())
                        wakeLock.release();
                    if (event.getY() < 0) {
                        // discard the recorded audio.
                        voiceRecorder.discardRecording();

                    } else {
                        // stop recording and send voice file
                        try {
                            int length = voiceRecorder.stopRecoding();
                            if (length > 0) {
                                sendVoice(voiceRecorder.getVoiceFilePath(),
                                        voiceRecorder
                                                .getVoiceFileName(toChatUsername),
                                        Integer.toString(length), false);
//                            } else if (length == EMError.INVALID_FILE) {
                            } else if (length == 0) {
                                Toast.makeText(getApplicationContext(), "无录音权限",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "录音时间太短",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(ChatActivity.this, "发送失败，请检测服务器是否连接",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    return true;
                default:
                    recordingContainer.setVisibility(View.INVISIBLE);
                    if (voiceRecorder != null)
                        voiceRecorder.discardRecording();
                    return false;
            }
        }
    }

    /**
     * 显示键盘图标
     *
     * @param view
     */
    public void setModeKeyboard(View view) {
        // mEditTextContent.setOnFocusChangeListener(new OnFocusChangeListener()
        // {
        // @Override
        // public void onFocusChange(View v, boolean hasFocus) {
        // if(hasFocus){
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        // }
        // }
        // });
        edittext_layout.setVisibility(View.VISIBLE);
        more.setVisibility(View.GONE);
        view.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.VISIBLE);
        // mEditTextContent.setVisibility(View.VISIBLE);
        mEditTextContent.requestFocus();
        // buttonSend.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.GONE);
        if (TextUtils.isEmpty(mEditTextContent.getText())) {
            btnMore.setVisibility(View.VISIBLE);
            buttonSend.setVisibility(View.GONE);
        } else {
            btnMore.setVisibility(View.GONE);
            buttonSend.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 显示或隐藏图标按钮页
     *
     * @param view
     */
    public void more(View view) {
        if (more.getVisibility() == View.GONE) {
            System.out.println("more gone");
            hideKeyboard();
            more.setVisibility(View.VISIBLE);
            btnContainer.setVisibility(View.VISIBLE);
            emojiIconContainer.setVisibility(View.GONE);
        } else {
            if (emojiIconContainer.getVisibility() == View.VISIBLE) {
                emojiIconContainer.setVisibility(View.GONE);
                btnContainer.setVisibility(View.VISIBLE);
                iv_emoticons_normal.setVisibility(View.VISIBLE);
                iv_emoticons_checked.setVisibility(View.INVISIBLE);
            } else {
                more.setVisibility(View.GONE);
            }

        }

    }

    /**
     * 点击文字输入框
     *
     * @param v
     */
    public void editClick(View v) {
        listView.setSelection(listView.getCount() - 1);
        if (more.getVisibility() == View.VISIBLE) {
            more.setVisibility(View.GONE);
            iv_emoticons_normal.setVisibility(View.VISIBLE);
            iv_emoticons_checked.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_send) {// 点击发送按钮(发文字和表情)
            String s = mEditTextContent.getText().toString();
            sendText(s);
        } else if (id == R.id.btn_take_picture) {
            selectPicFromCamera();// 点击照相图标
        } else if (id == R.id.btn_picture) {
            selectPicFromLocal(); // 点击图片图标
        } else if (id == R.id.btn_location) { // 位置
            startActivityForResult(new Intent(this, BaiduMapActivity.class), REQUEST_CODE_MAP);
        } else if (id == R.id.iv_emoticons_normal) { // 点击显示表情框
            more.setVisibility(View.VISIBLE);
            iv_emoticons_normal.setVisibility(View.INVISIBLE);
            iv_emoticons_checked.setVisibility(View.VISIBLE);
            btnContainer.setVisibility(View.GONE);
            emojiIconContainer.setVisibility(View.VISIBLE);
            hideKeyboard();
        } else if (id == R.id.iv_emoticons_checked) { // 点击隐藏表情框
            iv_emoticons_normal.setVisibility(View.VISIBLE);
            iv_emoticons_checked.setVisibility(View.INVISIBLE);
            btnContainer.setVisibility(View.VISIBLE);
            emojiIconContainer.setVisibility(View.GONE);
            more.setVisibility(View.GONE);

        } else if (id == R.id.btn_video) {
//            // 点击摄像图标
            Intent intent = new Intent(ChatActivity.this, ImageGridActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
        } else if (id == R.id.btn_file) { // 点击文件图标
            selectFileFromLocal();
        } else if (id == R.id.btn_voice_call) { // 点击语音电话图标
//            if (!EMChatManager.getInstance().isConnected())
//                Toast.makeText(this, "尚未连接至服务器，请稍后重试", Toast.LENGTH_SHORT).show();
//            else
//                startActivity(new Intent(ChatActivity.this, VoiceCallActivity.class).putExtra("username", toChatUsername).putExtra("isComingCall", false));
        }
    }

    /**
     * 获取表情的gridview的子view
     *
     * @param i
     * @return
     */
    private View getGridChildView(int i) {
        View view = View.inflate(this, R.layout.expression_gridview, null);
        ExpandGridView gv = (ExpandGridView) view.findViewById(R.id.gridview);
        List<String> list = new ArrayList<String>();
        if (i == 1) {
            List<String> list1 = reslist.subList(0, 20);
            list.addAll(list1);
        } else if (i == 2) {
            list.addAll(reslist.subList(20, reslist.size()));
        }
        list.add("delete_expression");
        final ExpressionAdapter expressionAdapter = new ExpressionAdapter(this, 1, list);
        gv.setAdapter(expressionAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String filename = expressionAdapter.getItem(position);
                try {
                    // 文字输入框可见时，才可输入表情
                    // 按住说话可见，不让输入表情
                    if (buttonSetModeKeyboard.getVisibility() != View.VISIBLE) {

                        if (filename != "delete_expression") { // 不是删除键，显示表情
                            // 这里用的反射，所以混淆的时候不要混淆SmileUtils这个类
                            @SuppressWarnings("rawtypes")
                            Class clz = Class.forName("com.kaichaohulian.baocms.utils.SmileUtils");
                            Field field = clz.getField(filename);
                            mEditTextContent.append(SmileUtils.getSmiledText(ChatActivity.this, (String) field.get(null)));
                        } else { // 删除文字或者表情
                            if (!TextUtils.isEmpty(mEditTextContent.getText())) {

                                int selectionStart = mEditTextContent.getSelectionStart();// 获取光标的位置
                                if (selectionStart > 0) {
                                    String body = mEditTextContent.getText().toString();
                                    String tempStr = body.substring(0, selectionStart);
                                    int i = tempStr.lastIndexOf("[");// 获取最后一个表情的位置
                                    if (i != -1) {
                                        CharSequence cs = tempStr.substring(i, selectionStart);
                                        if (SmileUtils.containsKey(cs
                                                .toString()))
                                            mEditTextContent.getEditableText()
                                                    .delete(i, selectionStart);
                                        else
                                            mEditTextContent.getEditableText()
                                                    .delete(selectionStart - 1,
                                                            selectionStart);
                                    } else {
                                        mEditTextContent.getEditableText()
                                                .delete(selectionStart - 1,
                                                        selectionStart);
                                    }
                                }
                            }

                        }
                    }
                } catch (Exception e) {
                }
            }
        });
        return view;
    }

    public List<String> getExpressionRes(int getSum) {
        List<String> reslist = new ArrayList<String>();
        for (int x = 1; x <= getSum; x++) {
            String filename = "ee_" + x;

            reslist.add(filename);

        }
        return reslist;

    }


    /**
     * listview滑动监听listener
     */
    private class ListScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                    if (view.getFirstVisiblePosition() == 0 && !isloading
                            && haveMoreData) {
                        loadmorePB.setVisibility(View.VISIBLE);
                        // sdk初始化加载的聊天记录为20条，到顶时去db里获取更多
                        try {
                            // 获取更多messges，调用此方法的时候从db获取的messages
                            // sdk会自动存入到此conversation中

                        } catch (Exception e1) {
                            loadmorePB.setVisibility(View.GONE);
                            return;
                        }
                        try {
                            Thread.sleep(300);
                        } catch (InterruptedException e) {
                        }
//                        if (messages.size() != 0) {
//                            // 刷新ui
//                            adapter.notifyDataSetChanged();
//                            listView.setSelection(messages.size() - 1);
//                            if (messages.size() != pagesize)
//                                haveMoreData = false;
//                        } else {
//                            haveMoreData = false;
//                        }
                        loadmorePB.setVisibility(View.GONE);
                        isloading = false;

                    }
                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {

        }

    }

    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 覆盖手机返回键
     */
    @Override
    public void onBackPressed() {
        if (more.getVisibility() == View.VISIBLE) {
            more.setVisibility(View.GONE);
            iv_emoticons_normal.setVisibility(View.VISIBLE);
            iv_emoticons_checked.setVisibility(View.INVISIBLE);
        } else {
            // if(type==3){
            // startActivity(new
            // Intent(getApplicationContext(),MyGroupActivity.class));
            // finish();
            // }else if(type==2){
            // startActivity(new
            // Intent(getApplicationContext(),ContactListActivity.class));
            // finish();
            // }else if(type==1){
            // startActivity(new
            // Intent(getApplicationContext(),XinDeActivity.class));
            // finish();
            // }else{
            finish();
            // }

            super.onBackPressed();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    /**
     * onActivityResult
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            finish();
        }

        if (resultCode == RESULT_CODE_EXIT_GROUP) {
            setResult(RESULT_OK);
            finish();
            return;
        }
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case RESULT_CODE_COPY: // 复制消息
                    MessageEntity copyMsg = adapter.getItem(data.getIntExtra("position", -1));
                    clipboard.setText(copyMsg.getMessage().toString());
                    break;
                case RESULT_CODE_DELETE: // 删除消息
                    MessageEntity deleteMsg = adapter.getItem(data.getIntExtra("position", -1));
                    conversation.remove(deleteMsg);
                    adapter.refresh();
                    listView.setSelection(data.getIntExtra("position", adapter.getCount()) - 1);
                    mDataHelper.DelMessage(deleteMsg.getMsgId());
                    break;
                case RESULT_CODE_FORWARD: // 转发消息
//                    ECMessage forwardMsg = (ECMessage) adapter.getItem(data
//                            .getIntExtra("position", 0));
//                    Intent intent = new Intent(this, ForwardMessageActivity.class);
//                    intent.putExtra("forward_msg_id", forwardMsg.getMsgId());
//                    startActivity(intent);

                    break;

                default:
                    break;
            }
        }
        if (resultCode == RESULT_OK) { // 清空消息
            if (requestCode == REQUEST_CODE_EMPTY_HISTORY) {
                // 清空会话
//                EMChatManager.getInstance().clearConversation(toChatUsername);
                adapter.refresh();
            } else if (requestCode == REQUEST_CODE_CAMERA) { // 发送照片
                if (cameraFile != null && cameraFile.exists())
                    DBLog.e("cameraFile.getAbsolutePath()------>>>>", cameraFile.getAbsolutePath());
                sendPicture(cameraFile.getAbsolutePath(), false);
            } else if (requestCode == REQUEST_CODE_SELECT_VIDEO) { // 发送本地选择的视频

                int duration = data.getIntExtra("dur", 0);
                String videoPath = data.getStringExtra("path");
                File file = new File(MyApplication.getInstance().VOICE_PATH,
                        "thvideo" + System.currentTimeMillis());
                Bitmap bitmap = null;
                FileOutputStream fos = null;
                try {
                    if (!file.getParentFile().exists()) {
                        file.getParentFile().mkdirs();
                    }
                    bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                    if (bitmap == null) {
                        DBLog.d("chatactivity",
                                "problem load video thumbnail bitmap,use default icon");
                        bitmap = BitmapFactory.decodeResource(getResources(),
                                R.mipmap.app_panel_video_icon);
                    }
                    fos = new FileOutputStream(file);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        fos = null;
                    }
                    if (bitmap != null) {
                        bitmap.recycle();
                        bitmap = null;
                    }

                }
                sendVideo(videoPath, file.getAbsolutePath(), duration / 1000);

            } else if (requestCode == REQUEST_CODE_LOCAL) { // 发送本地图片
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        sendPicByUri(selectedImage);
                    }
                }
            } else if (requestCode == REQUEST_CODE_SELECT_FILE) { // 发送选择的文件
                if (data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        sendFile(uri);
                    }
                }

            } else if (requestCode == REQUEST_CODE_MAP) { // 地图
                double latitude = data.getDoubleExtra("latitude", 0);
                double longitude = data.getDoubleExtra("longitude", 0);
                String locationAddress = data.getStringExtra("address");
                if (locationAddress != null && !locationAddress.equals("")) {
                    more(more);
                    sendLocationMsg(latitude, longitude, "", locationAddress);
                } else {
                    Toast.makeText(this, "无法获取到您的位置信息！", Toast.LENGTH_SHORT)
                            .show();
                }
                // 重发消息
            } else if (requestCode == REQUEST_CODE_TEXT
                    || requestCode == REQUEST_CODE_VOICE
                    || requestCode == REQUEST_CODE_PICTURE
                    || requestCode == REQUEST_CODE_LOCATION
                    || requestCode == REQUEST_CODE_VIDEO
                    || requestCode == REQUEST_CODE_FILE) {
                resendMessage();
            } else if (requestCode == REQUEST_CODE_COPY_AND_PASTE) {
                // 粘贴
                if (!TextUtils.isEmpty(clipboard.getText())) {
                    String pasteText = clipboard.getText().toString();
                    if (pasteText.startsWith(COPY_IMAGE)) {
                        // 把图片前缀去掉，还原成正常的path
                        sendPicture(pasteText.replace(COPY_IMAGE, ""), false);
                    }

                }
            } else if (requestCode == REQUEST_CODE_ADD_TO_BLACKLIST) { // 移入黑名单
                MessageEntity deleteMsg = adapter.getItem(data
                        .getIntExtra("position", -1));
//                addUserToBlacklist(deleteMsg.getFrom());
            } else if (conversation.size() > 0) {
                adapter.refresh();
                setResult(RESULT_OK);
            } else if (requestCode == REQUEST_CODE_GROUP_DETAIL) {
                adapter.refresh();
            }
        }
    }

    /**
     * 返回
     *
     * @param view
     */
    public void back(View view) {
        finish();
    }

    public class ReceiveBroadCast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BROADCAST_ACTION)) {
                // 将发送的消息更新到本地数据库并刷新UI
                conversation.add((MessageEntity) intent.getSerializableExtra("message"));
                // 通知adapter有消息变动，adapter会根据加入的这条message显示消息和调用sdk的发送方法
                adapter.refresh();
                listView.setSelection(listView.getCount() - 1);
            }
        }

    }
}

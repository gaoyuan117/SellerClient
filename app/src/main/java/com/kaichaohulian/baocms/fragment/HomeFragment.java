package com.kaichaohulian.baocms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.MainActivity;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.AdverActivity;
import com.kaichaohulian.baocms.activity.BonusHistoryActivity;
import com.kaichaohulian.baocms.activity.InvitationmgActivity;
import com.kaichaohulian.baocms.activity.InvitedetailActivity;
import com.kaichaohulian.baocms.activity.NewFriendsActivity;
import com.kaichaohulian.baocms.activity.OrderHistoryActivity;
import com.kaichaohulian.baocms.activity.PhoneMsgDetailActivity;
import com.kaichaohulian.baocms.activity.SmallMoneyDetailActivity;
import com.kaichaohulian.baocms.activity.WithdrawalsDetailActivity;
import com.kaichaohulian.baocms.adapter.GroupChatAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseEcFragment;
import com.kaichaohulian.baocms.constant.SystemPush;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.dialog.ECListDialog;
import com.kaichaohulian.baocms.ecdemo.common.dialog.ECProgressDialog;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferenceSettings;
import com.kaichaohulian.baocms.ecdemo.common.utils.ECPreferences;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.ecdemo.common.view.NetWarnBannerView;
import com.kaichaohulian.baocms.ecdemo.storage.ConversationSqlManager;
import com.kaichaohulian.baocms.ecdemo.storage.GroupSqlManager;
import com.kaichaohulian.baocms.ecdemo.storage.IMessageSqlManager;
import com.kaichaohulian.baocms.ecdemo.ui.CCPListAdapter;
import com.kaichaohulian.baocms.ecdemo.ui.ConversationAdapter;
import com.kaichaohulian.baocms.ecdemo.ui.SDKCoreHelper;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.CustomerServiceHelper;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.model.Conversation;
import com.kaichaohulian.baocms.ecdemo.ui.contact.ContactLogic;
import com.kaichaohulian.baocms.ecdemo.ui.group.GroupNoticeActivity;
import com.kaichaohulian.baocms.ecdemo.ui.group.GroupService;
import com.kaichaohulian.baocms.entity.GroupEntity;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.yuntongxun.ecsdk.ECChatManager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.ecsdk.im.ECGroup;
import com.yuntongxun.ecsdk.im.ECGroupOption;
import com.yuntongxun.ecsdk.platformtools.ECHandlerHelper;

import java.util.ArrayList;

/**
 * 主界面
 * Created by ljl on 2016/12/11.
 */
public class HomeFragment extends BaseEcFragment implements CCPListAdapter.OnListAdapterCallBackListener {

    private java.util.List<GroupEntity> List;
    private boolean hidden;

    private GroupChatAdapter adapter;

    public RelativeLayout errorItem;
    public TextView errorText;

    private static final String TAG = "ECSDK_Demo.ConversationListFragment";

    public static HomeFragment getInstance(MyApplication myApplication, Activity activity, Context context) {
        HomeFragment instance = new HomeFragment();
        Bundle args = new Bundle();
        instance.setArguments(args);
        return instance;

    }

    public HomeFragment() {

    }

    /**
     * 会话消息列表ListView
     */
    private ListView mListView;
    private NetWarnBannerView mBannerView;
    private ConversationAdapter mAdapter;
    private OnUpdateMsgUnreadCountsListener mAttachListener;
    private ECProgressDialog mPostingdialog;


    @SuppressLint("ValidFragment")
    public HomeFragment(MyApplication myApplication, Activity activity, Context context) {
        super(myApplication, activity, context);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden && !((MainActivity) getActivity()).isConflict) {
            refresh();
        }

        updateConnectState();
        IMessageSqlManager.registerMsgObserver(mAdapter);
        mAdapter.notifyChange();
    }

    /**
     * 刷新页面
     */
    public void refresh() {
        if (getActivity() != null) {
//            normal_list.clear();
//            normal_list.addAll(loadConversationsWithRecentChat());
//            adapter = new ConversationAdapter(getActivity(), normal_list, top_list,topMap);
//            listView.setAdapter(adapter);
        }
    }


//    @Override
//    public void initView() {
//        listView = getId(R.id.listView);
//        errorItem = getId(R.id.rl_error_item);
//
//        errorItem.setVisibility(View.GONE);
//
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if(position == List.size()){
//                    return;
//                }
//                GroupEntity GroupEntity = List.get(position);
//                Bundle Bundle = new Bundle();
//                Bundle.putInt("id", GroupEntity.getId());
//                Bundle.putString("chatGroupId", GroupEntity.getChatGroupId());
//                ActivityUtil.next(getActivity(), GroupChatDetailActivity.class, Bundle);
//            }
//        });
//    }


    @Override
    protected void onReleaseTabUI() {

    }

    final private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View visew, int position, long id) {
            if (mAdapter != null) {
                int headerViewsCount = mListView.getHeaderViewsCount();
                if (position < headerViewsCount) {
                    return;
                }
                int _position = position - headerViewsCount;

                if (mAdapter == null || mAdapter.getItem(_position) == null) {
                    return;
                }
                Conversation conversation = mAdapter.getItem(_position);
                String contactId = conversation.getContactId();


                //商户审核
                if (contactId.equals(SystemPush.SYS_ACCT_REVIEW)) {
                    return;
                }

                //充值
                if (contactId.equals(SystemPush.SYS_ACCT_CHARGE)) {
                    Intent intent = new Intent(getActivity(), PhoneMsgDetailActivity.class);
                    startActivity(intent);
                    return;
                }

                //提现详情
                if (contactId.equals(SystemPush.SYS_ACCT_WITHDRAWAL)) {
                    Intent intent = new Intent(getActivity(), WithdrawalsDetailActivity.class);
                    startActivity(intent);
                    return;
                }

                //分红详情
                if (contactId.equals(SystemPush.SYS_ACCT_BONUS)) {
                    Intent intent = new Intent(getActivity(), BonusHistoryActivity.class);
                    startActivity(intent);
                    return;
                }

                //邀请
                if (contactId.equals("18510829668")) {
                    Intent intent = new Intent(getActivity(), InvitationmgActivity.class);
                    intent.putExtra("type", "discover");
                    startActivity(intent);
                    return;
                }

                //广告
                if (contactId.equals("18510829669")) {
                    Intent intent = new Intent(getActivity(), AdverActivity.class);
                    startActivity(intent);
                    return;
                }


                //好友请求
                if (contactId.equals("182638268288")) {
                    Intent intent = new Intent(getActivity(), NewFriendsActivity.class);
                    startActivity(intent);
                    return;
                }
                //退款
                if (contactId.equals("182638268289")) {
                    Intent intent = new Intent(getActivity(), SmallMoneyDetailActivity.class);
                    startActivity(intent);
                    return;
                }
                //订单详情
                if (contactId.equals(SystemPush.SYS_ACCT_ORDER)) {
                    Intent intent = new Intent(getActivity(), OrderHistoryActivity.class);
                    startActivity(intent);
                    return;
                }

                int type = conversation.getMsgType();
                if (type == 1000) {
                    Intent intent = new Intent(getActivity(), GroupNoticeActivity.class);
                    startActivity(intent);
                    return;
                }
                if (ContactLogic.isCustomService(conversation.getSessionId())) {
                    showProcessDialog();
                    dispatchCustomerService(conversation.getSessionId());
                    return;
                }

                String info = SharedPrefsUtil.getValue(context, conversation.getSessionId(), null);
                String nick;

                if (info != null) {
                    String[] data = info.split("-x-");
                    nick = data[1];

                    if (conversation.getSessionId().startsWith("g") && data.length == 3) {
                        nick = data[1];
                        CCPAppManager.startGroupChattingAction(getActivity(), conversation.getSessionId(),
                                nick, Integer.parseInt(data[2]), false);
                        return;
                    }

                } else
                    nick = conversation.getUsername();
                conversation.getContactId();
                CCPAppManager.startChattingAction(getActivity(), conversation.getSessionId(), nick);
            }
        }
    };

    /**
     * 处理在线客服界面请求
     *
     * @param sessionId
     */
    private void dispatchCustomerService(String sessionId) {
        CustomerServiceHelper.startService(sessionId, new CustomerServiceHelper.OnStartCustomerServiceListener() {
            @Override
            public void onServiceStart(String event) {
                dismissPostingDialog();
                CCPAppManager.startCustomerServiceAction(getActivity(), event);
            }

            @Override
            public void onError(ECError error) {
                dismissPostingDialog();
            }
        });
    }

    private final AdapterView.OnItemLongClickListener mOnLongClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if (mAdapter != null) {
                int headerViewsCount = mListView.getHeaderViewsCount();
                if (position < headerViewsCount) {
                    return false;
                }
                int _position = position - headerViewsCount;

                if (mAdapter == null || mAdapter.getItem(_position) == null) {
                    return false;
                }
                Conversation conversation = mAdapter.getItem(_position);
                final int itemPosition = position;
                final String[] menu = buildMenu(conversation);
                ECListDialog dialog = new ECListDialog(getActivity(), /*new String[]{getString(R.string.main_delete)}*/menu);
                dialog.setOnDialogItemClickListener(new ECListDialog.OnDialogItemClickListener() {
                    @Override
                    public void onDialogItemClick(Dialog d, int position) {
                        handleContentMenuClick(itemPosition, position);
                    }
                });
                dialog.setTitle(conversation.getUsername());
                dialog.show();
                return true;
            }
            return false;
        }
    };


    private String[] buildMenu(Conversation conversation) {//设置长按条目 2*2
        if (conversation != null && conversation.getSessionId() != null) {
            boolean isTop = ConversationSqlManager.querySessionisTopBySessionId(conversation.getSessionId());//支持单人、群组
            if (conversation.getSessionId().toLowerCase().startsWith("g")) {
                ECGroup ecGroup = GroupSqlManager.getECGroup(conversation.getSessionId());
                boolean isNotice = ecGroup.isNotice();
                if (ecGroup == null || !GroupSqlManager.getJoinState(ecGroup.getGroupId())) {
                    return new String[]{getString(R.string.main_delete)};
                }
                if (ecGroup.isNotice()) {
                    if (isTop) {
                        return new String[]{getString(R.string.main_delete), getString(R.string.cancel_top), getString(R.string.menu_mute_notify)};

                    } else {
                        return new String[]{getString(R.string.main_delete), getString(R.string.set_top), getString(R.string.menu_mute_notify)};
                    }
                } else {
                    if (isTop) {
                        return new String[]{getString(R.string.main_delete), getString(R.string.cancel_top), getString(R.string.menu_notify)};
                    } else {
                        return new String[]{getString(R.string.main_delete), getString(R.string.set_top), getString(R.string.menu_notify)};

                    }

                }
            } else {
                if (isTop) {
                    return new String[]{getString(R.string.main_delete), getString(R.string.cancel_top)};
                } else {
                    return new String[]{getString(R.string.main_delete), getString(R.string.set_top)};

                }

            }
        }
        return new String[]{getString(R.string.main_delete)};
    }


    private void setcancelTopSession(ArrayList<String> arrayList, String item) {
        if (!arrayList.contains(item)) {
            ConversationSqlManager.updateSessionToTop(item, false);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        registerReceiver(new String[]{GroupService.ACTION_SYNC_GROUP, IMessageSqlManager.ACTION_SESSION_DEL});

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mAttachListener = (OnUpdateMsgUnreadCountsListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnUpdateMsgUnreadCountsListener");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        IMessageSqlManager.unregisterMsgObserver(mAdapter);
    }

    /**
     *
     */
    public void initView() {
        if (mListView != null) {
            mListView.setAdapter(null);

            if (mBannerView != null) {
                mListView.removeHeaderView(mBannerView);
            }
        }

        mListView = (ListView) findViewById(R.id.main_chatting_lv);
        View mEmptyView = findViewById(R.id.empty_conversation_tv);
        mListView.setEmptyView(mEmptyView);
        mListView.setDrawingCacheEnabled(false);
        mListView.setScrollingCacheEnabled(false);

        mListView.setOnItemLongClickListener(mOnLongClickListener);
        mListView.setOnItemClickListener(mItemClickListener);
        mBannerView = new NetWarnBannerView(getActivity());
        mBannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reTryConnect();
            }
        });
        mListView.addHeaderView(mBannerView);
        mAdapter = new ConversationAdapter(getActivity(), this);
        try {
            mListView.setAdapter(mAdapter);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        registerForContextMenu(mListView);
    }


    private String getAutoRegistAccount() {
        SharedPreferences sharedPreferences = ECPreferences
                .getSharedPreferences();
        ECPreferenceSettings registAuto = ECPreferenceSettings.SETTINGS_REGIST_AUTO;
        String registAccount = sharedPreferences.getString(registAuto.getId(),
                (String) registAuto.getDefaultValue());
        return registAccount;
    }

    private void reTryConnect() {
        ECDevice.ECConnectState connectState = SDKCoreHelper.getConnectState();
        if (connectState == null || connectState == ECDevice.ECConnectState.CONNECT_FAILED) {

            if (!TextUtils.isEmpty(getAutoRegistAccount())) {
                SDKCoreHelper.init(getActivity());
            }
        }
    }

    public void updateConnectState() {
        if (!isAdded()) {
            return;
        }
        ECDevice.ECConnectState connect = SDKCoreHelper.getConnectState();
        if (connect == ECDevice.ECConnectState.CONNECTING) {
            mBannerView.setNetWarnText(getString(R.string.connecting_server));
            mBannerView.reconnect(true);
        } else if (connect == ECDevice.ECConnectState.CONNECT_FAILED) {
            mBannerView.setNetWarnText(getString(R.string.connect_server_error));
            mBannerView.reconnect(false);
        } else if (connect == ECDevice.ECConnectState.CONNECT_SUCCESS) {
            mBannerView.hideWarnBannerView();
        }
        LogUtil.d(TAG, "updateConnectState connect :" + connect.name());
    }


    private Boolean handleContentMenuClick(int convresion, int position) {
        if (mAdapter != null) {
            int headerViewsCount = mListView.getHeaderViewsCount();
            if (convresion < headerViewsCount) {
                return false;
            }
            int _position = convresion - headerViewsCount;

            if (mAdapter == null || mAdapter.getItem(_position) == null) {
                return false;
            }
            final Conversation conversation = mAdapter.getItem(_position);
            switch (position) {
                case 0:
                    showProcessDialog();
                    ECHandlerHelper handlerHelper = new ECHandlerHelper();
                    handlerHelper.postRunnOnThead(new Runnable() {
                        @Override
                        public void run() {
                            IMessageSqlManager.deleteChattingMessage(conversation.getSessionId());
                            ToastUtil.showMessage(R.string.clear_msg_success);
                            HomeFragment.this.getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dismissPostingDialog();
                                    mAdapter.notifyChange();
                                }
                            });
                        }
                    });
                    break;
                case 2:
                    showProcessDialog();
                    final boolean notify = GroupSqlManager.isGroupNotify(conversation.getSessionId());
                    ECGroupOption option = new ECGroupOption();
                    option.setGroupId(conversation.getSessionId());
                    option.setRule(notify ? ECGroupOption.Rule.SILENCE : ECGroupOption.Rule.NORMAL);
                    GroupService.setGroupMessageOption(option, new GroupService.GroupOptionCallback() {
                        @Override
                        public void onComplete(String groupId) {
                            if (mAdapter != null) {
                                mAdapter.notifyChange();
                            }
                            ToastUtil.showMessage(notify ? R.string.new_msg_mute_notify : R.string.new_msg_notify);
                            dismissPostingDialog();
                        }

                        @Override
                        public void onError(ECError error) {
                            dismissPostingDialog();
                            ToastUtil.showMessage("设置失败");
                        }
                    });
                    break;

                case 1:
                    showProcessDialog();
                    final boolean isTop = ConversationSqlManager.querySessionisTopBySessionId(conversation.getSessionId());
                    ECChatManager chatManager = SDKCoreHelper.getECChatManager();
                    if (chatManager == null) {
                        return null;
                    }
                    chatManager.setSessionToTop(conversation.getSessionId(), !isTop, new ECChatManager.OnSetContactToTopListener() {
                        @Override
                        public void onSetContactResult(ECError error, String contact) {
                            dismissPostingDialog();
                            if (error.errorCode == SdkErrorCode.REQUEST_SUCCESS) {
                                ConversationSqlManager.updateSessionToTop(conversation.getSessionId(), !isTop);
                                mAdapter.notifyChange();
                                ToastUtil.showMessage("设置成功");
                            } else {
                                ToastUtil.showMessage("设置失败");
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
        return null;
    }


    @Override
    public void OnListAdapterCallBack() {
        if (mAttachListener != null) {
            mAttachListener.OnUpdateMsgUnreadCounts();
        }
    }

    public interface OnUpdateMsgUnreadCountsListener {
        void OnUpdateMsgUnreadCounts();
    }

    @Override
    protected void handleReceiver(Context context, Intent intent) {
        super.handleReceiver(context, intent);
        if (GroupService.ACTION_SYNC_GROUP.equals(intent.getAction())
                || IMessageSqlManager.ACTION_SESSION_DEL.equals(intent.getAction())) {
            if (mAdapter != null) {
                mAdapter.notifyChange();
            }
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.conversation;
    }

    void showProcessDialog() {
        mPostingdialog = new ECProgressDialog(HomeFragment.this.getActivity(), R.string.login_posting_submit);
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
}

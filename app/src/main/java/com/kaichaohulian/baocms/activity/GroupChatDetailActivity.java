package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.ChenYuanAdapter;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.ecdemo.storage.ConversationSqlManager;
import com.kaichaohulian.baocms.ecdemo.storage.IMessageSqlManager;
import com.kaichaohulian.baocms.ecdemo.ui.SDKCoreHelper;
import com.kaichaohulian.baocms.entity.BaseEntity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.GroupDetail;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.event.FinishEvent;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.kaichaohulian.baocms.view.MyGridView;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.kaichaohulian.baocms.view.SwitchButton;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yuntongxun.ecsdk.ECChatManager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECGroupManager;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.ecsdk.im.ECGroupOption;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.HashMap;

import de.greenrobot.event.EventBus;

/**
 * 群聊信息
 * Created by ljl on 2016/12/19 0019.
 */
public class GroupChatDetailActivity extends BaseActivity implements View.OnClickListener, SwitchButton.OnCheckedChangeListener {
    Button btn_exit_grp;
    MyGridView ChenYuanGrid;
    String edtName, edtGongGao;
    TextView allChenYuanText, tv_name, noticeContext, tv_myname;
    SwitchButton sb_MianDaRao, sb_ZhiDing, sb_save_txl, sb_show_name;
    RelativeLayout BangPaiNameLayout, re_qinkong, rl_gonggao, allChenYuan, myNameLayout, re_code, re_jinyan, re_zhuanrang;

    ChenYuanAdapter mAdapter;

    int mId = 0;
    String GroupId = "";
    String chatGroupId = "";
    GroupDetail detail;
    private boolean isOwner;

    public static final int ME_NAME_EDIT_CODE = 0x1;//修改姓名
    public static final int ME_GONG_EDIT_CODE = 0x2;//修改公告
    public static final int NICK_NAME_EDIT_CODE = 0x3;//修改昵称


    @Override
    public void setContent() {
        setContentView(R.layout.groupchat_detail_activity);
    }

    public void back(View v) {
        ActivityUtil.goBack(getActivity());
    }

    @Override
    public void initData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle Bundle = getIntent().getExtras();
            chatGroupId = Bundle.getString("chatGroupId");
            mId = Bundle.getInt("id", 0);
        }
        mAdapter = new ChenYuanAdapter();
        getData();
        setCenterTitle("聊天信息");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initView() {
        allChenYuanText = getId(R.id.allChenYuanText);
        tv_name = getId(R.id.tv_name);
        noticeContext = getId(R.id.noticeContext);
        ChenYuanGrid = getId(R.id.ChenYuanGrid);
        BangPaiNameLayout = getId(R.id.BangPaiNameLayout);
        rl_gonggao = getId(R.id.rl_gonggao);
        re_qinkong = getId(R.id.re_qinkong);
        sb_MianDaRao = getId(R.id.sb_MianDaRao);
        sb_ZhiDing = getId(R.id.sb_ZhiDing);
        sb_save_txl = getId(R.id.sb_save_txl);
        sb_show_name = getId(R.id.sb_show_name);
        allChenYuan = getId(R.id.allChenYuan);
        myNameLayout = getId(R.id.myNameLayout);
        tv_myname = getId(R.id.tv_myname);
        re_code = getId(R.id.re_code);
        btn_exit_grp = getId(R.id.btn_exit_grp);
        re_jinyan = getId(R.id.re_jinyan);
        re_jinyan.setVisibility(View.GONE);
        re_zhuanrang = getId(R.id.re_zhuanrang);
        re_zhuanrang.setVisibility(View.GONE);
        ChenYuanGrid.setAdapter(mAdapter);
        boolean isTop = ConversationSqlManager.querySessionisTopBySessionId(chatGroupId);
        sb_ZhiDing.setChecked(isTop);
    }

    @Override
    public void initEvent() {
        BangPaiNameLayout.setOnClickListener(this);
        rl_gonggao.setOnClickListener(this);
        sb_MianDaRao.setOnCheckedChangeListener(this);
        sb_ZhiDing.setOnCheckedChangeListener(this);
        sb_save_txl.setOnCheckedChangeListener(this);
        sb_show_name.setOnCheckedChangeListener(this);
        allChenYuan.setOnClickListener(this);
        myNameLayout.setOnClickListener(this);
        re_code.setOnClickListener(this);
        btn_exit_grp.setOnClickListener(this);
        re_jinyan.setOnClickListener(this);
        re_zhuanrang.setOnClickListener(this);
        re_qinkong.setOnClickListener(this);
        ChenYuanGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isOwner) {
                    if (position == mAdapter.getCount() - 1) {
                        if (detail != null) {
                            Bundle Bundle = new Bundle();
                            Bundle.putSerializable("data", detail);
                            Bundle.putBoolean("isDelMember", true);
                            ActivityUtil.next(getActivity(), addGroupFriendsActivity.class, Bundle);
                        }
                    } else if (position == mAdapter.getCount() - 2) {
                        Bundle Bundle = new Bundle();
                        Bundle.putSerializable("data", detail);
                        Bundle.putBoolean("isAddMember", true);
                        ActivityUtil.next(getActivity(), addGroupFriendsActivity.class, Bundle);
                    } else {
                        GroupDetail.DataObject.Members user = mAdapter.getItem(position);
                        searchUser(user.id);
                    }
                } else {
                    GroupDetail.DataObject.Members user = mAdapter.getItem(position);
                    searchUser(user.id);
                }


            }
        });
    }


    private void searchUser(final int id) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("friendId", id);
        HttpUtil.post(Url.dependIDGetUserInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        JSONObject jsonObject = response.getJSONObject("dataObject");
                        UserInfo UserInfo = new UserInfo();
                        UserInfo.setUserId(jsonObject.getInt("id"));
//                        UserInfo.setCreatedTime(jsonObject.getString("createdTime"));
//                        UserInfo.setLocked(jsonObject.getBoolean("isLocked"));
//                        UserInfo.setLastModifiedTime(jsonObject.getString("lastModifiedTime"));
//                        UserInfo.setLastModifier(jsonObject.getString("lastModifier"));
                        UserInfo.setUsername(jsonObject.getString("username"));
                        UserInfo.setPassword(jsonObject.getString("password"));
                        UserInfo.setAccountNumber(jsonObject.getString("accountNumber"));
                        UserInfo.setQrCode(jsonObject.getString("qrCode"));
                        UserInfo.setDistrictId(jsonObject.getString("districtId"));
                        UserInfo.setSex(jsonObject.getString("sex"));
                        UserInfo.setThermalSignatrue(jsonObject.getString("thermalSignatrue"));
                        UserInfo.setPhoneNumber(jsonObject.getString("phoneNumber"));
                        UserInfo.setUserEmail(jsonObject.getString("userEmail"));
                        UserInfo.setBalance(jsonObject.getString("balance"));
                        UserInfo.setAvatar(jsonObject.getString("avatar"));
                        UserInfo.setBackAvatar(jsonObject.getString("backAvatar"));
                        UserInfo.setIsfriend(jsonObject.getInt("isfriend"));
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", UserInfo);

                        Intent intent = new Intent(getActivity(), FriendDetailActivity.class);
                        intent.putExtra(FriendDetailActivity.FROM_GROUP, true);
                        intent.putExtras(bundle);
                        startActivity(intent);

//                        ActivityUtil.next(getActivity(), FriendDetailActivity.class, bundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    finish();
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

    String messageNo, topmessage, saveMail, displayName;

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {
            case R.id.sb_MianDaRao:
                if (isChecked) {
                    messageNo = "1";
//                    updateMiandarao(1);
                    slicen(ECGroupOption.Rule.SILENCE);
                } else {
//                    updateMiandarao(0);
                    messageNo = "0";
                    slicen(ECGroupOption.Rule.NORMAL);
                }
                break;
            case R.id.sb_ZhiDing:
                if (isChecked) {
                    topmessage = "1";
                    updateZhiding(1);
                    zhiding(chatGroupId, true);
                } else {
                    updateZhiding(0);
                    topmessage = "0";
                    zhiding(chatGroupId, false);
                }
                break;
            case R.id.sb_save_txl:
                if (isChecked) {
                    saveMail = "1";
                    updateSaveMail(1);
                } else {
                    saveMail = "0";
                    updateSaveMail(0);
                }
                break;
            case R.id.sb_show_name:
                if (isChecked) {
                    displayName = "1";
                    setUpNickName(1);
                } else {
                    displayName = "0";
                    setUpNickName(0);
                }
                break;

        }
        updateMyUser();
    }

    /**
     * 消息免打扰
     */
    private void slicen(ECGroupOption.Rule rule) {
        // 所属的群组ID
        String groupId = chatGroupId;
        ECGroupOption option = new ECGroupOption(groupId);
        // 设置群组消息通知免打扰状态
        option.setRule(rule);

        // 获得SDK群组创建管理类
        ECGroupManager groupManager = ECDevice.getECGroupManager();
        // 调用设置群消息提醒接口，设置结果回调
        groupManager.setGroupMessageOption(option,
                new ECGroupManager.OnSetGroupMessageOptionListener() {
                    @Override
                    public void onSetGroupMessageOptionComplete(ECError error, String groupId) {
                        if (error.errorCode == SdkErrorCode.REQUEST_SUCCESS) {
                            // 设置群组消息免打扰规则成功
//                            ToastUtil.showMessage("设置群组消息免打扰规则成功");
                            return;
                        }
                        // 设置群组消息免打扰规则失败
                        Log.e("ECSDK_Demo", "set group message option rule fail " +
                                ", errorCode=" + error.errorCode);
                    }
                });
    }

    public void zhiding(final String sessionid, final boolean isTop) {
        ECChatManager chatManager = SDKCoreHelper.getECChatManager();
        if (chatManager == null) {
            return;
        }
        chatManager.setSessionToTop(sessionid, isTop, new ECChatManager.OnSetContactToTopListener() {
            @Override
            public void onSetContactResult(ECError error, String contact) {
                if (error.errorCode == SdkErrorCode.REQUEST_SUCCESS) {
                    ConversationSqlManager.updateSessionToTop(sessionid, isTop);
                    ToastUtil.showMessage("设置成功");
                } else {
                    ToastUtil.showMessage("设置失败");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.re_qinkong:
                if (detail != null) {
                    long sessionid = ConversationSqlManager.querySessionIdForBySessionId(chatGroupId);
                    IMessageSqlManager.deleteChattingMessage(sessionid);
                }
                break;
            case R.id.re_zhuanrang:
                if (detail != null) {
                    Bundle Bundle = new Bundle();
                    Bundle.putSerializable("data", detail);
                    Bundle.putBoolean("iszhuanrang", true);
                    ActivityUtil.next(getActivity(), addGroupFriendsActivity.class, Bundle);
                }
                break;
            case R.id.re_jinyan:
                if (detail != null) {
                    Bundle Bundle = new Bundle();
                    Bundle.putSerializable("data", detail);
                    Bundle.putBoolean("isjinyan", true);
                    ActivityUtil.next(getActivity(), addGroupFriendsActivity.class, Bundle);
                }
                break;
            case R.id.re_code:
                if (v.getTag() != null) {
                    intent.putExtra("code", v.getTag().toString());
                    intent.putExtra("name", tv_name.getText().toString());
                    intent.putExtra("isFromGroup", true);
                    intent.putExtra("groupId", chatGroupId);
                    intent.setClass(getActivity(), GroupCodeActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.myNameLayout:
                intent.putExtra("mTitleName", "我的群聊昵称");
                intent.setClass(getActivity(), MeNameEditActivity.class);
                startActivityForResult(intent, NICK_NAME_EDIT_CODE);
                break;
            case R.id.BangPaiNameLayout:
                if(!isOwner){
                    ToastUtil.showMessage("您不是该群群主,不能修改群名称");
                    return;
                }
                intent.putExtra("mTitleName", "群聊名称");
                intent.setClass(getActivity(), MeNameEditActivity.class);
                startActivityForResult(intent, ME_NAME_EDIT_CODE);
                break;
            case R.id.rl_gonggao:
                if(!isOwner){
                    ToastUtil.showMessage("您不是该群群主,不能设置群公告");
                    return;
                }
                intent.putExtra("mTitleName", "群公告");
                intent.setClass(getActivity(), MeNameEditActivity.class);
                startActivityForResult(intent, ME_GONG_EDIT_CODE);
                break;
            case R.id.allChenYuan:
                if (detail != null) {
                    Bundle Bundle = new Bundle();
                    Bundle.putSerializable("data", detail);
                    Bundle.putBoolean("isdel", true);
                    Bundle.putBoolean("isshow", false);
                    ActivityUtil.next(getActivity(), addGroupFriendsActivity.class, Bundle);
                } else {
                    showToastMsg("暂无成员 请先添加");
                }
                break;
            case R.id.btn_exit_grp:

                if (((Button) v).getText().toString().equals("解散群聊")) {
                    jieshanqunliao();
                } else {
                    delExit();
                }
                break;
        }
    }


    public void jieshanqunliao() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("groupId", GroupId);
        HttpUtil.post(Url.quitGroup, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("解散群", response.toString());
                    BaseEntity baseEntity = JSON.parseObject(response.toString(), BaseEntity.class);
                    if (baseEntity.code.equals("0")) {
                        finish();
                    }
                } catch (Exception e) {
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

    public void delExit() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("groupId", GroupId);
        HttpUtil.post(Url.delGroup, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("解散群", response.toString());
                    BaseEntity baseEntity = JSON.parseObject(response.toString(), BaseEntity.class);
//                    if (baseEntity.code.equals("0")) {
                    long sessionid = ConversationSqlManager.querySessionIdForBySessionId(chatGroupId);
                    IMessageSqlManager.deleteGroupChat(sessionid);
                    EventBus.getDefault().post(new FinishEvent());
                    finish();
//                    }
                } catch (Exception e) {
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

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("chatGroupId", chatGroupId);
        HttpUtil.post(Url.detailByChatId, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("群详情", response.toString());
                    detail = JSON.parseObject(response.toString(), GroupDetail.class);
                    if (detail.code.equals("0")) {
                        if (detail.dataObject.owner == MyApplication.getInstance().UserInfo.getUserId()) {
                            isOwner = true;
                            mAdapter.isOwner(true);
                        } else {
                            isOwner = false;
                            mAdapter.isOwner(false);
                        }

                        GroupDetail.DataObject dataObject = detail.dataObject;
                        allChenYuanText.setText("全部群成员(" + dataObject.memberCount + ")");
                        if (StringUtils.isEmpty(dataObject.name)) {
                            tv_name.setText("未设置");
                        } else {
                            tv_name.setText(dataObject.name);
                        }
                        if (StringUtils.isEmpty(dataObject.introduction)) {
                            noticeContext.setText("暂无公告");
                        } else {
                            noticeContext.setText(dataObject.introduction);
                        }
                        re_code.setTag(dataObject.qrcode);
                        mAdapter.setDisplayName(Integer.parseInt(dataObject.displayName));
                        mAdapter.setMembers(dataObject.members);
                        messageNo = dataObject.messageNo;
                        topmessage = dataObject.topmessage;
                        saveMail = dataObject.saveMail;
                        displayName = dataObject.displayName;
                        if (messageNo.equals("1")) {
                            sb_MianDaRao.setChecked(true);
                        } else {
                            sb_MianDaRao.setChecked(false);
                        }
                        if (topmessage.equals("1")) {
                            sb_ZhiDing.setChecked(true);
                        } else {
                            sb_ZhiDing.setChecked(false);
                        }
                        if (saveMail.equals("1")) {
                            sb_save_txl.setChecked(true);
                        } else {
                            sb_save_txl.setChecked(false);
                        }
                        if (displayName.equals("1")) {
                            sb_show_name.setChecked(true);
                        } else {
                            sb_show_name.setChecked(false);
                        }
                        GroupId = dataObject.id + "";
                        if (dataObject.members != null && dataObject.members.size() > 0) {
                            if (dataObject.members.get(0).id == MyApplication.getInstance().UserInfo.getUserId()) {
                                btn_exit_grp.setText("解散群聊");
                                re_zhuanrang.setVisibility(View.GONE);
                            }
                        }
                        if (dataObject.members != null && dataObject.members.size() > 0) {
                            for (GroupDetail.DataObject.Members member : dataObject.members) {
                                if (member.id == MyApplication.getInstance().UserInfo.getUserId()) {
                                    String nameInGroup = member.nameInGroup;
                                    if (!TextUtils.isEmpty(nameInGroup) && !"null".equals(nameInGroup)) {
                                        tv_myname.setText(member.nameInGroup);
                                    } else {
                                        tv_myname.setText("暂未设置");
                                    }
                                }
                            }
                        }
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ME_NAME_EDIT_CODE:
                    if (data == null) {
                        return;
                    }
                    edtName = data.getStringExtra("result");
                    Log.e("gy", "群名：" + edtName);
                    if (edtName == null) {
                        return;
                    }
                    tv_name.setText(edtName);
                    updateMyUser();
                    break;
                case ME_GONG_EDIT_CODE:
                    if (data == null) {
                        return;
                    }
                    edtGongGao = data.getStringExtra("result");
                    noticeContext.setText(edtGongGao);
                    updateGongGao();
                    break;
                case NICK_NAME_EDIT_CODE:
                    String name = data.getStringExtra("result");
                    tv_myname.setText(name);
                    updateMemberNickName(name);
                    break;
            }
        }
    }

    private void updateMemberNickName(String newNickName) {
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().UserInfo.getUserId());
        params.put("groupId", mId);
        params.put("nickname", newNickName);
        HttpUtil.post(Url.updateNickName, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        Log.e("TRACE", response.toString());
                    }
                } catch (Exception e) {
                    showToastMsg("数据异常，请重试");
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
                ShowDialog.dissmiss();
            }
        });
    }

    public void updateGongGao() {
        if (StringUtils.isEmpty(GroupId)) {
            return;
        }
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("groupId", GroupId);
        params.put("introduction", edtGongGao);
        HttpUtil.post(Url.notice, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("发布公告", response.toString());
                } catch (Exception e) {
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

    public void updateMyUser() {
        try {


            if (TextUtils.isEmpty(GroupId)) {
                return;
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("groupId", GroupId);
            map.put("name", edtName);
            RetrofitClient.getInstance().createApi().UpdateGroup(map)
                    .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                    .subscribe(new BaseObjObserver<CommonEntity>(this) {
                        @Override
                        protected void onHandleSuccess(CommonEntity commonEntity) {
                            Toast.makeText(GroupChatDetailActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpNickName(int isShowNickName) {
        if (StringUtils.isEmpty(GroupId)) {
            return;
        }
        RequestParams params = new RequestParams();
        params.put("groupId", GroupId);
        params.put("displayName", isShowNickName);
        HttpUtil.post(Url.updateName, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("修改详情", response.toString());
                } catch (Exception e) {
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

    public void updateMiandarao(int messageFlag) {
        if (StringUtils.isEmpty(GroupId)) {
            return;
        }
        RequestParams params = new RequestParams();
        params.put("groupId", GroupId);
        params.put("messageNo", messageFlag);
        Log.e("gy", "消息免打扰groupId：" + GroupId);
        Log.e("gy", "消息免打扰messageNo：" + messageFlag);
        HttpUtil.post(Url.updateName, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("修改详情", response.toString());
                } catch (Exception e) {
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

    public void updateSaveMail(int messageFlag) {
        if (StringUtils.isEmpty(GroupId)) {
            return;
        }
        RequestParams params = new RequestParams();
        params.put("groupId", GroupId);
        params.put("saveMail", messageFlag);
        HttpUtil.post(Url.updateName, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("修改详情", response.toString());
                } catch (Exception e) {
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

    public void updateZhiding(int topmessage) {
        if (StringUtils.isEmpty(GroupId)) {
            return;
        }
        RequestParams params = new RequestParams();
        params.put("groupId", GroupId);
        params.put("topmessage", topmessage);
        HttpUtil.post(Url.updateName, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("修改详情", response.toString());
                } catch (Exception e) {
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

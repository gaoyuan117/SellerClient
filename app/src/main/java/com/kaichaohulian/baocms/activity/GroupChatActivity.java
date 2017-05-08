package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.GroupChatAdapter;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.ecdemo.storage.GroupSqlManager;
import com.kaichaohulian.baocms.ecdemo.ui.group.CreateGroupActivity;
import com.kaichaohulian.baocms.ecdemo.ui.group.GroupService;
import com.kaichaohulian.baocms.entity.GroupEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yuntongxun.ecsdk.ECError;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 群聊界面
 * Created by ljl on 2016/12/14 0014.
 */
public class GroupChatActivity extends BaseActivity {
    public static final String IS_SELECT_GROUPID = "IS_SELECT_GROUPID";
    public static final String IS_FROM_MM = "IS_FROM_MM";
    public static final String FROM_MM_GROUPID = "FROM_MM_GROUPID";

    private TextView tv_total;
    private ListView listView;
    private List<GroupEntity> List;
    private GroupChatAdapter adapter;

    private boolean mIsSelectGroupID = false;
    private boolean mIsFromMM = false;
    private String mFromMMGroupid = "";

    @Override
    public void setContent() {
        setContentView(R.layout.groupchat_activity);
    }

    @Override
    public void initData() {
        mIsFromMM = getIntent().getBooleanExtra(IS_FROM_MM, false);
        mFromMMGroupid = getIntent().getStringExtra(FROM_MM_GROUPID);
        mIsSelectGroupID = getIntent().getBooleanExtra(IS_SELECT_GROUPID, false);
        List = new ArrayList<>();
        adapter = new GroupChatAdapter(getActivity(), List);
        GroupService.syncGroup(new GroupService.Callback() {
            @Override
            public void onSyncGroup() {

            }

            @Override
            public void onSyncGroupInfo(String groupId) {

            }

            @Override
            public void onGroupDel(String groupId) {

            }

            @Override
            public void onError(ECError error) {

            }

            @Override
            public void onUpdateGroupAnonymitySuccess(String groupId, boolean isAnonymity) {

            }
        });
    }

    @Override
    public void initView() {
        setCenterTitle("群聊");
        setIm1_view(R.mipmap.group_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GroupChatActivity.this, CreateGroupActivity.class));
            }
        });

        listView = getId(R.id.listView);

        View footerView = LayoutInflater.from(this).inflate(R.layout.item_contact_list_footer, null);
        tv_total = (TextView) footerView.findViewById(R.id.tv_total);
        tv_total.setVisibility(View.VISIBLE);
        listView.addFooterView(footerView);

        listView.setAdapter(adapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (listView != null && adapter != null) {
            getData();
        }
    }

    @Override
    public void initEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GroupEntity GroupEntity = adapter.getItem(position);
                if (GroupEntity != null) {
                    if (mIsSelectGroupID) {
                        Intent intent = new Intent();
                        intent.putExtra("sessionid", GroupEntity.getChatGroupId());
                        intent.putExtra("groupname", GroupEntity.getName());
                        intent.putExtra("groupid", "" + GroupEntity.getId());
                        setResult(RedBagActivity.REQ_GOURP_ID, intent);
                        finish();
                        return;
                    }
//                    Bundle Bundle = new Bundle();
//                    Bundle.putInt("id", GroupEntity.getId());
//                    Bundle.putString("chatGroupId", GroupEntity.getChatGroupId());
//                    ActivityUtil.next(getActivity(), GroupChatDetailActivity.class, Bundle);
                    CCPAppManager.startGroupChattingAction(GroupChatActivity.this, GroupEntity.getChatGroupId(), GroupEntity.getName(), GroupEntity.getId(), false);
                }
            }
        });
        getData();
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
                        List.clear();
                        List<String> allGroupIdByJoin = GroupSqlManager.getAllGroupIdBy(true);
                        JSONArray jsonArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e("gy", "size：" + jsonArray.length());
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            final GroupEntity GroupEntity = new GroupEntity();
                            GroupEntity.setCapacity(jsonObject.getInt("capacity"));
                            String chatGroupId = jsonObject.getString("chatGroupId");

                            //过滤不在容联管理中的群组，避免点击进入空白
                            boolean flag = false;
                            for (String tempGroupid : allGroupIdByJoin) {
                                if (tempGroupid.equals(chatGroupId)) {
                                    flag = true;
                                }
                            }
//                            if (flag) continue;
                            GroupEntity.setChatGroupId(chatGroupId);
                            GroupEntity.setCreatedTime(jsonObject.getString("createdTime"));
                            GroupEntity.setGroupNumber(jsonObject.getString("groupNumber"));
                            GroupEntity.setId(jsonObject.getInt("id"));
                            GroupEntity.setMemberCount(jsonObject.getInt("memberCount"));
                            GroupEntity.setName(jsonObject.getString("name"));
                            GroupEntity.setRole(jsonObject.getString("role"));
                            GroupEntity.setStatus(jsonObject.getString("status"));
                            JSONArray avatarArray = jsonObject.getJSONArray("images");
                            List<String> avatarList = new ArrayList<String>();
                            for (int j = 0; j < avatarArray.length(); j++) {
                                avatarList.add(avatarArray.getString(j));
                            }

                            SharedPrefsUtil.putValue(getActivity(), jsonObject.getString("chatGroupId"), avatarArray.toString()
                                    + "-x-" + jsonObject.getString("name") + "-x-" + jsonObject.getInt("id"));

                            GroupEntity.setAvatar(avatarList);
                            List.add(GroupEntity);
                            Log.e("gy", "list  size：" + List.size());
                            if (mIsFromMM) {
                                if (GroupEntity.getChatGroupId().equals(mFromMMGroupid)) {
                                    mHandler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            CCPAppManager.startGroupChattingAction(GroupChatActivity.this, GroupEntity.getChatGroupId(),
                                                    GroupEntity.getName(), GroupEntity.getId(), false);
                                            finish();
                                        }
                                    }, 500);
                                }
                            }
                        }
                    }
                    tv_total.setText(List.size() + "个群聊");
                    adapter.notifyDataSetChanged();
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
                ShowDialog.dissmiss();
            }
        });

    }

    Handler mHandler = new Handler();
}

package com.kaichaohulian.baocms.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.GroupMMAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.ecdemo.ui.group.GroupInfoActivity;
import com.kaichaohulian.baocms.entity.GroupEntity;
import com.kaichaohulian.baocms.entity.GroupMMEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimq on 2017/3/3.
 */
public class RelativeMMGroupActivity2 extends BaseActivity {
    private String mCode;
    private TextView tv_code;
    private GridView mGridView;
    private GroupMMAdapter mAdapter;
    private List<GroupMMEntity> data;
    private ProgressDialog progressDialog;

    private Handler handler = new Handler();

    @Override
    public void setContent() {
        setContentView(R.layout.activity_relative_group2);
        setCenterTitle("面对面建群");
    }

    Runnable updateRunn = new Runnable() {
        @Override
        public void run() {
            updateMemberData();
            handler.postDelayed(this, 5000);
        }
    };

    @Override
    public void initData() {
        data = new ArrayList<>();
        tv_code = getId(R.id.code);
        mGridView = getId(R.id.face2face_person);
        mCode = getIntent().getStringExtra("code");
        tv_code.setText(mCode);
        progressDialog = new ProgressDialog(this);
        mAdapter = new GroupMMAdapter(RelativeMMGroupActivity2.this, data);
        mGridView.setAdapter(mAdapter);
        updateMemberData();

        handler.postDelayed(updateRunn, 5000);
    }

    private void updateMemberData() {
        LogUtil.e("TRACE", "updateMemberData");
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("numberCode", mCode);
        HttpUtil.post(Url.GetUsersByNumber, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    LogUtil.e("TRACE", "updateMemberData : " + response.toString());
                    DBLog.e("面对面建群:", response.toString());
                    if (response.getInt("code") == 0) {
                        data.clear();
                        JSONArray array = response.getJSONArray("dataObject");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            GroupMMEntity entity = new GroupMMEntity();
                            int id = MyApplication.getInstance().UserInfo.getUserId();
                            int user_id = object.getInt("user_id");
//                            if (id == user_id) {
//                                continue;
//                            }
                            entity.setUser_id(object.getInt("user_id"));
                            entity.setAccount(object.getString("account"));
                            entity.setPassword(object.getString("password"));
                            entity.setAvator(object.getString("avator"));
                            entity.setUsername(object.getString("username"));
                            entity.setBalance(object.getString("balance"));
                            entity.setCreatedTime(object.getInt("createdTime"));
                            entity.setLastTime(object.getString("lastTime"));
                            entity.setPhoneNumber(object.getString("phoneNumber"));
                            entity.setEmail(object.getString("email"));
                            entity.setBshop(object.getInt("bshop"));
                            entity.setAudit(object.getInt("audit"));
                            entity.setRegip(object.getString("regip"));
                            entity.setInvite6(object.getInt("invite6"));
                            data.add(entity);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
//                    showToastMsg(response.getString("errorDescription"));
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

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        getId(R.id.join_in).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createGroup(data);
            }
        });
    }

    public void createGroup(List<GroupMMEntity> list) {
        JSONArray JSONArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            JSONArray.put(list.get(i).getUser_id());
        }
        RequestParams params = new RequestParams();
        JSONObject JSONObject = new JSONObject();
        try {
            JSONObject.put("id", MyApplication.getInstance().UserInfo.getUserId());
            JSONObject.put("userToInviteIds", JSONArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.put("JsonString", JSONObject);
        DBLog.e("tag", Url.groups_friends_add);
        progressDialog.setMessage("正在加入群聊..");
        progressDialog.show();
        HttpUtil.post(Url.groups_friends_add, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("创建群聊：", response.toString());
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
                        String name = response.getString("name");
                        String chatGroupId = response.getString("chatGroupId");
                        int groupNumber = response.getInt("groupNumber");

//                        CCPAppManager.startGroupChattingAction(RelativeMMGroupActivity2.this, chatGroupId,
//                                name, groupNumber, false);

                        Intent intent = new Intent(getActivity(), GroupChatActivity.class);
                        intent.putExtra(GroupChatActivity.IS_FROM_MM, true);
                        intent.putExtra(GroupChatActivity.FROM_MM_GROUPID, chatGroupId);
                        startActivity(intent);
                        finish();
                    }

                    finish();
                } catch (Exception e) {
                    showToastMsg("数据异常，请重试");
                    e.printStackTrace();
                } finally {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                progressDialog.dismiss();
            }
        });
    }

    //
//    public void getData(final String chatGroupId) {
//        RequestParams params = new RequestParams();
//        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
//        HttpUtil.post(Url.groups_all, params, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//                    DBLog.e("获取我加入和我创建的群", response.toString());
//                    if (response.getInt("code") == 0) {
//                        List<GroupEntity> mList = new ArrayList<>();
//                        JSONArray jsonArray = response.getJSONArray("dataObject");
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject jsonObject = jsonArray.getJSONObject(i);
//                            GroupEntity GroupEntity = new GroupEntity();
//                            GroupEntity.setCapacity(jsonObject.getInt("capacity"));
//                            GroupEntity.setChatGroupId(jsonObject.getString("chatGroupId"));
//                            GroupEntity.setCreatedTime(jsonObject.getString("createdTime"));
//                            GroupEntity.setGroupNumber(jsonObject.getString("groupNumber"));
//                            GroupEntity.setId(jsonObject.getInt("id"));
//                            GroupEntity.setMemberCount(jsonObject.getInt("memberCount"));
//                            GroupEntity.setName(jsonObject.getString("name"));
//                            GroupEntity.setRole(jsonObject.getString("role"));
//                            GroupEntity.setStatus(jsonObject.getString("status"));
//                            JSONArray avatarArray = jsonObject.getJSONArray("images");
//                            List<String> avatarList = new ArrayList<String>();
//                            for (int j = 0; j < avatarArray.length(); j++) {
//                                avatarList.add(avatarArray.getString(j));
//                            }
//                            SharedPrefsUtil.putValue(getActivity(), jsonObject.getString("chatGroupId"), avatarArray.toString()
//                                    + "-x-" + jsonObject.getString("name") + "-x-" + jsonObject.getInt("id"));
//                            GroupEntity.setAvatar(avatarList);
//                            mList.add(GroupEntity);
//                            if (GroupEntity.getRole().equals("USER")) {
//                                if (chatGroupId.equals(GroupEntity.getChatGroupId())) {
//                                    CCPAppManager.startGroupChattingAction(RelativeMMGroupActivity2.this, GroupEntity.getChatGroupId(),
//                                            GroupEntity.getName(), GroupEntity.getId(), false);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    ShowDialog.dissmiss();
//                }
//            }
//
//            @Override
//            public void onFinish() {
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                showToastMsg("请求服务器失败");
//                DBLog.e("tag", statusCode + ":" + responseString);
//                ShowDialog.dissmiss();
//            }
//        });
//    }
//
    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteCache();
        if (handler != null) {
            handler.removeCallbacks(updateRunn);
        }
    }

    private void deleteCache() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("numberCode", mCode);
        HttpUtil.post(Url.deleteCodeUser, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("面对面建群:", response.toString());
                    if (response.getInt("code") == 0) {
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
}

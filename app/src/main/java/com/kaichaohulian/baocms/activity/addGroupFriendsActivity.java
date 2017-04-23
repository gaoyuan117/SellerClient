package com.kaichaohulian.baocms.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.entity.GroupDetail;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class addGroupFriendsActivity extends BaseActivity {
    GroupDetail detail;
    private ListView listView;
    private TextView tv_checked;
    private Button del_in_button;
    private List<ContactFriendsEntity> contactList;
    private PickContactAdapter contactAdapter;
    private ProgressDialog progressDialog;

    //是否显示选择框
    private boolean isshow = true;
    //是否删除
    private boolean isdel = false;
    //是否禁言
    private boolean isjinyan = false;
    //是否转让
    private boolean iszhuanrang = false;

    //是否添加好友
    private boolean isAddMember = false;
    //是否删除好友
    private boolean isDelMember = false;

    private String groupId = null;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_add_group_friend);
    }

    @Override
    public void initData() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            detail = (GroupDetail) getIntent().getExtras().getSerializable("data");
            isdel = getIntent().getExtras().getBoolean("isdel", false);
            isshow = getIntent().getExtras().getBoolean("isshow", true);
            isjinyan = getIntent().getExtras().getBoolean("isjinyan", false);
            iszhuanrang = getIntent().getExtras().getBoolean("iszhuanrang", false);
            isAddMember = getIntent().getExtras().getBoolean("isAddMember", false);
            isDelMember = getIntent().getExtras().getBoolean("isDelMember", false);
        }
        if (detail == null) {
            finish();
        }
        if (detail.dataObject != null) {
            groupId = detail.dataObject.id + "";
        }
        TextView title = (TextView) findViewById(R.id.tv_title);
        tv_checked = getId(R.id.tv_checked);
        if (isAddMember) {
            title.setText("添加好友");
            tv_checked.setText("确定");
        }

        if (isDelMember) {
            title.setText("删除好友");
            tv_checked.setText("确定");
        }

        if (isdel) {
            title.setText("全部成员");
        } else {
            if (isjinyan) {
                title.setText("成员禁言");
            } else if (iszhuanrang) {
                title.setText("群主转让");
            }
        }
        contactList = new ArrayList<>();
    }

    @Override
    public void initView() {
        progressDialog = new ProgressDialog(this);
        listView = getId(R.id.list);
        del_in_button = getId(R.id.del_in_button);

        ImageView iv_back = getId(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getContactList();
    }

    @Override
    public void initEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (isshow) {
                    CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                    checkBox.toggle();
                }

            }
        });

        tv_checked.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isAddMember) {
                    save();
                    return;
                }

                if (isDelMember) {
//TODO 删除好友
                    del();
                    return;
                }

                if (isdel) {
                    if (tv_checked.getText().toString().trim().equals("删除")) {
                        tv_checked.setText("取消");
                        del_in_button.setVisibility(View.VISIBLE);
                        isshow = true;
                        if (contactAdapter != null) {
                            contactAdapter.notifyDataSetChanged();
                        }
                    } else {
                        tv_checked.setText("删除");
                        del_in_button.setVisibility(View.GONE);
                        isshow = false;
                        if (contactAdapter != null) {
                            contactAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    if (isjinyan) {
//                        jinyan();
                    } else if (iszhuanrang) {
                        zhuanrang();
                    } else {
                        save();
                    }

                }
            }
        });
    }

    /**
     * 获取联系人列表，并过滤掉黑名单和排序
     */
    private void getContactList() {
        if (isDelMember || isdel || isjinyan || iszhuanrang) {
            if (detail.dataObject != null && detail.dataObject.members != null && detail.dataObject.members.size() > 0) {
                List<GroupDetail.DataObject.Members> members = detail.dataObject.members;
                for (int k = 0; k < members.size(); k++) {
                    GroupDetail.DataObject.Members item = members.get(k);
                    ContactFriendsEntity ContactFriendsEntity = new ContactFriendsEntity();
                    ContactFriendsEntity.setId(item.id);
                    ContactFriendsEntity.setAvatar(item.avatar);
                    ContactFriendsEntity.setCreatedTime("");
                    ContactFriendsEntity.setImNumber("");
                    ContactFriendsEntity.setPhoneNumber("");
                    ContactFriendsEntity.setThermalSignatrue(item.thermalSignatrue);
                    ContactFriendsEntity.setUsername(item.username);
                    contactList.add(ContactFriendsEntity);
                }
                contactAdapter = new PickContactAdapter(getActivity(), R.layout.item_contactlist_listview_checkbox, contactList);
                listView.setAdapter(contactAdapter);
            }
        } else {
            RequestParams params = new RequestParams();
            params.put("id", MyApplication.getInstance().UserInfo.getUserId());
            HttpUtil.post(Url.getFriends, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        DBLog.e("获取通讯录好友：", response.toString());
                        if (response.getInt("code") == 0) {
                            contactList.clear();
                            JSONArray JSONArray = response.getJSONArray("dataObject");
                            for (int i = 0; i < JSONArray.length(); i++) {
                                ContactFriendsEntity ContactFriendsEntity = new ContactFriendsEntity();
                                JSONObject jsonObject = JSONArray.getJSONObject(i);
                                ContactFriendsEntity.setId(jsonObject.getInt("id"));
                                ContactFriendsEntity.setAvatar(jsonObject.getString("avatar"));
                                ContactFriendsEntity.setCreatedTime(jsonObject.getString("createdTime"));
                                ContactFriendsEntity.setImNumber(jsonObject.getString("imNumber"));
                                ContactFriendsEntity.setPhoneNumber(jsonObject.getString("phoneNumber"));
                                ContactFriendsEntity.setThermalSignatrue(jsonObject.getString("thermalSignatrue"));
                                ContactFriendsEntity.setUsername(jsonObject.getString("username"));
                                if (detail.dataObject != null && detail.dataObject.members != null && detail.dataObject.members.size() > 0) {
                                    List<GroupDetail.DataObject.Members> members = detail.dataObject.members;
                                    boolean iscf = false;
                                    for (int k = 0; k < members.size(); k++) {
                                        if (members.get(k).id == jsonObject.getInt("id")) {
                                            iscf = true;
                                        }
                                    }
                                    if (!iscf) {
                                        contactList.add(ContactFriendsEntity);
                                    }
                                } else {
                                    contactList.add(ContactFriendsEntity);
                                }
                            }
                        }
                        if (contactList == null || contactList.size() <= 0) {
                            showToastMsg("您的全部好友已加入该群，暂无更多好友");
                        }
                        contactAdapter = new PickContactAdapter(getActivity(), R.layout.item_contactlist_listview_checkbox, contactList);
                        listView.setAdapter(contactAdapter);
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

    List<String> addList;

    /**
     * 确认选择的members
     */
    public void save() {
        contactList = contactAdapter.getListFriends();
        addList = new ArrayList<>();
        for (int i = 0; i < contactList.size(); i++) {
            ContactFriendsEntity item = contactList.get(i);
            if (item.isSelect()) {
                addList.add(item.getId() + "");
            }
        }
        if (addList.size() == 0) {
            showToastMsg("请先选择");
            return;
        }
        progressDialog.setMessage("正在加人...");
        progressDialog.show();
        try {
            createNewGroup(addList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void zhuanrang() {
        contactList = contactAdapter.getListFriends();
        addList = new ArrayList<>();
        for (int i = 0; i < contactList.size(); i++) {
            ContactFriendsEntity item = contactList.get(i);
            if (item.isSelect()) {
                addList.add(item.getId() + "");
            }
        }
        if (addList.size() == 0) {
            showToastMsg("请先选择");
            return;
        }
        progressDialog.setMessage("正在转让...");
        progressDialog.show();
        try {
            zhuanrangGroup(addList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void del() {
        contactList = contactAdapter.getListFriends();
        addList = new ArrayList<>();
        for (int i = 0; i < contactList.size(); i++) {
            ContactFriendsEntity item = contactList.get(i);
            if (item.isSelect()) {
                addList.add(item.getId() + "");
            }
        }
        if (addList.size() == 0) {
            showToastMsg("请先选择");
            return;
        }
        if (addList.size() == contactList.size()) {
            showToastMsg("群组成员不能为空");
            return;
        }
        progressDialog.setMessage("正在删除...");
        progressDialog.show();
        try {
            delGroup(addList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void zhuanrangGroup(List<String> members) throws JSONException {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("groupId", groupId);
        params.put("transferTo", members.get(0));
        HttpUtil.post(Url.ZHUAN_RANG_QUN_ZU, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("群成员禁言：", response.toString());
                    if (response.getInt("code") == 0) {
                        finish();
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
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

    private void delGroup(List<String> members) throws JSONException {
        JSONArray JSONArray = new JSONArray();
        for (int i = 0; i < members.size(); i++) {
            JSONArray.put(members.get(i));
        }
        RequestParams params = new RequestParams();
        JSONObject JSONObject = new JSONObject();
        JSONObject.put("groupId", groupId);
        JSONObject.put("userToInviteIds", JSONArray);
        params.put("JsonString", JSONObject);
        HttpUtil.post(Url.groups_friends_removeMembers, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("删除群成员：", response.toString());
                    if (response.getInt("code") == 0) {
                        finish();
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
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

    /**
     * 创建新群组
     */
    private void createNewGroup(List<String> members) throws JSONException {
        JSONArray JSONArray = new JSONArray();
        for (int i = 0; i < members.size(); i++) {
            JSONArray.put(members.get(i));
        }
        RequestParams params = new RequestParams();
        JSONObject JSONObject = new JSONObject();
        JSONObject.put("groupId", groupId);
        JSONObject.put("userToInviteIds", JSONArray);
        params.put("JsonString", JSONObject);
        HttpUtil.post(Url.groups_friends_invites, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("创建群聊：", response.toString());
                    if (response.getInt("code") == 0) {
                        finish();
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
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

    /**
     * adapter
     */
    private class PickContactAdapter extends BaseAdapter implements CheckBox.OnCheckedChangeListener {

        private LayoutInflater layoutInflater;
        private boolean[] isCheckedArray;
        private List<ContactFriendsEntity> mListFriends = new ArrayList<ContactFriendsEntity>();
        private int res;

        public PickContactAdapter(Context context, int resource, List<ContactFriendsEntity> users) {
            this.res = resource;
            this.mListFriends = users;
            layoutInflater = LayoutInflater.from(context);
            isCheckedArray = new boolean[mListFriends.size()];
        }

        public List<ContactFriendsEntity> getListFriends() {
            return mListFriends;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = layoutInflater.inflate(res, null);
            ImageView iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            ContactFriendsEntity user = getItem(position);
            if (user != null) {
                String avater = user.getAvatar();
                String name = user.getUsername();
                iv_avatar.setImageResource(R.mipmap.default_useravatar);
                if (!StringUtils.isEmpty(avater)) {
                    ImageLoader.getInstance().displayImage(avater, iv_avatar);
                }
                if (!StringUtils.isEmpty(name)) {
                    tv_name.setText(name);
                } else {
                    tv_name.setText("未命名");
                }
                if (isshow) {
                    if (user.isSelect()) {
                        checkBox.setChecked(true);
                    } else {
                        checkBox.setChecked(false);
                    }
                    checkBox.setVisibility(View.VISIBLE);
                    checkBox.setTag(position);
                    checkBox.setOnCheckedChangeListener(this);
                } else {
                    checkBox.setVisibility(View.GONE);
                }
            }
            return convertView;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mListFriends.size();
        }

        @Override
        public ContactFriendsEntity getItem(int position) {
            try {
                return mListFriends.get(position);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (iszhuanrang) {
                for (int i = 0; i < mListFriends.size(); i++) {
                    ContactFriendsEntity mListItem = getItem(i);
                    mListItem.setIsSelect(false);
                }
            }
            int mPosition = Integer.parseInt(buttonView.getTag().toString());
            ContactFriendsEntity mItem = getItem(mPosition);
            mItem.setIsSelect(!mItem.isSelect());
            notifyDataSetChanged();
        }
    }
}

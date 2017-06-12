package com.kaichaohulian.baocms.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.ui.SDKCoreHelper;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yuntongxun.ecsdk.ECGroupManager;
import com.yuntongxun.ecsdk.im.ECGroup;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class CreatChatRoomActivity extends BaseActivity {
    private ImageView iv_search;
    private TextView tv_checked;
    private EditText et_search;
    private ListView listView;
    /**
     * 是否为一个新建的群组
     */
    protected boolean isCreatingNewGroup;
    /**
     * 是否为单选
     */
    private boolean isSignleChecked;
    private PickContactAdapter contactAdapter;
    /**
     * group中一开始就有的成员
     */
    private List<String> exitingMembers = new ArrayList<String>();
    // 可滑动的显示选中用户的View
    private LinearLayout menuLinerLayout;

    // 选中用户总数,右上角显示
    int total = 0;
    private String userId = null;
    private String groupId = null;
    private ProgressDialog progressDialog;
    private String groupname;
    private String hxid;
    // 添加的列表
    private List<String> addList = new ArrayList<String>();
    private List<ContactFriendsEntity> contactList;


    /**
     * 创建的群组
     */
    private ECGroup group;


    @Override
    public void setContent() {
        setContentView(R.layout.activity_chatroom);
    }

    @Override
    public void initData() {
        goBack();
        contactList = new ArrayList<>();
        hxid = MyApplication.getInstance().UserInfo.getAccountNumber();

        progressDialog = new ProgressDialog(this);
        groupId = getIntent().getStringExtra("groupId");
        userId = getIntent().getStringExtra("userId");

        tv_checked = (TextView) this.findViewById(R.id.tv_checked);

        if (groupId != null) {

//            isCreatingNewGroup = false;
//            group = EMGroupManager.getInstance().getGroup(groupId);
//            if (group != null) {
//                exitingMembers = group.getMembers();
//                groupname = group.getGroupName();
//            }

        } else if (userId != null) {

            isCreatingNewGroup = true;
            exitingMembers.add(userId);
            total = 1;
            addList.add(userId);
        } else {

            isCreatingNewGroup = true;
        }
    }

    @Override
    public void initView() {
        iv_search = getId(R.id.iv_search);
        listView = getId(R.id.list);
        menuLinerLayout = getId(R.id.linearLayoutMenu);
        et_search = getId(R.id.et_search);

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View headerView = layoutInflater.inflate(R.layout.item_chatroom_header, null);
        TextView tv_header = (TextView) headerView.findViewById(R.id.tv_header);
        tv_header.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(CreatChatRoomActivity.this, ChatRoomActivity.class));
//                finish();
            }

        });
//        listView.addHeaderView(headerView);
    }

    @Override
    public void initEvent() {
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                checkBox.toggle();

            }
        });
        tv_checked.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                save();
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.length() > 0) {
                    String str_s = et_search.getText().toString().trim();
                    List<ContactFriendsEntity> users_temp = new ArrayList<ContactFriendsEntity>();
                    for (ContactFriendsEntity user : contactList) {
                        String usernick = user.getUsername();
                        Log.e("usernick--->>>", usernick);
                        Log.e("str_s--->>>", str_s);

                        if (usernick.contains(str_s)) {

                            users_temp.add(user);
                        }
                        contactAdapter = new PickContactAdapter(
                                CreatChatRoomActivity.this,
                                R.layout.item_contactlist_listview_checkbox,
                                users_temp);
                        listView.setAdapter(contactAdapter);

                    }

                } else {
                    contactAdapter = new PickContactAdapter(
                            CreatChatRoomActivity.this,
                            R.layout.item_contactlist_listview_checkbox,
                            contactList);
                    listView.setAdapter(contactAdapter);
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {

            }
        });

        getContactList();
    }

    // 即时显示被选中用户的头像和昵称。

    private void showCheckImage(String avatar, ContactFriendsEntity glufineid) {

        if (exitingMembers.contains(glufineid.getId()) && groupId != null) {
            return;
        }
        if (addList.contains(glufineid.getId())) {
            return;
        }
        total++;

        // 包含TextView的LinearLayout
        // 参数设置
        LinearLayout.LayoutParams menuLinerLayoutParames = new LinearLayout.LayoutParams(
                108, 108, 1);
        View view = LayoutInflater.from(this).inflate(
                R.layout.item_chatroom_header_item, null);
        ImageView images = (ImageView) view.findViewById(R.id.iv_avatar);
        menuLinerLayoutParames.setMargins(6, 6, 6, 6);

        // 设置id，方便后面删除
        view.setTag(glufineid);
        if (avatar == null) {
            images.setImageResource(R.mipmap.default_useravatar);
        } else {
            Glide.with(getActivity()).load(avatar).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(images);
        }

        menuLinerLayout.addView(view, menuLinerLayoutParames);
        tv_checked.setText("确定(" + total + ")");
        if (total > 0) {
            if (iv_search.getVisibility() == View.VISIBLE) {
                iv_search.setVisibility(View.GONE);
            }
        }
        addList.add(glufineid.getId() + "");
    }

    private void deleteImage(ContactFriendsEntity glufineid) {
        View view = menuLinerLayout.findViewWithTag(glufineid);

        menuLinerLayout.removeView(view);
        total--;
        tv_checked.setText("确定(" + total + ")");
        addList.remove(glufineid.getUsername());
        if (total < 1) {
            if (iv_search.getVisibility() == View.GONE) {
                iv_search.setVisibility(View.VISIBLE);
            }
        }

    }

    /**
     * 确认选择的members
     */
    public void save() {
        if (addList.size() == 0) {
            Toast.makeText(CreatChatRoomActivity.this, "请选择用户", Toast.LENGTH_LONG).show();
            return;
        }
        // 如果只有一个用户说明只是单聊,并且不是从群组加人
        if (addList.size() == 1 && isCreatingNewGroup) {
            String userId = addList.get(0);
            ContactFriendsEntity user = contactList.get(0);
            if (user != null) {
                String userNick = user.getUsername();
                String userAvatar = user.getAvatar();
                startActivity(new Intent(getApplicationContext(), ChatActivity.class).putExtra("userId", userId).putExtra("userNick", userNick).putExtra("userAvatar", userAvatar));
                finish();
            }
        } else {
            if (isCreatingNewGroup) {
                progressDialog.setMessage("正在创建群聊...");
            } else {
                progressDialog.setMessage("正在加人...");
            }
            progressDialog.show();

            ECGroupManager ecGroupManager = SDKCoreHelper.getECGroupManager();
            if (ecGroupManager == null) {
                return;
            }
            // 调用API创建群组、处理创建群组接口回调
            try {
                createNewGroup(addList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
        JSONObject.put("id", MyApplication.getInstance().UserInfo.getUserId());
        JSONObject.put("userToInviteIds", JSONArray);
        params.put("JsonString", JSONObject);
        DBLog.e("gy", "userToInviteIds："+JSONArray.toString());
        DBLog.e("gy", "JsonString："+JSONObject.toString());
        HttpUtil.post(Url.groups_friends_add, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("创建群聊：", response.toString());
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
                        String chatGroupId = response.getString("chatGroupId");

                        Intent intent = new Intent(getActivity(), GroupChatActivity.class);
                        intent.putExtra(GroupChatActivity.IS_FROM_MM, true);
                        intent.putExtra(GroupChatActivity.FROM_MM_GROUPID, chatGroupId);
                        startActivity(intent);
                        finish();
                    }
                    showToastMsg(response.getString("errorDescription"));
                    finish();
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

    private void updateGroupName(String groupId, String updateStr) {
    }


    private void dismissPostingDialog() {
        if (progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
    }


    /**
     * adapter
     */
    private class PickContactAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private boolean[] isCheckedArray;
        private List<ContactFriendsEntity> list = new ArrayList<ContactFriendsEntity>();
        private int res;

        public PickContactAdapter(Context context, int resource, List<ContactFriendsEntity> users) {
            this.res = resource;
            this.list = users;
            layoutInflater = LayoutInflater.from(context);
            isCheckedArray = new boolean[list.size()];

        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            convertView = layoutInflater.inflate(res, null);

            ImageView iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            TextView tvHeader = (TextView) convertView.findViewById(R.id.header);
            final ContactFriendsEntity user = list.get(position);

            final String avater = user.getAvatar();
            String name = user.getUsername();
            String header = user.getHeader();
            final String username = user.getId() + "";
            tv_name.setText(name);
            iv_avatar.setImageResource(R.mipmap.default_useravatar);
            if (avater != null && !avater.equals("")) {
                Glide.with(getActivity()).load(avater).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(iv_avatar);
            }
            if (position == 0 || header != null
                    && !header.equals(getItem(position - 1))) {
                if ("".equals(header)) {
                    tvHeader.setVisibility(View.GONE);
                } else {
                    tvHeader.setVisibility(View.VISIBLE);
                    tvHeader.setText(header);
                }
            } else {
                tvHeader.setVisibility(View.GONE);
            }

            // 选择框checkbox
            final CheckBox checkBox = (CheckBox) convertView
                    .findViewById(R.id.checkbox);

//            if (exitingMembers != null && exitingMembers.contains(username)) {
//                checkBox.setButtonDrawable(R.drawable.btn_check);
//            } else {
//                checkBox.setButtonDrawable(R.drawable.check_blue);
//            }

            if (addList != null && addList.contains(username)) {
                checkBox.setChecked(true);
                isCheckedArray[position] = true;
            }
            if (checkBox != null) {
                checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView,
                                                 boolean isChecked) {
                        // 群组中原来的成员一直设为选中状态
                        if (exitingMembers.contains(username)) {
                            isChecked = true;
                            checkBox.setChecked(true);
                        }
                        isCheckedArray[position] = isChecked;
                        // 如果是单选模式
                        if (isSignleChecked && isChecked) {
                            for (int i = 0; i < isCheckedArray.length; i++) {
                                if (i != position) {
                                    isCheckedArray[i] = false;
                                }
                            }
                            contactAdapter.notifyDataSetChanged();
                        }

                        if (isChecked) {
                            // 选中用户显示在滑动栏显示
                            showCheckImage(avater,
                                    list.get(position));

                        } else {
                            // 用户显示在滑动栏删除
                            deleteImage(list.get(position));

                        }

                    }
                });
                // 群组中原来的成员一直设为选中状态
                if (exitingMembers.contains(username)) {
                    checkBox.setChecked(true);
                    isCheckedArray[position] = true;
                } else {
                    checkBox.setChecked(isCheckedArray[position]);
                }

            }
            return convertView;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public String getItem(int position) {
            if (position < 0) {
                return "";
            }

            String header = list.get(position).getHeader();

            return header;

        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }
    }

    @SuppressLint("DefaultLocale")
    public class PinyinComparator implements Comparator<ContactFriendsEntity> {

        @SuppressLint("DefaultLocale")
        @Override
        public int compare(ContactFriendsEntity o1, ContactFriendsEntity o2) {
            // TODO Auto-generated method stub
            String py1 = o1.getHeader();
            String py2 = o2.getHeader();
            // 判断是否为空""
            if (isEmpty(py1) && isEmpty(py2))
                return 0;
            if (isEmpty(py1))
                return -1;
            if (isEmpty(py2))
                return 1;
            String str1 = "";
            String str2 = "";
            try {
                str1 = ((o1.getHeader()).toUpperCase()).substring(0, 1);
                str2 = ((o2.getHeader()).toUpperCase()).substring(0, 1);
            } catch (Exception e) {
                System.out.println("某个str为\" \" 空");
            }
            return str1.compareTo(str2);
        }

        private boolean isEmpty(String str) {
            return str == null || "".equals(str.trim());
        }
    }


    /**
     * 获取联系人列表，并过滤掉黑名单和排序
     */
    private void getContactList() {
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
                            contactList.add(ContactFriendsEntity);
                        }
                        // 对list进行排序
                        Collections.sort(contactList, new PinyinComparator() {
                        });
                    }
                    contactAdapter = new PickContactAdapter(getActivity(), R.layout.item_contactlist_listview_checkbox, contactList);
                    listView.setAdapter(contactAdapter);
//                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }

}

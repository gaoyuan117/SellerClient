package com.kaichaohulian.baocms.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Adminis on 2017/1/17.
 */
public class AddMemberActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_search, mIvBack;
    private TextView tv_checked, mTvTitle, mTvNoMessage;
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
    private List<String> exitingMembers = new ArrayList<>();
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
    private List<String> addList = new ArrayList<>();
    private List<ContactFriendsEntity> contactList;
    private List<ContactFriendsEntity> selectContactList = new ArrayList<>();
    private MyApplication application;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_chatroom);
    }

    @Override
    public void initData() {
        contactList = new ArrayList<>();
        hxid = MyApplication.getInstance().UserInfo.getAccountNumber();
        progressDialog = new ProgressDialog(this);
        application = (MyApplication) getApplication();
//        groupId = getIntent().getStringExtra("groupId");
//        userId = getIntent().getStringExtra("userId");
//
//        if (groupId != null) {
////            isCreatingNewGroup = false;
////            group = EMGroupManager.getInstance().getGroup(groupId);
////            if (group != null) {
////                exitingMembers = group.getMembers();
////                groupname = group.getGroupName();
////            }
//        } else if (userId != null) {
//            isCreatingNewGroup = true;
//            exitingMembers.add(userId);
//            total = 1;
//            addList.add(userId);
//        } else {
//            isCreatingNewGroup = true;
//        }
    }

    @Override
    public void initView() {
        mIvBack = getId(R.id.iv_back);
        mTvTitle = getId(R.id.tv_title);
        mTvTitle.setText("选择联系人");
        tv_checked = getId(R.id.tv_checked);
        iv_search = getId(R.id.iv_search);
        listView = getId(R.id.list);
        menuLinerLayout = getId(R.id.linearLayoutMenu);
        et_search = getId(R.id.et_search);
        mTvNoMessage = getId(R.id.tv_header);
        getContactList();
    }

    @Override
    public void initEvent() {
        mIvBack.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                checkBox.toggle();
//                if (exitingMembers.contains(contactList.get(position).getId() + "")) {
//                    return;
//                }
//                contactAdapter.notifyDataSetChanged();

            }
        });
        tv_checked.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                save();
            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    String str_s = et_search.getText().toString().trim();
                    List<ContactFriendsEntity> users_temp = new ArrayList<>();
                    for (ContactFriendsEntity user : contactList) {
                        String userNick = user.getUsername();
                        if (userNick.contains(str_s)) {
                            users_temp.add(user);
                            contactAdapter = new PickContactAdapter(AddMemberActivity.this, R.layout.item_contactlist_listview_checkbox, users_temp);
                            listView.setAdapter(contactAdapter);
                        } else {
                            mTvNoMessage.setVisibility(View.VISIBLE);
                            listView.setVisibility(View.GONE);
                        }
                    }
                } else {
                    listView.setVisibility(View.VISIBLE);
                    contactAdapter = new PickContactAdapter(AddMemberActivity.this, R.layout.item_contactlist_listview_checkbox, contactList);
                    listView.setAdapter(contactAdapter);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {

            }
        });
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
        LinearLayout.LayoutParams menuLinerLayoutParames = new LinearLayout.LayoutParams(108, 108, 1);
        View view = LayoutInflater.from(this).inflate(R.layout.item_chatroom_header_item, null);
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
                tv_checked.setText("确定");
            }
        }
    }

    /**
     * 确认选择的members
     */
    public void save() {
        if (addList.size() == 0) {
            Toast.makeText(AddMemberActivity.this, "请选择联系人", Toast.LENGTH_LONG).show();
            return;
        }
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).isSelect()) {
                selectContactList.add(contactList.get(i));
            }
        }
        application.setContactList(selectContactList);
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                break;
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
                    if (response.getInt("code") == 0) {
                        Log.e("TAG", response.toString());
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
                            if (exitingMembers.contains(ContactFriendsEntity.getId() + "")) {
                                ContactFriendsEntity.setIsSelect(true);
                            }
                            contactList.add(ContactFriendsEntity);
                        }
                        // 对list进行排序
//                        Collections.sort(contactList, new PinyinComparator() {});
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

    @SuppressLint("DefaultLocale")
    public class PinyinComparator implements Comparator<ContactFriendsEntity> {

        @SuppressLint("DefaultLocale")
        @Override
        public int compare(ContactFriendsEntity o1, ContactFriendsEntity o2) {
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
            return "".equals(str.trim());
        }
    }

    /**
     * adapter
     */
    private class PickContactAdapter extends BaseAdapter {
        private LayoutInflater layoutInflater;
        private boolean[] isCheckedArray;
        private List<ContactFriendsEntity> list = new ArrayList<>();
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
            if (position == 0 || header != null && !header.equals(getItem(position - 1))) {
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
            final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            if (exitingMembers != null && exitingMembers.contains(username)) {
                checkBox.setButtonDrawable(R.drawable.btn_check);
            } else {
                checkBox.setButtonDrawable(R.drawable.check_blue);
            }
//            if (user.isSelect()) {
//                checkBox.setChecked(true);
//            } else {
//                checkBox.setChecked(false);
//            }
            if (addList != null && addList.contains(username)) {
                checkBox.setChecked(true);
                isCheckedArray[position] = true;
            }
            if (checkBox != null) {
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
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
                            contactList.get(position).setIsSelect(true);
                            // 选中用户显示在滑动栏显示
                            showCheckImage(avater, list.get(position));
                        } else {
                            contactList.get(position).setIsSelect(false);
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
            return position;
        }
    }

}

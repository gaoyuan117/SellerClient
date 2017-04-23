package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.RemindFriendsAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.HanziToPinyin;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.kaichaohulian.baocms.view.SidebarRemind;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by LMS on 2017/2/18.
 */

public class RemindFriendsSeeActivity extends BaseActivity {

    private RemindFriendsAdapter adapter;
    private List<ContactFriendsEntity> contactList;
    private ListView listView;
    private boolean hidden;
    private SidebarRemind sidebar;
    ArrayList<Integer> remindList  = null;
    ArrayList<Integer> userList  = null;

    private TextView tv_total;
    private LayoutInflater infalter;

    TextView rightTitle;
    private RelativeLayout re_search;
    private int type;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_remind_friends);
    }

    @Override
    public void initData() {
        type = getIntent().getIntExtra("type", 0);
        remindList = new ArrayList<>();
        userList = new ArrayList<>();
        contactList = new ArrayList<ContactFriendsEntity>();
    }

    @Override
    public void initView() {
        setCenterTitle("提醒谁看");
        setLeftTitle("取消");
        rightTitle = setRightTitle("确定");

        listView = getId(R.id.listHaoYou);
        re_search = getId(R.id.re_search_remind);

        infalter = LayoutInflater.from(this);
        View footerView = infalter.inflate(R.layout.item_contact_list_footer, null);
        listView.addFooterView(footerView);

        sidebar = getId(R.id.sidebar);
        sidebar.setListView(listView);

        tv_total = (TextView) footerView.findViewById(R.id.tv_total);

        // 设置adapter
        adapter = new RemindFriendsAdapter(this, R.layout.item_remind_friends_list, contactList);
        listView.setAdapter(adapter);

        // 获取设置contactlist
        getContactList();




    }

    @Override
    public void initEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Boolean aBoolean = RemindFriendsAdapter.getIsSelected().get(position);
                if(aBoolean){
                    RemindFriendsAdapter.getIsSelected().put(position,false);
                    if(type==1){
                        remindList.remove(position);
                    }else{
                        userList.remove(position);
                    }
                }else{
                    RemindFriendsAdapter.getIsSelected().put(position,true);
                    if(type == 1){
                        remindList.add(contactList.get(position).getId());
                    }else{
                        userList.add(contactList.get(position).getId());
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        rightTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putIntegerArrayListExtra("reminds",remindList);
                intent.putIntegerArrayListExtra("users",userList);
                setResult(200,intent);
                DBLog.i("提醒的好友发出",remindList);
                RemindFriendsSeeActivity.this.finish();
            }
        });
        re_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemindFriendsSeeActivity.this,MyFriendsSearchActivity.class);
                startActivity(intent);
            }
        });
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
                            if (Character.isDigit(ContactFriendsEntity.getUsername().charAt(0))) {
                                ContactFriendsEntity.setHeader("#");
                            } else {
                                ContactFriendsEntity.setHeader(HanziToPinyin.getInstance().get(ContactFriendsEntity.getUsername().substring(0, 1))
                                        .get(0).target.substring(0, 1).toUpperCase());
                                char header = ContactFriendsEntity.getHeader().toLowerCase().charAt(0);
                                if (header < 'a' || header > 'z') {
                                    ContactFriendsEntity.setHeader("#");
                                }
                            }
                            contactList.add(ContactFriendsEntity);

                            SharedPrefsUtil.putValue(getActivity(), ContactFriendsEntity.getPhoneNumber(), ContactFriendsEntity.getAvatar() + "-x-" + ContactFriendsEntity.getUsername());
                            SharedPrefsUtil.putValue(getActivity(), ContactFriendsEntity.getId() + "", ContactFriendsEntity.getAvatar() + "-x-" + ContactFriendsEntity.getUsername());

                        }
                        tv_total.setText(String.valueOf(contactList.size()) + "位联系人");
                        // 对list进行排序
                        Collections.sort(contactList, new PinyinComparator() {
                        });
                        adapter.notifyDataSetChanged();
                        adapter.initDate();
                    }
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

    public class PinyinComparator implements Comparator<ContactFriendsEntity> {
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
}

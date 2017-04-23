package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.AddressListAdapter;
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
 * Created by Jimq on 2017/2/18.
 */

public class AddressListActivity extends BaseActivity {

    private AddressListAdapter adapter;
    private List<ContactFriendsEntity> contactList;
    private ListView listView;
    private boolean hidden;
    private SidebarRemind sidebar;
    ArrayList<String> remindList = null;

    private TextView tv_total;
    private LayoutInflater infalter;

    TextView rightTitle;
    private boolean isFromRedpck;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_remind_friends);
    }

    @Override
    public void initData() {
        remindList = new ArrayList<>();
        contactList = new ArrayList<ContactFriendsEntity>();
    }

    @Override
    public void initView() {
        setCenterTitle("通讯录");

        isFromRedpck = getIntent().getBooleanExtra("isFromRedpck", false);

        listView = getId(R.id.listHaoYou);
        infalter = LayoutInflater.from(this);
        View footerView = infalter.inflate(R.layout.item_contact_list_footer, null);
        listView.addFooterView(footerView);

        sidebar = getId(R.id.sidebar);
        sidebar.setListView(listView);

        tv_total = (TextView) footerView.findViewById(R.id.tv_total);

        // 设置adapter
        adapter = new AddressListAdapter(this, R.layout.item_tranfer_address_list, contactList);
        listView.setAdapter(adapter);

        // 获取设置contactlist
        getContactList();
    }

    @Override
    public void initEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isFromRedpck) {
                    Intent pickSessionIntent = new Intent();
                    pickSessionIntent.putExtra("sessionid", contactList.get(position).getPhoneNumber());
                    setResult(0, pickSessionIntent);
                    finish();
                    return;
                }
                Intent intent = new Intent(AddressListActivity.this, WithDrawToFriendActivity.class);
                intent.putExtra("contact_logo", contactList.get(position).getAvatar());
                intent.putExtra("mRecipients", (long) contactList.get(position).getId());
                intent.putExtra("contact_user", contactList.get(position).getUsername());
                intent.putExtra("contact_id", "" + contactList.get(position).getId());
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

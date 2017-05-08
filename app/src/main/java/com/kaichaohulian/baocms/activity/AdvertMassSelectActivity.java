package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.AdvertMassSelectAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.ChineseToEnglish;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.kaichaohulian.baocms.view.Sidebar;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvertMassSelectActivity extends BaseActivity {


    @BindView(R.id.listHaoYou_selectorFriend)
    ListView listHaoYou;
    @BindView(R.id.sidebar_selectorFriend)
    Sidebar sidebar;
    private AdvertMassSelectAdapter adapter;
    private List<ContactFriendsEntity> contactList;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_advert_mass_select);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        contactList = new ArrayList<>();
        sidebar.setListView(listHaoYou);
        adapter = new AdvertMassSelectAdapter(this, R.layout.item_advertselectlist, contactList);
        listHaoYou.setAdapter(adapter);

        getContactList();
    }

    @Override
    public void initView() {
        setCenterTitle("好友群发");
        TextView tv = setRightTitle("下一步");
        tv.setBackgroundResource(R.mipmap.rounded_rectangle);
        tv.setTextColor(getResources().getColor(R.color.ccp_green_alpha));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer buffer=new StringBuffer();
                HashSet set=new HashSet();
                set=adapter.GetSet();
                if(set.size()==0){
                    Toast.makeText(AdvertMassSelectActivity.this, "请选择要群发的好友", Toast.LENGTH_SHORT).show();
                    return;
                }
                Iterator<ContactFriendsEntity> iterator = set.iterator();
                if(iterator.hasNext()){
                    buffer.append(iterator.next().getId()+",");
                }
                buffer.append(MyApplication.getInstance().UserInfo.getUserId());
                Intent intent=new Intent(getActivity(),ReleaseAdvertActivity.class);
                intent.putExtra("ids",buffer.toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void initEvent() {

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
                            ContactFriendsEntity contract = new ContactFriendsEntity();
                            JSONObject jsonObject = JSONArray.getJSONObject(i);
                            contract.setId(jsonObject.getInt("id"));
                            contract.setAvatar(jsonObject.getString("avatar"));
                            contract.setCreatedTime(jsonObject.getString("createdTime"));
                            contract.setImNumber(jsonObject.getString("imNumber"));
                            contract.setPhoneNumber(jsonObject.getString("phoneNumber"));
                            contract.setThermalSignatrue(jsonObject.getString("thermalSignatrue"));
                            contract.setUsername(jsonObject.getString("username"));
                            if (Character.isDigit(contract.getUsername().charAt(0))) {
                                contract.setHeader("#");
                            } else {
                                contract.setHeader(ChineseToEnglish.getInstance().getSelling(contract.getUsername()).trim().substring(0, 1));
                                char header = contract.getHeader().toLowerCase().charAt(0);
                                if (header < 'a' || header > 'z') {
                                    contract.setHeader("#");
                                }

                            }
                            boolean flag = false;
                            for (ContactFriendsEntity tempContact : contactList) {
                                if (tempContact.getId() == contract.getId()) {
                                    flag = true;
                                    break;
                                }
                            }
                            if (!flag) {
                                contactList.add(contract);
                                SharedPrefsUtil.putValue(getActivity(), contract.getPhoneNumber(), contract.getAvatar() + "-x-" + contract.getUsername());
                                SharedPrefsUtil.putValue(getActivity(), contract.getId() + "", contract.getAvatar() + "-x-" + contract.getUsername());
                            }
                        }
                        // 对list进行排序
                        Collections.sort(contactList, new PinyinComparator() {
                        });
                        adapter.notifyDataSetChanged();
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

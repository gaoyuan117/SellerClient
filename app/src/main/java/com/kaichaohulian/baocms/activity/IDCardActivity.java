package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.AdvertMassSelectAdapter;
import com.kaichaohulian.baocms.adapter.IDCardAdapter;
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
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IDCardActivity extends BaseActivity {


    @BindView(R.id.listHaoYou_selectorFriend)
    ListView listHaoYou;
    @BindView(R.id.sidebar_selectorFriend)
    Sidebar sidebar;
    private IDCardAdapter adapter;
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
        adapter = new IDCardAdapter(this, R.layout.item_id_card, contactList);
        listHaoYou.setAdapter(adapter);

        try {
            getContactList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initView() {
        setCenterTitle("选取好友");
    }

    @Override
    public void initEvent() {
        listHaoYou.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("data", contactList.get(position));
                setResult(111, intent);
                finish();
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

                        ContactFriendsEntity contract = new ContactFriendsEntity();
                        contract.setId(MyApplication.getInstance().UserInfo.getUserId());
                        contract.setAvatar(MyApplication.getInstance().UserInfo.getAvatar());
                        contract.setCreatedTime(MyApplication.getInstance().UserInfo.getCreatedTime());
                        contract.setImNumber(MyApplication.getInstance().UserInfo.getAccountNumber());
                        contract.setPhoneNumber(MyApplication.getInstance().UserInfo.getPhoneNumber());
                        contract.setThermalSignatrue(MyApplication.getInstance().UserInfo.getThermalSignatrue());
                        contract.setUsername(MyApplication.getInstance().UserInfo.getUsername());
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
                        // 对list进行排序
                        Collections.sort(contactList, new IDCardActivity.PinyinComparator() {
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

package com.kaichaohulian.baocms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.MainActivity;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.BusinessActivity;
import com.kaichaohulian.baocms.activity.GroupChatActivity;
import com.kaichaohulian.baocms.activity.LabelActivity;
import com.kaichaohulian.baocms.activity.NewFriendsActivity;
import com.kaichaohulian.baocms.adapter.ConactAdapter;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseFragment;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
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


/**
 * 通讯录
 * Created by ljl on 2016/12/11.
 */
@SuppressLint("ValidFragment")
public class ContactListFragment extends BaseFragment {

    private ConactAdapter adapter;
    private List<ContactFriendsEntity> contactList;
    private RelativeLayout re_newfriends, GroupChat, Label, Shopping;
    private ListView listView;
    private boolean hidden;
    private Sidebar sidebar;

    private TextView tv_unread;
    private TextView tv_total;
    private LayoutInflater infalter;

    public ContactListFragment(MyApplication myApplication, Activity activity, Context context) {
        super(myApplication, activity, context);
    }

    @Override
    public void setContent() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_contactlist, null);
    }

    @Override
    public void initData() {
        contactList = new ArrayList<ContactFriendsEntity>();
    }

    @Override
    public void initView() {
        listView = getId(R.id.listHaoYou);
        infalter = LayoutInflater.from(getActivity());
        View headView = infalter.inflate(R.layout.item_contact_list_header, null);
        listView.addHeaderView(headView);
        View footerView = infalter.inflate(R.layout.item_contact_list_footer, null);
        listView.addFooterView(footerView);
        re_newfriends = (RelativeLayout) headView.findViewById(R.id.re_newfriends);
        GroupChat = (RelativeLayout) headView.findViewById(R.id.GroupChat);
        Label = (RelativeLayout) headView.findViewById(R.id.Label);
        Shopping = (RelativeLayout) headView.findViewById(R.id.Shopping);

        sidebar = getId(R.id.sidebar);
        sidebar.setListView(listView);
        tv_unread = (TextView) headView.findViewById(R.id.tv_unread);
        if (((MainActivity) getActivity()).unreadAddressLable.getVisibility() == View.VISIBLE) {
            tv_unread.setVisibility(View.VISIBLE);
            tv_unread.setText(((MainActivity) getActivity()).unreadAddressLable.getText());
        } else {
            tv_unread.setVisibility(View.GONE);
        }

        tv_total = (TextView) footerView.findViewById(R.id.tv_total);
        // 设置adapter
        adapter = new ConactAdapter(getActivity(), R.layout.item_contact_list, contactList);
        listView.setAdapter(adapter);

        // 获取设置contactlist
        getContactList();
    }

    @Override
    public void initEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position != 0 && position != contactList.size() + 1) {
                    ContactFriendsEntity user = contactList.get(position - 1);
                    Bundle Bundle = new Bundle();
                    Bundle.putString("userId", user.getPhoneNumber());
                    Bundle.putString("userNick", user.getUsername());
                    Bundle.putString("userAvatar", user.getAvatar());
                    Bundle.putString("imNumber", user.getImNumber());
//                    ActivityUtil.next(getActivity(), ChatActivity.class, Bundle);

                    CCPAppManager.startChattingAction(ContactListFragment.this.getActivity()
                            , user.getPhoneNumber(), user.getUsername(), true);
                }

            }
        });

        re_newfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), NewFriendsActivity.class);
            }
        });
        GroupChat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), GroupChatActivity.class);
            }
        });
        Label.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), LabelActivity.class);
            }
        });
        Shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), BusinessActivity.class);
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        this.hidden = hidden;
        if (!hidden) {
            refresh();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!hidden) {
            refresh();
        }
    }

    // 刷新ui
    public void refresh() {
        try {
            // 可能会在子线程中调到这方法
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    getContactList();
                    adapter.notifyDataSetChanged();
                    tv_total.setText(String.valueOf(contactList.size()) + "位联系人");
                    if (((MainActivity) getActivity()).unreadAddressLable.getVisibility() == View.VISIBLE) {
                        tv_unread.setVisibility(View.VISIBLE);
                        tv_unread.setText(((MainActivity) getActivity()).unreadAddressLable.getText());

                    } else {
                        tv_unread.setVisibility(View.GONE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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
                                contract.setHeader(ChineseToEnglish.getInstance().getSelling(contract.getUsername()).trim().substring(0,1));
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
                        tv_total.setText(String.valueOf(contactList.size()) + "位联系人");
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

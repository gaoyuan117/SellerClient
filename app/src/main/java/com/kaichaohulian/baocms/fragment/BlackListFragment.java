package com.kaichaohulian.baocms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.ConactAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseFragment;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.HanziToPinyin;
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
public class BlackListFragment extends BaseFragment {

    private ConactAdapter adapter;
    private List<ContactFriendsEntity> contactList;
    private ListView listView;
    private boolean hidden;
    private Sidebar sidebar;

    public static final String FLAG = "FLAG";
    public static final int FLAG_CIRCLE_REJECT = 1;
    public static final int FLAG_CIRCLE_BLACKLIST = 2;
    public static final int FLAG_BLACKLIST = 3;

    public BlackListFragment(MyApplication myApplication, Activity activity, Context context) {
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

        sidebar = getId(R.id.sidebar);
        sidebar.setListView(listView);

        // 设置adapter
        adapter = new ConactAdapter(getActivity(), R.layout.item_contact_list, contactList);
        listView.setAdapter(adapter);

        int flag = getActivity().getIntent().getIntExtra(FLAG, -1);
        switch (flag) {
            case FLAG_BLACKLIST:
                getContactList(FLAG_BLACKLIST);
                break;
            case FLAG_CIRCLE_REJECT:
                getContactList(FLAG_CIRCLE_REJECT);
                break;
            case FLAG_CIRCLE_BLACKLIST:
                getContactList(FLAG_CIRCLE_BLACKLIST);
                break;
        }
    }

    @Override
    public void initEvent() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position != 0 && position != contactList.size() + 1) {
                }

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

    public void refresh() {
        try {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    getContactList(FLAG_BLACKLIST);
                    adapter.notifyDataSetChanged();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getContactList(int flagBlacklist) {
        RequestParams params = new RequestParams();
        params.put("uId", MyApplication.getInstance().UserInfo.getUserId());
        params.put("flag", "" + flagBlacklist);
        HttpUtil.post(Url.blacklist, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取黑名单好友：", response.toString());
                    if (response.getInt("code") == 0) {
                        contactList.clear();
                        JSONArray JSONArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < JSONArray.length(); i++) {
                            ContactFriendsEntity ContactFriendsEntity = new ContactFriendsEntity();
                            JSONObject jsonObject = JSONArray.getJSONObject(i);
                            ContactFriendsEntity.setId(jsonObject.getInt("id"));
                            ContactFriendsEntity.setAvatar(jsonObject.getString("cuImg"));
                            ContactFriendsEntity.setUsername(jsonObject.getString("cuname"));
                            contactList.add(ContactFriendsEntity);
                        }
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
            if (TextUtils.isEmpty(py1) && TextUtils.isEmpty(py2))
                return 0;
            if (TextUtils.isEmpty(py1))
                return -1;
            if (TextUtils.isEmpty(py2))
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

    }
}

package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.RecentlyContactAdapter;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.ecdemo.storage.ConversationSqlManager;
import com.kaichaohulian.baocms.entity.RecentlyContact;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TransferSelectContactActivity extends BaseActivity {
    private RecentlyContactAdapter adapter;
    private List<RecentlyContact> contactList;
    private ListView listView;
    private LayoutInflater infalter;
    //    private TextView tv_total;
    private LinearLayout search_layout;
    private LinearLayout open_the_list;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_select_contact_action);
    }

    @Override
    public void initData() {
        contactList = new ArrayList<>();
    }

    @Override
    public void initView() {
        setCenterTitle("转账");
        listView = getId(R.id.recentlist);
        open_the_list = getId(R.id.open_the_list);
        search_layout = getId(R.id.search_layout);
        infalter = LayoutInflater.from(getActivity());
        View footerView = infalter.inflate(R.layout.item_contact_list_footer, null);
        listView.addFooterView(footerView);
//        tv_total = (TextView) footerView.findViewById(R.id.tv_total);
        // 设置adapter
        adapter = new RecentlyContactAdapter(getActivity(), R.layout.item_shopping_list, contactList);
        listView.setAdapter(adapter);
        search_layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), MyFriendsSearchActivity.class);
            }

        });
        open_the_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TransferSelectContactActivity.this, AddressListActivity.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecentlyContact contact = (RecentlyContact) adapterView.getAdapter().getItem(i);
                getUserId(contact.getSessionId(), contact.getLogo(), "" + contact.getSessionId(), "" + contact.getShopName());
            }

        });
        // 获取设置shoppinglist
//        getShoppingList();
    }

    public void getUserId(String phone, final String logo, final String sessionId, final String shopName) {
        RequestParams params = new RequestParams();
        params.put("phoneNumber", phone);
        HttpUtil.post(Url.dependPhoneGetUserInfo, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
                        int id = response.getInt("id");

                        Intent intent = new Intent(TransferSelectContactActivity.this, WithDrawToFriendActivity.class);
                        intent.putExtra("contact_logo", logo);
                        intent.putExtra("mRecipients", sessionId + "");
                        intent.putExtra("contact_user", shopName);
                        intent.putExtra("contact_id", id + "");
                        startActivity(intent);

                        setResult(RESULT_OK, intent);
                        finish();

                    }
                } catch (Exception e) {
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
                ToastUtil.showMessage("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }

    @Override
    public void initEvent() {
        contactList.clear();
        final ArrayList<String> arrayList = ConversationSqlManager.getInstance().qureyAllSession();
        for (String session : arrayList) {
            String info = SharedPrefsUtil.getValue(this, session, null);
            String avaterPath = "";
            String nick = "";

            if (info != null) {
                String[] data = info.split("-x-");
                Log.e("led---", info);
                if (data.length > 1) {
                    avaterPath = data[0];
                    nick = data[1];
                }
            }
            RecentlyContact contact = new RecentlyContact();
            contact.setSessionId(session);
            contact.setLogo(avaterPath);
            contact.setShopName(nick);
            contact.setHeader("最近联系人");
            contactList.add(contact);
        }
        adapter.notifyDataSetChanged();
//        tv_total.setText(String.valueOf(contactList.size()) + "位联系人");
    }

    public void back(View view) {
        finish();
    }

}

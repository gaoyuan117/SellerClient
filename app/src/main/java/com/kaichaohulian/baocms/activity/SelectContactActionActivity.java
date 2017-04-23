package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.RecentlyContactAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.storage.ConversationSqlManager;
import com.kaichaohulian.baocms.entity.RecentlyContact;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;

import java.util.ArrayList;
import java.util.List;

public class SelectContactActionActivity extends BaseActivity {
    private RecentlyContactAdapter adapter;
    private List<RecentlyContact> contactList;
    private ListView listView;
    private LayoutInflater infalter;
    private TextView tv_total;
    private boolean mIsSelectFromPck;
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
//        GroupChatActivity.IS_SELECT_GROUPID
        mIsSelectFromPck = getIntent().getBooleanExtra(GroupChatActivity.IS_SELECT_GROUPID, false);

        setCenterTitle("发红包");
        listView = getId(R.id.recentlist);
        open_the_list = getId(R.id.open_the_list);
        infalter = LayoutInflater.from(getActivity());
        View footerView = infalter.inflate(R.layout.item_contact_list_footer, null);
        listView.addFooterView(footerView);
        tv_total = (TextView) footerView.findViewById(R.id.tv_total);
        // 设置adapter
        adapter = new RecentlyContactAdapter(getActivity(), R.layout.item_shopping_list, contactList);
        listView.setAdapter(adapter);

        open_the_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectContactActionActivity.this, AddressListActivity.class);
                intent.putExtra("isFromRedpck", true);
                startActivityForResult(intent, 100);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RecentlyContact contact = (RecentlyContact) adapterView.getAdapter().getItem(i);
                if (mIsSelectFromPck) {
                    String sessionId = contact.getSessionId();
                    Intent redpckResult = new Intent();
                    redpckResult.putExtra("sessionid", sessionId);
                    setResult(RedBagActivity.REQ_CONTRACT_ID, redpckResult);
                    finish();
                    return;
                }

                Intent intent = new Intent();
                intent.putExtra("mRecipients", contact.getSessionId());
                setResult(RESULT_OK, intent);
                finish();
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
        tv_total.setText(String.valueOf(contactList.size()) + "位联系人");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            try {
                String sessionId = data.getStringExtra("sessionid");
                Intent redpckResult = new Intent();
                redpckResult.putExtra("sessionid", sessionId);
                setResult(RedBagActivity.REQ_CONTRACT_ID, redpckResult);
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                finish();
            }
        }
    }
}

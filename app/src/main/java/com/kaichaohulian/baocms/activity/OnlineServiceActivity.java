package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.OnlineServiceListAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingFragment;
import com.kaichaohulian.baocms.entity.OnlineServiceEntity;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnlineServiceActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    @BindView(R.id.lv_onlineservice)
    ListView lvOnlineservice;
    private List<OnlineServiceEntity> data;
    private OnlineServiceListAdapter adapter;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_online_service);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        data = new ArrayList<OnlineServiceEntity>();
        adapter = new OnlineServiceListAdapter(getActivity(), data);
        adapter.setLayoutIds(R.layout.item_onlineservicelist);
        lvOnlineservice.setAdapter(adapter);
        RetrofitClient.getInstance().createApi().getOnlineServicelist()
                .compose(RxUtils.<HttpArray<OnlineServiceEntity>>io_main())
                .subscribe(new BaseListObserver<OnlineServiceEntity>(getActivity(), "加载客服列表中") {
                    @Override
                    protected void onHandleSuccess(List<OnlineServiceEntity> list) {
                        data.clear();
                        data.addAll(list);
                        adapter.notifyDataSetChanged();
                        Log.e(TAG, "onHandleSuccess: " + list.toString());
                    }
                });

    }


    @Override
    public void initView() {
        setCenterTitle("在线客服");
    }

    @Override
    public void initEvent() {
        lvOnlineservice.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), ChattingActivity.class);
        intent.putExtra(ChattingFragment.RECIPIENTS, data.get(position).getPhoneNumber());
        intent.putExtra(ChattingFragment.CONTACT_USER, data.get(position).getName());
        intent.putExtra("user_id", data.get(position).getId());
        intent.putExtra(ChattingFragment.CUSTOMER_SERVICE, false);
        startActivity(intent);
    }
}

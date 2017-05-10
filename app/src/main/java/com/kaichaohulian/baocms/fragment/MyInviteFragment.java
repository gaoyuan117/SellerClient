package com.kaichaohulian.baocms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.MyInviteAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseFragment;
import com.kaichaohulian.baocms.entity.MyInviteBean;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.view.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by gaoyuan on 2017/5/9.
 */

@SuppressLint("ValidFragment")
public class MyInviteFragment extends BaseFragment {

    @BindView(R.id.lv_invitationMg)
    RecyclerView mRecyclerView;

    private List<MyInviteBean> mList;
    private MyInviteAdapter mAdapter;
    private
    Unbinder unbinder;

    public MyInviteFragment(MyApplication myApplication, Activity activity, Context context) {
        super(myApplication, activity, context);
    }


    @Override
    public void setContent() {
        mView = View.inflate(getActivity(), R.layout.fragment_invite, null);
        unbinder = ButterKnife.bind(this, mView);
    }

    @Override
    public void initData() {
        loadMyInvite(1);
        mList = new ArrayList<>();
        mAdapter = new MyInviteAdapter(R.layout.item_discover_my_invite, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(
                getActivity(), LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(getActivity(), R.color.bg_color_gray)));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    private void loadMyInvite(int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", MyApplication.getInstance().UserInfo.getUserId());
        map.put("page", page);
        RetrofitClient.getInstance().createApi().getMyDiscoverInvite(map)
                .compose(RxUtils.<HttpArray<MyInviteBean>>io_main())
                .subscribe(new BaseListObserver<MyInviteBean>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(List<MyInviteBean> list) {
                        if (list == null) {
                            return;
                        }
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

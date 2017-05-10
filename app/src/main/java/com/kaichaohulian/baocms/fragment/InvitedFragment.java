package com.kaichaohulian.baocms.fragment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.InvitedAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseFragment;
import com.kaichaohulian.baocms.entity.InvitedBean;
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
 *
 */

public class InvitedFragment extends BaseFragment {


    @BindView(R.id.lv_invitationMg)
    RecyclerView mRecyclerView;

    private InvitedAdapter mAdapter;
    private List<InvitedBean> mList;
    Unbinder unbinder;

    public InvitedFragment(MyApplication myApplication, Activity activity, Context context) {
        super(myApplication, activity, context);
    }

    @Override
    public void setContent() {
        mView = View.inflate(getActivity(), R.layout.fragment_invite, null);
        unbinder = ButterKnife.bind(this, mView);

    }

    @Override
    public void initData() {
        loadInvited(1);

        mList = new ArrayList<>();
        mAdapter = new InvitedAdapter(R.layout.item_discover_invite, mList);
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

    private void loadInvited(int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", MyApplication.getInstance().UserInfo.getUserId());
        map.put("page", page);
        RetrofitClient.getInstance().createApi().getDiscoverInvited(map)
                .compose(RxUtils.<HttpArray<InvitedBean>>io_main())
                .subscribe(new BaseListObserver<InvitedBean>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(List<InvitedBean> list) {
                        if (list == null) {
                            return;
                        }
                        mList.clear();
                        mList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

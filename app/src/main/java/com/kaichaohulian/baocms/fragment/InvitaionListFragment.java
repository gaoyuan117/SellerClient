package com.kaichaohulian.baocms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.InvitationmgActivity;
import com.kaichaohulian.baocms.adapter.MyInviteListAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseFragment;
import com.kaichaohulian.baocms.entity.MyInviteEntity;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xzwzz on 2017/5/8.
 */

@SuppressLint("ValidFragment")
public class InvitaionListFragment extends BaseFragment {

    @BindView(R.id.lv_invitationMg)
    ListView lvInvitationMg;
    Unbinder unbinder;
    private ArrayList<MyInviteEntity> dataList = new ArrayList<>();
    private int index = 1;
    private MyInviteListAdapter adapter;
    private boolean IsLoad=false;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public InvitaionListFragment(MyApplication myApplication, Activity activity, Context context) {
        super(myApplication, activity, context);
    }

    @Override
    public void setContent() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_invitation, null);
        unbinder = ButterKnife.bind(this, mView);
    }

    @Override
    public void initData() {



    }

    public void LoadData(){
        switch (getArguments().getInt("type")){
            case InvitationmgActivity.MY_SEND:
                RetrofitClient.getInstance().createApi().getMyInvite(MyApplication.getInstance().UserInfo.getUserId(), index)
                        .compose(RxUtils.<HttpArray<MyInviteEntity>>io_main())
                        .subscribe(new BaseListObserver<MyInviteEntity>(getActivity(), "加载中...") {
                            @Override
                            protected void onHandleSuccess(List<MyInviteEntity> list) {
                                dataList.addAll(list);
                                adapter.notifyDataSetChanged();
                                if(list==null){
                                    Toast.makeText(context, "暂无邀请", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case InvitationmgActivity.MY_JOIN:
                RetrofitClient.getInstance().createApi().GetMyJoinInvite(MyApplication.getInstance().UserInfo.getUserId(), index)
                        .compose(RxUtils.<HttpArray<MyInviteEntity>>io_main())
                        .subscribe(new BaseListObserver<MyInviteEntity>(getActivity(), "加载中...") {
                            @Override
                            protected void onHandleSuccess(List<MyInviteEntity> list) {
                                dataList.addAll(list);
                                adapter.notifyDataSetChanged();
                                if(list==null){
                                    Toast.makeText(context, "暂无邀请", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }

    @Override
    public void initView() {
        adapter = new MyInviteListAdapter(getActivity(), dataList);
        adapter.setLayoutIds(R.layout.item_invitelist);
        lvInvitationMg.setAdapter(adapter);
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if(isVisible&&!IsLoad){
            LoadData();
            IsLoad=true;
        }else{

        }
    }
}
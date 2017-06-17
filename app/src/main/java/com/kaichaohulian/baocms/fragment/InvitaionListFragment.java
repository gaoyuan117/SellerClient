package com.kaichaohulian.baocms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.InvitationmgActivity;
import com.kaichaohulian.baocms.activity.InvitedetailActivity;
import com.kaichaohulian.baocms.adapter.MyInviteListAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseFragment;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.InviteOfFindEntity;
import com.kaichaohulian.baocms.entity.MyInviteEntity;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
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
    private boolean IsLoad = false;
    private boolean IsOwn;

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

    public void LoadData() {
        switch (getArguments().getInt("type")) {
            case InvitationmgActivity.MY_SEND:
                IsOwn = true;
                RetrofitClient.getInstance().createApi().getMyInvite(MyApplication.getInstance().UserInfo.getUserId(), index)
                        .compose(RxUtils.<HttpArray<MyInviteEntity>>io_main())
                        .subscribe(new BaseListObserver<MyInviteEntity>(getActivity(), "加载中...") {
                            @Override
                            protected void onHandleSuccess(List<MyInviteEntity> list) {
                                dataList.addAll(list);
                                adapter.notifyDataSetChanged();
                                if (list == null) {
                                    Toast.makeText(context, "暂无邀请", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
            case InvitationmgActivity.MY_JOIN:
                IsOwn = false;
                RetrofitClient.getInstance().createApi().GetMyJoinInvite(MyApplication.getInstance().UserInfo.getUserId(), index)
                        .compose(RxUtils.<HttpArray<MyInviteEntity>>io_main())
                        .subscribe(new BaseListObserver<MyInviteEntity>(getActivity(), "加载中...") {
                            @Override
                            protected void onHandleSuccess(List<MyInviteEntity> list) {
                                dataList.addAll(list);
                                adapter.notifyDataSetChanged();
                                if (list == null) {
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
        switch (getArguments().getInt("type")) {
            case InvitationmgActivity.MY_SEND:
            case InvitationmgActivity.MY_JOIN:
                adapter.setLayoutIds(R.layout.item_invitelist);
                break;

        }
        lvInvitationMg.setAdapter(adapter);
        lvInvitationMg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), InvitedetailActivity.class);
                intent.putExtra("inviteId", dataList.get(i).getInvite().getId());
                intent.putExtra("UserId", MyApplication.getInstance().UserInfo.getUserId());
                intent.putExtra("IsOwn", IsOwn);
                startActivity(intent);
            }
        });

        lvInvitationMg.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final String[] cities = {"删除",};
                //    设置一个下拉的列表选择项
                final String advertId = dataList.get(i).getInvite().getId() + "";

                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delInvite(advertId);
                    }
                });
                builder.show();
                return true;
            }
        });

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible && !IsLoad) {
            LoadData();
            IsLoad = true;
        }
    }

    /**
     * 删除邀请
     * @param inviteId
     */
    private void delInvite(String inviteId) {
        RetrofitClient.getInstance().createApi().delInvite(MyApplication.getInstance().UserInfo.getUserId(), inviteId)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        ToastUtil.showMessage("删除成功");
                        dataList.clear();
                        LoadData();
                    }
                });
    }
}

package com.kaichaohulian.baocms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.AdverActivity;
import com.kaichaohulian.baocms.activity.NearbyZyActivity;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseFragment;
import com.kaichaohulian.baocms.view.zxing.activity.CaptureActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by gaoyuan on 2017/5/4.
 */
@SuppressLint("ValidFragment")
public class DiscoverFragment extends BaseFragment {

    @BindView(R.id.rl_discover_gg)
    RelativeLayout rlGuanggao;
    @BindView(R.id.rl_discover_invite)
    RelativeLayout rlInvite;
    @BindView(R.id.rl_discover_scan)
    RelativeLayout rlScan;
    @BindView(R.id.tv_discover_chat_num)
    TextView tvChatNum;
    @BindView(R.id.rl_discover_fj)
    RelativeLayout rlFuJin;
    Unbinder unbinder;

    public DiscoverFragment(MyApplication myApplication, Activity activity, Context context) {
        super(myApplication, activity, context);
    }

    @Override
    public void setContent() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_discover, null);
        unbinder = ButterKnife.bind(this, mView);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @OnClick({R.id.rl_discover_gg, R.id.rl_discover_invite, R.id.rl_discover_scan, R.id.rl_discover_fj})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_discover_gg://广告信息
                startActivity(new Intent(getActivity(), AdverActivity.class));
                break;

            case R.id.rl_discover_invite://邀请信息

                break;

            case R.id.rl_discover_scan://扫一扫
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivity(intent);
                break;

            case R.id.rl_discover_fj://附近的人
                startActivity(new Intent(getActivity(), NearbyZyActivity.class));
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

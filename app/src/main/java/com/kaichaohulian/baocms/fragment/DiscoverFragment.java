package com.kaichaohulian.baocms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.NearService;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.UpdateLocationService;
import com.kaichaohulian.baocms.activity.AdverActivity;
import com.kaichaohulian.baocms.activity.InvitationmgActivity;
import com.kaichaohulian.baocms.activity.NearbyZyActivity;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseFragment;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.NewInfoBean;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
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
    @BindView(R.id.img_discover_adver_chat)
    ImageView chatAdver;
    @BindView(R.id.img_discover_invite_chat)
    ImageView chatInvite;
    @BindView(R.id.img_discover_near_chat)
    ImageView chatNear;
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
                Intent intent2 = new Intent(getActivity(), InvitationmgActivity.class);
                intent2.putExtra("type", "discover");
                startActivity(intent2);
                break;

            case R.id.rl_discover_scan://扫一扫
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivity(intent);
                break;

            case R.id.rl_discover_fj://附近的人
                startActivity(new Intent(getActivity(), NearbyZyActivity.class));
                getActivity().startService(new Intent(getActivity(), NearService.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        newInfo();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            newInfo();
        }
    }

    private void newInfo() {
        RetrofitClient.getInstance().createApi().newInfo(MyApplication.getInstance().UserInfo.getUserId())
                .compose(RxUtils.<HttpResult<NewInfoBean>>io_main())
                .subscribe(new BaseObjObserver<NewInfoBean>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(NewInfoBean newInfoBean) {
                        if (newInfoBean.getAdvertStatus() == 1) {
                            chatAdver.setVisibility(View.VISIBLE);
                        } else {
                            chatAdver.setVisibility(View.GONE);
                        }

                        if (newInfoBean.getInviteStatus() == 1) {
                            chatInvite.setVisibility(View.VISIBLE);
                        } else {
                            chatInvite.setVisibility(View.GONE);
                        }

                        if (newInfoBean.getNearStatus() == 1) {
                            chatNear.setVisibility(View.VISIBLE);
                        } else {
                            chatNear.setVisibility(View.GONE);
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

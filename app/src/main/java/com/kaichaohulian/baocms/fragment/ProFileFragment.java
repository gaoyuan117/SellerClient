package com.kaichaohulian.baocms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.UserInfoManager;
import com.kaichaohulian.baocms.activity.AdvertMgActivity;
import com.kaichaohulian.baocms.activity.AdvertmassActivity;
import com.kaichaohulian.baocms.activity.InvitationmgActivity;
import com.kaichaohulian.baocms.activity.MeAboutActivity;
import com.kaichaohulian.baocms.activity.MeSettingsActivity;
import com.kaichaohulian.baocms.activity.MyAlbumActivity;
import com.kaichaohulian.baocms.activity.OnlineServiceActivity;
import com.kaichaohulian.baocms.activity.PersonalActivity;
import com.kaichaohulian.baocms.activity.PocketActivity;
import com.kaichaohulian.baocms.activity.SendinvitationActivity;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseFragment;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 * Created by ljl on 2016/12/11.
 */
@SuppressLint("ValidFragment")
public class ProFileFragment extends BaseFragment {


    private TextView me_head_name;
    private TextView me_buyer_number;

    private ImageView im_QrCode;
    private ImageView me_head_icon;

    public ProFileFragment(MyApplication myApplication, Activity activity, Context context) {
        super(myApplication, activity, context);
    }

    @Override
    public void setContent() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.me_layout, null);
        ButterKnife.bind(this, mView);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPrefsUtil.putValue(getActivity(), MyApplication.getInstance().UserInfo.getPhoneNumber(), MyApplication.getInstance().UserInfo.getAvatar() + "-x-" + MyApplication.getInstance().UserInfo.getUsername());
        SharedPrefsUtil.putValue(getActivity(), MyApplication.getInstance().UserInfo.getUserId() + "", MyApplication.getInstance().UserInfo.getAvatar() + "-x-" + MyApplication.getInstance().UserInfo.getUsername());
    }

    @Override
    public void initView() {
        me_head_icon = getId(R.id.me_head_icon);
        me_buyer_number = getId(R.id.me_buyer_number);
        me_head_name = getId(R.id.me_head_name);
        im_QrCode = getId(R.id.im_QrCode);
        Glide.with(MyApplication.getInstance()).load(MyApplication.getInstance().UserInfo.getAvatar()).error(R.mipmap.default_useravatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(me_head_icon);
        me_head_name.setText(MyApplication.getInstance().UserInfo.getUsername());
        String userid = String.valueOf(MyApplication.getInstance().UserInfo.getUserId());

        if (!TextUtils.isEmpty(userid) && !"null".equals(userid)) {
            me_buyer_number.setText("ID:" + userid);
        } else {
            me_buyer_number.setText("暂未获取到账号");
        }

        Glide.with(MyApplication.getInstance()).load(Url.BASE_URL + MyApplication.getInstance().UserInfo.getQrCode()).diskCacheStrategy(DiskCacheStrategy.ALL).into(im_QrCode);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initView();
        }
    }

    @Override
    public void initEvent() {

    }


    @OnClick({R.id.me_name, R.id.me_relativelayout_MassAdvertising, R.id.me_relativelayout_Advertising_manager, R.id.me_relativelayout_SendInvitation, R.id.me_relativelayout_invitationManager, R.id.me_relativelayout_album, R.id.me_relativelayout_pocket, R.id.me_relativelayout_about, R.id.me_relativelayout_OnlineService, R.id.me_relativelayout_settings})
    public void onClick(View view) {
        switch (view.getId()) {
            //个人信息
            case R.id.me_name:
                ActivityUtil.next(getActivity(), PersonalActivity.class);
                break;
            //群发广告
            case R.id.me_relativelayout_MassAdvertising:
                ActivityUtil.next(getActivity(), AdvertmassActivity.class);
                break;
            //广告管理
            case R.id.me_relativelayout_Advertising_manager:
                ActivityUtil.next(getActivity(), AdvertMgActivity.class);
                break;
            //发送邀请
            case R.id.me_relativelayout_SendInvitation:
                ActivityUtil.next(getActivity(), SendinvitationActivity.class);
                break;
            //邀请管理
            case R.id.me_relativelayout_invitationManager:
                Intent intent = new Intent(getActivity(),InvitationmgActivity.class);
                intent.putExtra("type","my");
                startActivity(intent);
                break;
            //相册
            case R.id.me_relativelayout_album:
                ActivityUtil.next(getActivity(), MyAlbumActivity.class);
                break;
            //钱包
            case R.id.me_relativelayout_pocket:
                UserInfoManager.getInstance().updateUserCache(getActivity());
                ActivityUtil.next(getActivity(), PocketActivity.class);
                break;
            //关于
            case R.id.me_relativelayout_about:

                ActivityUtil.next(getActivity(), MeAboutActivity.class);
                break;
            //在线客服
            case R.id.me_relativelayout_OnlineService:
                ActivityUtil.next(getActivity(), OnlineServiceActivity.class);
                break;
            //设置
            case R.id.me_relativelayout_settings:
                ActivityUtil.next(getActivity(), MeSettingsActivity.class);
                break;
        }
    }
}

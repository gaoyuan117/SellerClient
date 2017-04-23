package com.kaichaohulian.baocms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.UserInfoManager;
import com.kaichaohulian.baocms.activity.CollectionListActivity;
import com.kaichaohulian.baocms.activity.MeSettingsActivity;
import com.kaichaohulian.baocms.activity.MyAlbumActivity;
import com.kaichaohulian.baocms.activity.PersonalActivity;
import com.kaichaohulian.baocms.activity.PocketActivity;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseFragment;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.melink.bqmmsdk.ui.store.EmojiPackageList;

/**
 * 我的
 * Created by ljl on 2016/12/11.
 */
@SuppressLint("ValidFragment")
public class ProFileFragment extends BaseFragment {

    private RelativeLayout mPersonal;
    private RelativeLayout mPocket;
    private RelativeLayout mSettings;
    private RelativeLayout mAlbum;
    private RelativeLayout mCollection;
    private RelativeLayout mFace;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPrefsUtil.putValue(getActivity(), MyApplication.getInstance().UserInfo.getPhoneNumber(), MyApplication.getInstance().UserInfo.getAvatar() + "-x-" + MyApplication.getInstance().UserInfo.getUsername());
        SharedPrefsUtil.putValue(getActivity(), MyApplication.getInstance().UserInfo.getUserId() + "", MyApplication.getInstance().UserInfo.getAvatar() + "-x-" + MyApplication.getInstance().UserInfo.getUsername());
    }

    @Override
    public void initView() {
        mPersonal = getId(R.id.me_name);
        mPocket = getId(R.id.me_relativelayout_pocket);
        mSettings = getId(R.id.me_relativelayout_settings);
        mAlbum = getId(R.id.me_relativelayout_album);
        mCollection = getId(R.id.me_relativelayout_collection);
        mFace = getId(R.id.me_relativelayout_face);

        me_head_icon = getId(R.id.me_head_icon);
        me_buyer_number = getId(R.id.me_buyer_number);
        me_head_name = getId(R.id.me_head_name);
        im_QrCode = getId(R.id.im_QrCode);


        Glide.with(MyApplication.getInstance()).load(MyApplication.getInstance().UserInfo.getAvatar()).error(R.mipmap.default_useravatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(me_head_icon);
        me_head_name.setText(MyApplication.getInstance().UserInfo.getUsername());
//        me_buyer_number.setText(MyApplication.getInstance().UserInfo.getUsername()+MyApplication.getInstance().UserInfo.getPhoneNumber());
        String accountNumber = MyApplication.getInstance().UserInfo.getPhoneNumber();

        if (!TextUtils.isEmpty(accountNumber) && !"null".equals(accountNumber)) {
            me_buyer_number.setText(accountNumber);
        } else {
            me_buyer_number.setText("暂未获取到账号");
        }
        Glide.with(MyApplication.getInstance()).load("http://115.29.99.167:8081/SellerNet/" + MyApplication.getInstance().UserInfo.getQrCode()).diskCacheStrategy(DiskCacheStrategy.ALL).into(im_QrCode);
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
        mPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(getActivity(), PersonalActivity.class);
            }
        });

        mPocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoManager.getInstance().updateUserCache(getActivity());
                ActivityUtil.next(getActivity(), PocketActivity.class);
            }
        });

        mSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.next(getActivity(), MeSettingsActivity.class);
            }
        });
        mAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), MyAlbumActivity.class);
            }
        });
        mCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), CollectionListActivity.class);
            }
        });
        mFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtil.next(getActivity(), SetPasswordActivity.class);
                startActivity(new Intent(getActivity(), EmojiPackageList.class));
            }
        });
    }

}

package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.utils.StringUtils;

public class SayHelloToNearPeopleActivity extends BaseActivity implements OnClickListener {

    private UserInfo UserInfo;

    @Override
    public void setContent() {
        setContentView(R.layout.nearby_sayhello_activity);
    }

    @Override
    public void initData() {
        UserInfo = (com.kaichaohulian.baocms.entity.UserInfo) getIntent().getSerializableExtra("data");
        if (UserInfo != null) {
            initView();
        } else
            finish();
    }

    @Override
    public void initView() {
        if (MyApplication.getInstance().UserInfo != null) {
            setCenterTitle(UserInfo.getUsername());
            TextView tv_name = getId(R.id.tv_name);
            TextView tv_region = getId(R.id.tv_region);
            TextView tv_hangye = getId(R.id.tv_hangye );
            TextView tv_fxid = getId(R.id.tv_fxid);
            ImageView iv_sex = getId(R.id.iv_sex);
            ImageView iv_avatar = getId(R.id.iv_avatar);
            Button btn_sayhello = getId(R.id.btn_sayhello);
            Button btn_tousu = getId(R.id.btn_tousu);
            if (!StringUtils.isEmpty(UserInfo.getUsername())) {
                tv_name.setText(UserInfo.getUsername());
            } else {
                tv_name.setText("未命名");
            }
            if (!StringUtils.isEmpty(UserInfo.getUserEmail())) {
                tv_region.setText(UserInfo.getUserEmail());
            } else {
                tv_region.setText("暂无地区");
            }
            if (!StringUtils.isEmpty(UserInfo.getAccountNumber())&&!"null".equals(UserInfo.getAccountNumber())){
                tv_fxid.setText("帐号:" + UserInfo.getAccountNumber());
            } else {
                tv_fxid.setText("暂无帐号");
            }
            if (!StringUtils.isEmpty(UserInfo.getIndustry())) {
                tv_fxid.setText(UserInfo.getIndustry());
            } else {
                tv_fxid.setText("");
            }
//            if (!StringUtils.isEmpty(UserInfo.getThermalSignatrue()) || "null".equals(UserInfo.getThermalSignatrue())) {
//                tv_sign.setText(UserInfo.getThermalSignatrue());
//            } else {
//                tv_sign.setText("这个人很懒，什么都没留下");
//            }
            if (UserInfo.getSex().equals("1")) {
                iv_sex.setImageResource(R.mipmap.ic_sex_male);
            } else {
                iv_sex.setImageResource(R.mipmap.ic_sex_female);
            }
            Glide.with(getActivity()).load(UserInfo.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv_avatar);
            btn_sayhello.setOnClickListener(this);
            btn_tousu.setOnClickListener(this);

        }

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sayhello:
//                if(MyApplication.getInstance().UserInfo.getId() == UserInfo.getUserId()){
//                    showToastMsg("不能和自己聊天。。");
//                    return;
//                }
//                if (UserInfo.getIsfriend() == 1) {
////                    ActivityUtil.next(getActivity(), Chat.class, Bundle);
//                } else {
                    Bundle Bundle = new Bundle();
                    Bundle.putString("friendId", String.valueOf(UserInfo.getUserId()));
                    ActivityUtil.next(getActivity(), SayHiActivity.class, Bundle);
//                }
                break;
            case R.id.btn_tousu:
                  Bundle Bundle1 = new Bundle();
                Bundle1.putString("friendId", String.valueOf(UserInfo.getUserId()));
                Bundle1.putBoolean("tousu", true);
                  ActivityUtil.next(getActivity(), SayHiActivity.class, Bundle1);
              break;
        }
    }
}

package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.util.TitleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NearbyZyActivity extends BaseActivity {


    @BindView(R.id.img_nearby_avatar)
    ImageView imgNearbyAvatar;
    @BindView(R.id.ll_nearby_notify)
    LinearLayout llNearbyNotify;
    @BindView(R.id.bt_nearby_show)
    Button btNearbyShow;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_nearby_zy);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        new TitleUtils(this).setTitle("附近的人");
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }


    @OnClick({R.id.ll_nearby_notify, R.id.bt_nearby_show})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_nearby_notify:
                startActivity(new Intent(this, GreetActivity.class));
                break;
            case R.id.bt_nearby_show:
                startActivity(new Intent(this, NearbyListActivity.class));
                break;
        }
    }
}

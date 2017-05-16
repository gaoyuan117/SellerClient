package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.GreetBean;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.util.TitleUtils;

import java.util.List;
import java.util.Map;

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
    @BindView(R.id.tv_nearby_num)
    TextView tvChatNums;

    private GreetBean mGreetBean;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_nearby_zy);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        new TitleUtils(this).setTitle("附近的人");
        loadGreetList();
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
                if (mGreetBean == null) {
                    return;
                }
                Intent intent = new Intent(this, GreetActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_nearby_show:
                startActivity(new Intent(this, NearbyListActivity.class));
                break;
        }
    }

    private void loadGreetList() {
        map.put("id", MyApplication.getInstance().UserInfo.getUserId() + "");
        map.put("page", "1");
        RetrofitClient.getInstance().createApi().greetList(map)
                .compose(RxUtils.<HttpResult<GreetBean>>io_main())
                .subscribe(new BaseObjObserver<GreetBean>(this, false) {
                    @Override
                    protected void onHandleSuccess(GreetBean greetBean) {
                        if (greetBean.getRequestDTOs1() == null) {
                            llNearbyNotify.setVisibility(View.GONE);
                        } else {
                            llNearbyNotify.setVisibility(View.VISIBLE);
                            mGreetBean = greetBean;
                            tvChatNums.setText("你收到" + greetBean.getRembers() + "条新的打招呼");
                            Glide.with(NearbyZyActivity.this)
                                    .load(greetBean.getRequestDTOs1().get(0).getAvatar())
                                    .error(R.mipmap.default_useravatar)
                                    .into(imgNearbyAvatar);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

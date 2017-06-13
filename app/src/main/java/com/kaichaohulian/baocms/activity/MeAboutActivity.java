package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.AboutBean;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeAboutActivity extends BaseActivity {


    @BindView(R.id.tv_about_content)
    TextView tvAboutContent;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_me_about);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        getAbout();
    }

    @Override
    public void initView() {
        setCenterTitle("关于");
    }

    @Override
    public void initEvent() {

    }

    private void getAbout(){
        RetrofitClient.getInstance().createApi().getAbout()
                .compose(RxUtils.<HttpResult<AboutBean>>io_main())
                .subscribe(new BaseObjObserver<AboutBean>(this) {
                    @Override
                    protected void onHandleSuccess(AboutBean aboutBean) {
                        tvAboutContent.setText(aboutBean.getContent());

                    }
                });
    }
}

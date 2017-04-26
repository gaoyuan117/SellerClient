package com.kaichaohulian.baocms.activity;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

import butterknife.ButterKnife;

public class MeAboutActivity extends BaseActivity {


    @Override
    public void setContent() {
        setContentView(R.layout.activity_me_about);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("关于");
    }

    @Override
    public void initEvent() {

    }
}

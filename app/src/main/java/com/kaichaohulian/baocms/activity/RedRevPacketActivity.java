package com.kaichaohulian.baocms.activity;

import android.view.Gravity;
import android.view.ViewGroup;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

public class RedRevPacketActivity extends BaseActivity {

    @Override
    public void setContent() {
        setContentView(R.layout.activity_redpacket);
        getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);
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
}

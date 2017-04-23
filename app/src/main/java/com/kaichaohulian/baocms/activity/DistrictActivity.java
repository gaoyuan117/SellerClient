package com.kaichaohulian.baocms.activity;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.Province;

import java.util.List;

public class DistrictActivity extends BaseActivity {

    List<Province> data;


    @Override
    public void setContent() {
        setContentView(R.layout.districtlayout);

    }

    @Override
    public void initData() {

  //      data.addAll(JSON.parseArray(json, Province.class));

    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }
}

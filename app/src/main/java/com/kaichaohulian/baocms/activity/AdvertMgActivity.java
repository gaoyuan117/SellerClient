package com.kaichaohulian.baocms.activity;

import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.AdvertmasslistAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvertMgActivity extends BaseActivity {


    @BindView(R.id.lv_advertmanager)
    ListView lvAdvertmanager;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_advert_mg);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        AdvertmasslistAdapter adapter=new AdvertmasslistAdapter(this,null);
        adapter.setLayoutIds(R.layout.item_advertmasslist);
        lvAdvertmanager.setAdapter(adapter);
    }

    @Override
    public void initView() {
        setCenterTitle("广告管理");
    }

    @Override
    public void initEvent() {

    }


}

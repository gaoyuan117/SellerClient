package com.kaichaohulian.baocms.activity;

import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.AdvertmasslistAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvertmassActivity extends BaseActivity {


    @BindView(R.id.lv_advertmass)
    ListView lvAdvertmass;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_advertmass);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        AdvertmasslistAdapter adapter=new AdvertmasslistAdapter(this,null);
        adapter.setLayoutIds(R.layout.item_advertmasslist);
        lvAdvertmass.setAdapter(adapter);
    }

    @Override
    public void initView() {
        setCenterTitle("群发广告");

    }

    @Override
    public void initEvent() {

    }


}

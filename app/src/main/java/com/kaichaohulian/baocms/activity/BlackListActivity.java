package com.kaichaohulian.baocms.activity;

import android.view.View;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.fragment.BlackListFragment;

public class BlackListActivity extends BaseActivity {

    @Override
    public void setContent() {
        setContentView(R.layout.activity_blacklist);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        TextView titleTV = (TextView) findViewById(R.id.center_title_tv);
        titleTV.setText("黑名单");

        BlackListFragment fragment = new BlackListFragment(MyApplication.getInstance(), getActivity(), getActivity());
        getSupportFragmentManager().beginTransaction().replace(R.id.ll_blacklist_container, fragment).commitAllowingStateLoss();
    }

    @Override
    public void initEvent() {

    }

}

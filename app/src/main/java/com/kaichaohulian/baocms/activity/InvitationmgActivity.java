package com.kaichaohulian.baocms.activity;

import android.app.Application;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.InvitationMgFragmentAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.fragment.InvitaionListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvitationmgActivity extends BaseActivity {


    @BindView(R.id.tab_invationMg)
    TabLayout tabInvationMg;
    @BindView(R.id.vg_invationMg)
    ViewPager vgInvationMg;
    private ArrayList<String> titles;
    private ArrayList<Fragment> fragments;
    private InvitationMgFragmentAdapter adapter;
    @Override
    public void setContent() {
        setContentView(R.layout.activity_invitationmg);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        setCenterTitle("邀请管理");
    }

    @Override
    public void initEvent() {
        titles=new ArrayList<>();
        fragments=new ArrayList<>();
        fragments.add(new InvitaionListFragment((MyApplication)getApplication(),InvitationmgActivity.this,InvitationmgActivity.this));
        fragments.add(new InvitaionListFragment((MyApplication)getApplication(),InvitationmgActivity.this,InvitationmgActivity.this));
        titles.add("我发起的");
        titles.add("我参与的");
        adapter=new InvitationMgFragmentAdapter(getSupportFragmentManager(),fragments,titles);
        vgInvationMg.setAdapter(adapter);
        tabInvationMg.setTabMode(TabLayout.MODE_FIXED);
        tabInvationMg.setupWithViewPager(vgInvationMg);

    }

}

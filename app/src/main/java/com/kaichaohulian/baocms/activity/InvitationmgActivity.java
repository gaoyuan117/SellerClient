package com.kaichaohulian.baocms.activity;

import android.app.Application;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

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

    public static final int MY_SEND=0,MY_JOIN=1;
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
        Bundle bundle=new Bundle();
        Bundle bundle1=new Bundle();

        InvitaionListFragment mysendInvite=new InvitaionListFragment((MyApplication)getApplication(),InvitationmgActivity.this,InvitationmgActivity.this);
        bundle.putInt("type",MY_SEND);
        mysendInvite.setArguments(bundle);


        InvitaionListFragment myjoinInvite=new InvitaionListFragment((MyApplication)getApplication(),InvitationmgActivity.this,InvitationmgActivity.this);
        bundle1.putInt("type",MY_JOIN);
        myjoinInvite.setArguments(bundle1);


        fragments.add(mysendInvite);
        fragments.add(myjoinInvite);

        titles.add("我发起的");
        titles.add("我参与的");

        vgInvationMg.setAdapter(new InvitationMgFragmentAdapter(getSupportFragmentManager(),fragments,titles));
        tabInvationMg.setTabMode(TabLayout.MODE_FIXED);
        tabInvationMg.setupWithViewPager(vgInvationMg);

    }

}

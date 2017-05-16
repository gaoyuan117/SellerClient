package com.kaichaohulian.baocms.activity;

import android.app.Application;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.InvitationMgFragmentAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.fragment.InvitaionListFragment;
import com.kaichaohulian.baocms.fragment.InvitedFragment;
import com.kaichaohulian.baocms.fragment.MyInviteFragment;

import java.lang.reflect.Field;
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
    public static final int MY_SEND = 0, MY_JOIN = 1;

    private String type;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_invitationmg);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void initView() {
        if (type != null && type.equals("discover")) {
            setCenterTitle("邀请信息");
        } else {
            setCenterTitle("邀请管理");
        }
    }

    @Override
    public void initEvent() {

        titles = new ArrayList<>();
        fragments = new ArrayList<>();
        Bundle bundle = new Bundle();
        Bundle bundle1 = new Bundle();




        if (type != null && type.equals("discover")) {

            fragments.add(new MyInviteFragment((MyApplication) getApplication(), InvitationmgActivity.this, InvitationmgActivity.this));
            fragments.add(new InvitedFragment((MyApplication) getApplication(), InvitationmgActivity.this, InvitationmgActivity.this));
            titles.add("我邀请的");
            titles.add("邀请我的");
        } else {
            InvitaionListFragment mysendInvite = new InvitaionListFragment((MyApplication) getApplication(), InvitationmgActivity.this, InvitationmgActivity.this);
            bundle.putInt("type", MY_SEND);
            mysendInvite.setArguments(bundle);

            InvitaionListFragment myjoinInvite = new InvitaionListFragment((MyApplication) getApplication(), InvitationmgActivity.this, InvitationmgActivity.this);
            bundle1.putInt("type", MY_JOIN);
            myjoinInvite.setArguments(bundle1);
            fragments.add(mysendInvite);
            fragments.add(myjoinInvite);

            titles.add("我发起的");
            titles.add("我参与的");
        }


        vgInvationMg.setAdapter(new InvitationMgFragmentAdapter(getSupportFragmentManager(), fragments, titles));
        tabInvationMg.setTabMode(TabLayout.MODE_FIXED);
        tabInvationMg.setupWithViewPager(vgInvationMg);

        tabInvationMg.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabInvationMg, 50, 50);
            }
        });

    }

    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());
        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }
}

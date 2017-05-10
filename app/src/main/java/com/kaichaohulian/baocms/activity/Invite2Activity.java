package com.kaichaohulian.baocms.activity;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.TabViewPagerAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.fragment.InvitedFragment;
import com.kaichaohulian.baocms.fragment.MyInviteFragment;
import com.kaichaohulian.baocms.util.TitleUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Invite2Activity extends BaseActivity {


//    @BindView(R.id.tab_discover_invite)
//    TabLayout mTabLayout;
//    @BindView(R.id.vp_discover_invite)
//    ViewPager mViewPager;
    private TabViewPagerAdapter viewPagerAdapter;
    private List<String> mList;
    private List<Fragment> fragmentList;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_invite2);
//        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
//        new TitleUtils(this).setTitle("邀请信息");
//
//        mList = new ArrayList<>();
//        fragmentList = new ArrayList<>();
//
//        mList.add("我邀请的");
//        mList.add("邀请我的");
//
//        fragmentList.add(new MyInviteFragment());
//        fragmentList.add(new InvitedFragment());
//        viewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager(), fragmentList, mList);
//        mViewPager.setAdapter(viewPagerAdapter);
//        mTabLayout.setupWithViewPager(mViewPager);
//
//        mTabLayout.setTabTextColors(getResources().getColor(R.color.zy_text_black), getResources().getColor(R.color.zy_orange));
//        mTabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                setIndicator(mTabLayout, 50, 50);
//            }
//        });


//        for (int i = 0; i < 10; i++) {
//            mList.add("1");
//        }
//        mAdapter = new DiscoverInviteAdapter(R.layout.item_discover_invite, mList);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.addItemDecoration(new RecyclerViewDivider(
//                this, LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(this, R.color.bg_color_gray)));
//        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

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

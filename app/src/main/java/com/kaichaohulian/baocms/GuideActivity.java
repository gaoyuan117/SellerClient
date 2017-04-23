package com.kaichaohulian.baocms;

import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.LoginActivity;
import com.kaichaohulian.baocms.adapter.ViewPagerAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.base.emoji.Symbols;
import com.kaichaohulian.baocms.utils.SPUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator
 * on 2016/4/12
 * 引导页
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private ViewPagerAdapter vpAdapter;
    private List<View> mViewLists;
    private ImageView[] points;
    //    private int[] pointIds = {R.id.iv1, R.id.iv2, R.id.iv3};
    private TextView mTvBegin;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_guide);
    }

    @Override
    public void initView() {
        SPUtils.put(getActivity(), "expireddate",new Date().getTime());

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        mViewLists = new ArrayList<>();
        mViewLists.add(inflater.inflate(R.layout.page_one, null));
        mViewLists.add(inflater.inflate(R.layout.page_two, null));
        mViewLists.add(inflater.inflate(R.layout.page_three, null));
        vpAdapter = new ViewPagerAdapter(mViewLists, getActivity());
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(vpAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void initData() {
        initPoints();
        // 第一次启动
        SPUtils.put(getActivity(), "isFirstIn", false);
        long millis = System.currentTimeMillis();
        SPUtils.put(getActivity(), "expireddate", millis);
    }

    @Override
    public void initEvent() {
//        mViewLists.get(2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean isLogin = (boolean) SPUtils.get(getActivity(), "isLogin", false);
//                if (isLogin) {
//                    startActivity(new Intent(GuideActivity.this, MainActivity.class));
//                } else
//                    startActivity(new Intent(GuideActivity.this, LoginActivity.class));
//                finish();
//            }
//        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    Handler mHandler = new Handler();

    @Override
    public void onPageSelected(int position) {
        if (position == 2) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("TRACE", "GUIDE FINISH");
                    boolean isLogin = (boolean) SPUtils.get(getActivity(), "isLogin", false);
                    if (isLogin) {
                        startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    } else
                        startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                    finish();
                }
            }, 2000);
        }
//        for (int i = 0; i < 3; i++) {
//            if (position == i) {
//                points[i].setImageResource(R.mipmap.start_luobo);
//            } else {
//                points[i].setImageResource(R.mipmap.start_yezi);
//            }
//        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void initPoints() {
//        points = new ImageView[mViewLists.size()];
//        for (int i = 0; i < mViewLists.size(); i++) {
//            points[i] = (ImageView) findViewById(pointIds[i]);
//        }
    }

}

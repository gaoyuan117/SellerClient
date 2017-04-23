package com.kaichaohulian.baocms.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.kaichaohulian.baocms.widget.radar.CustomViewPager;
import com.kaichaohulian.baocms.widget.radar.FixedSpeedScroller;
import com.kaichaohulian.baocms.widget.radar.Info;
import com.kaichaohulian.baocms.widget.radar.RadarViewGroup;
import com.kaichaohulian.baocms.widget.radar.ZoomOutPageTransformer;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AdvancedRadarActivity extends Activity implements ViewPager.OnPageChangeListener, RadarViewGroup.IRadarClickListener {

    private CustomViewPager viewPager;
    private RelativeLayout ryContainer;
    private RadarViewGroup radarViewGroup;
    private int mPosition;
    private FixedSpeedScroller scroller;
    private List<Info> mDatas = new ArrayList<>();
    private double latitude = 0, longitud = 0;
    private Handler handler = new Handler();
    private Runnable runnable;
    private ViewpagerAdapter mAdapter;
    private ImageView backIV;
    private ImageView selfIV;

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 1000); // 开始Timer
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable); //停止Timer
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar);
        initView();
        if (MyApplication.getInstance().UserInfo.getAvatar().contains("default")) {
            Glide.with(this).load(MyApplication.getInstance().UserInfo.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.default_useravatar).into(selfIV);
        }
        ryContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
        mAdapter = new ViewpagerAdapter();
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(10);
        viewPager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewPager.addOnPageChangeListener(this);
        setViewPagerSpeed(250);

        radarViewGroup.setiRadarClickListener(this);
        radarViewGroup.setDatas(mDatas);
//
        if (MyApplication.getInstance().BDLocation != null) {
            latitude = MyApplication.getInstance().BDLocation.getLatitude();
            longitud = MyApplication.getInstance().BDLocation.getLongitude();
        }

        runnable = new Runnable() {
            public void run() {
                getData();
                handler.postDelayed(this, 5000);
            }
        };
    }

    private void initView() {
        viewPager = (CustomViewPager) findViewById(R.id.vp);
        radarViewGroup = (RadarViewGroup) findViewById(R.id.radar);
        ryContainer = (RelativeLayout) findViewById(R.id.ry_container);
        backIV = (ImageView) findViewById(R.id.iv_advancedradar_back);
        selfIV = (ImageView) findViewById(R.id.iv_radar_selficon);
        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setViewPagerSpeed(int duration) {
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            scroller = new FixedSpeedScroller(AdvancedRadarActivity.this, new AccelerateInterpolator());
            field.set(viewPager, scroller);
            scroller.setmDuration(duration);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPosition = position;
    }

    @Override
    public void onPageSelected(int position) {
        radarViewGroup.setCurrentShowItem(position);
        if (viewPager.getSpeed() < -1800) {
            viewPager.setCurrentItem(mPosition + 2);
            viewPager.setSpeed(0);
        } else if (viewPager.getSpeed() > 1800 && mPosition > 0) {
            viewPager.setCurrentItem(mPosition - 1);
            viewPager.setSpeed(0);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRadarItemClick(int position) {
        viewPager.setCurrentItem(position);
    }

    class ViewpagerAdapter extends PagerAdapter {
        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final Info info = mDatas.get(position);
            View view = LayoutInflater.from(AdvancedRadarActivity.this).inflate(R.layout.viewpager_layout, null);
            ImageView ivPortrait = (ImageView) view.findViewById(R.id.iv);
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            TextView tvDistance = (TextView) view.findViewById(R.id.tv_distance);
            tvName.setText(info.getName());
            tvDistance.setText(info.getDistance());
            ImageLoader.getInstance().displayImage(info.getPortraitId(), ivPortrait);
            container.addView(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Info info = mDatas.get(position);
                    addFriendRequest(info.getId());
                }
            });
            return view;
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private void getData() {
        DBLog.e("雷达加好友");
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("longitud", longitud);
        params.put("latitude", latitude);
        params.put("type", "3");
        HttpUtil.post(Url.users_radarUsers, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("雷达加好友：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray dataObject = response.getJSONArray("dataObject");
                        for (int i = 0; i < dataObject.length(); i++) {
                            JSONObject jsonObject = dataObject.getJSONObject(i);

                            Info info = new Info();
                            info.setName(jsonObject.getString("username"));
                            info.setPortraitId(jsonObject.getString("avatar"));
                            info.setDistance(jsonObject.getString("distance"));
                            info.setId(jsonObject.getString("userId"));
//                            info.setDistance(Math.round((Math.random() * 10) * 100) / 100 + "");

                            boolean flag = true;
                            for (Info tempInfo : mDatas) {
                                if (tempInfo.getName().equals(info.getName())) {
                                    flag = false;
                                    break;
                                }
                            }
                            if (flag) {
                                mDatas.add(info);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
//                        Bundle Bundle = new Bundle();
//                        Bundle.putSerializable("data", UserInfo);
//                        ActivityUtil.next(RadarActivity.this, FriendDetailActivity.class, Bundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ToastUtil.showMessage("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        deleteUsers();
    }

    private void deleteUsers() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getId());
        HttpUtil.post(Url.deleteUsers, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ShowDialog.dissmiss();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ToastUtil.showMessage("请求服务器失败");
                ShowDialog.dissmiss();
            }
        });
    }

    public void addFriendRequest(String userid) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("friendId", userid);
        params.put("message", "请求添加您为好友");
        HttpUtil.get(Url.friends_apply, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        ToastUtil.showMessage("添加好友申请成功");
                    }
                    ToastUtil.showMessage(response.getString("errorDescription"));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ShowDialog.dissmiss();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ToastUtil.showMessage("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }

}

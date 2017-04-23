package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.utils.DBLog;

public class MyCurrentLocationActivity extends FragmentActivity {
    public MyLocationListenner myListener = new MyLocationListenner();
    LocationClient mLocClient = null;
    private TextView mTitleTV;
    private RelativeLayout mNoDisplayRL;
    private RelativeLayout mCurrAdrRL;
    private ImageView mNoDisplayIV;
    private ImageView mCurrAdrIV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(this);
        setContentView(R.layout.activity_current_location);
        initView();
        bindView();
        initData();
    }

    private void initView() {
        mTitleTV = (TextView) findViewById(R.id.center_title_tv);
        mNoDisplayRL = (RelativeLayout) findViewById(R.id.indisplay_address);
        mCurrAdrRL = (RelativeLayout) findViewById(R.id.current_address);
        mNoDisplayIV = (ImageView) findViewById(R.id.indisplay_img);
        mCurrAdrIV = (ImageView) findViewById(R.id.current_img);
    }

    private void bindView() {
        mNoDisplayRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                mNoDisplayIV.setVisibility(View.VISIBLE);
                Intent addrIntent = new Intent();
                addrIntent.putExtra("addr", "");
                setResult(300, addrIntent);
                finish();
            }
        });
        mCurrAdrRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                mCurrAdrRL.setVisibility(View.VISIBLE);
                Intent addrIntent = new Intent();
                addrIntent.putExtra("addr", mAddr);
                setResult(300, addrIntent);
                finish();
            }
        });
    }

    private String mAddr = "";

    public void reset() {
        mNoDisplayIV.setVisibility(View.INVISIBLE);
        mCurrAdrRL.setVisibility(View.INVISIBLE);
    }

    public void initData() {
        mTitleTV.setText("所在位置");
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取定位结果
            StringBuffer sb = new StringBuffer(256);
            if (location.getLocType() == BDLocation.TypeGpsLocation) {  // GPS定位结果
                sb.append(location.getAddrStr());
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {   // 网络定位结果
                sb.append(location.getAddrStr());    //获取地址信息
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {
                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            DBLog.e("位置信息", sb.toString());
            mAddr = location.getProvince() + location.getCity() + location.getStreet() + location.getStreetNumber();
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocClient.stop();
    }
}

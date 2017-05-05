package com.kaichaohulian.baocms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.utils.DBLog;

import java.util.HashMap;
import java.util.Map;

public class UpdateLocationService extends Service {
    private LocationClient locationClient;
    public BDLocation BDLocation;

    public UpdateLocationService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BaiduSdk();
        return START_STICKY;
    }

    public void BaiduSdk() {
        locationClient = new LocationClient(this);
        //设置定位条件
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);        //是否打开GPS
        option.setCoorType("bd09ll");       //设置返回值的坐标类型。
        option.setPriority(LocationClientOption.NetWorkFirst);  //设置定位优先级
        option.setScanSpan(120000);    //设置定时定位的时间间隔。单位毫秒
        option.setAddrType("all");
        locationClient.setLocOption(option);
        //注册位置监听器
        locationClient.registerLocationListener(new BDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) {
                    return;
                }
                updateLocation(location);
            }
        });
        locationClient.start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void updateLocation(BDLocation bdLocation){
        Map<String,String > map = new HashMap<>();
        map.put("id", MyApplication.getInstance().UserInfo.getId()+"");
        map.put("longitud",bdLocation.getLongitude()+"");
        map.put("latitude",bdLocation.getLatitude()+"");
        RetrofitClient.getInstance().createApi().updateLocation(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(this) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        Log.e("gy","定位成功");
                    }
                });
    }
}

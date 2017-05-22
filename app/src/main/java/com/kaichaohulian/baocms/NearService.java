package com.kaichaohulian.baocms;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.util.HashMap;
import java.util.Map;

public class NearService extends Service {
    public NearService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateLocation();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void updateLocation( ) {
        Map<String, String> map = new HashMap<>();
        if (MyApplication.getInstance().UserInfo == null) {
            return;
        }
        map.put("id", MyApplication.getInstance().UserInfo.getUserId() + "");
        map.put("longitud", MyApplication.lng + "");
        map.put("latitude", MyApplication.lat + "");
        RetrofitClient.getInstance().createApi().updateLocation(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(this) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        Log.e("gy", "定位成功");
                    }
                });
    }
}

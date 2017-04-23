package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.GlideCircleTransform;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class RadarActivity extends BaseActivity {

    private Button exit;
    private ImageView head;

    private double latitude = 0, longitud = 0;

    private boolean isLoding = false;
    private Handler handler = new Handler();
    private Runnable runnable;


    @Override
    protected void onResume() {
        super.onResume();
        isLoding = false;
        handler.postDelayed(runnable, 1000); // 开始Timer
    }

    @Override
    protected void onPause() {
        super.onPause();
        isLoding = true;
        handler.removeCallbacks(runnable); //停止Timer
    }

    @Override
    public void setContent() {
        setContentView(R.layout.radar_activity);
    }

    @Override
    public void initData() {
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

    @Override
    public void initView() {
        exit = getId(R.id.exit);
        head = getId(R.id.head);

        Glide.with(getActivity()).load(MyApplication.getInstance().UserInfo.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(getActivity())).into(head);
    }

    @Override
    public void initEvent() {
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        DBLog.e("雷达加好友：", isLoding);
        if (isLoding || latitude == 0) {
            return;
        }
        isLoding = true;
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
                    if (response.getInt("code") == 0 && isLoding) {
                        JSONObject jsonObject = response.getJSONObject("dataObject");
                        UserInfo UserInfo = new UserInfo();
                        UserInfo.setUserId(jsonObject.getInt("userId"));
//                        UserInfo.setCreatedTime(jsonObject.getString("createdTime"));
//                        UserInfo.setLocked(jsonObject.getBoolean("isLocked"));
//                        UserInfo.setLastModifiedTime(jsonObject.getString("lastModifiedTime"));
//                        UserInfo.setLastModifier(jsonObject.getString("lastModifier"));
                        UserInfo.setUsername(jsonObject.getString("username"));
                        UserInfo.setPassword(jsonObject.getString("password"));
                        UserInfo.setAccountNumber(jsonObject.getString("accountNumber"));
                        UserInfo.setQrCode(jsonObject.getString("qrCode"));
                        UserInfo.setDistrictId(jsonObject.getString("districtId"));
                        UserInfo.setSex(jsonObject.getString("sex"));
                        UserInfo.setThermalSignatrue(jsonObject.getString("thermalSignatrue"));
                        UserInfo.setPhoneNumber(jsonObject.getString("phoneNumber"));
                        UserInfo.setUserEmail(jsonObject.getString("userEmail"));
                        UserInfo.setBalance(jsonObject.getString("balance"));
                        UserInfo.setAvatar(jsonObject.getString("avatar"));
                        UserInfo.setBackAvatar(jsonObject.getString("backAvatar"));
//                        UserInfo.setLoginFailedCount(jsonObject.getInt("loginFailedCount"));
                        UserInfo.setIsfriend(jsonObject.getInt("isfriend"));
//                        UserInfo.setImages(jsonObject.getString("images"));
                        Bundle Bundle = new Bundle();
                        Bundle.putSerializable("data", UserInfo);
                        ActivityUtil.next(RadarActivity.this, FriendDetailActivity.class, Bundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    isLoding = false;
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                isLoding = false;
            }
        });
    }
}

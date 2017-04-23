package com.kaichaohulian.baocms.activity;

import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.NearByPeopleListEntity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.listener.ShakeListener;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class ShakeActivity extends BaseActivity {

    ShakeListener mShakeListener = null;
    Vibrator mVibrator;
    ImageView imgRen, imgBusiness;
    TextView tvRen, tvBusiness;
    private RelativeLayout mImgUp;
    private RelativeLayout mImgDn;

    private SlidingDrawer mDrawer;
    private Button mDrawerBtn;
    private PopupWindow popupWindow;
    private NearByPeopleListEntity entity;
    private boolean status = false;
    int which = 1;
    LinearLayout ln_people, ln_business;

    @Override
    public void setContent() {
        setContentView(R.layout.shake_activity);
    }

    @Override
    public void initData() {
        mShakeListener = new ShakeListener(getActivity());
    }

    @Override
    public void initView() {
        setCenterTitle("摇一摇");
        mVibrator = (Vibrator) getApplication().getSystemService(VIBRATOR_SERVICE);

        imgBusiness = getId(R.id.img_business);
        imgRen = getId(R.id.img_ren);
        tvBusiness = getId(R.id.tv_business);
        tvRen = getId(R.id.tv_ren);
        mImgUp = getId(R.id.shakeImgUp);
        mImgDn = getId(R.id.shakeImgDown);
        ln_business = getId(R.id.linear_business);
        ln_people = getId(R.id.linear_people);

        imgBusiness.setImageResource(R.mipmap.shake_ren_gray);
        tvBusiness.setTextColor(getResources().getColor(R.color.deep_gray));

//        mDrawer = getId(R.id.slidingDrawer1);
//        mDrawerBtn = getId(R.id.handle);
    }

    @Override
    public void initEvent() {

        mShakeListener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            public void onShake() {
                //Toast.makeText(getApplicationContext(), "抱歉，暂时没有找到在同一时刻摇一摇的人。\n再试一次吧！", Toast.LENGTH_SHORT).show();
                status = false;
                if (which == 1) {
                    getPeople();
                } else {
                    getStore();
                }
                startAnim();  //开始 摇一摇手掌动画
                mShakeListener.stop();
                startVibrato(); //开始 震动
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //Toast.makeText(getApplicationContext(), "抱歉，暂时没有找到\n在同一时刻摇一摇的人。\n再试一次吧！", 500).setGravity(Gravity.CENTER,0,0).show();
                        //mtoast.setGravity(Gravity.CENTER, 0, 0);
//                        DBLog.showToast("抱歉，暂时没有找到\n在同一时刻摇一摇的人。\n再试一次吧！", getApplicationContext());
                        mVibrator.cancel();
                        mShakeListener.start();
                    }
                }, 2000);
            }
        });
        ln_people.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which = 1;
                imgBusiness.setImageResource(R.mipmap.shake_ren_gray);
                tvBusiness.setTextColor(getResources().getColor(R.color.shake_tv_gray));

                imgRen.setImageResource(R.mipmap.ren);
                tvRen.setTextColor(getResources().getColor(R.color.shake_tv_green));
            }
        });
        ln_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                which = 2;
                imgRen.setImageResource(R.mipmap.shake_ren_gray);
                tvRen.setTextColor(getResources().getColor(R.color.shake_tv_gray));

                imgBusiness.setImageResource(R.mipmap.ren);
                tvBusiness.setTextColor(getResources().getColor(R.color.shake_tv_green));

            }
        });
    }

    public void startAnim() {   //定义摇一摇动画动画
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f);
        mytranslateanimup0.setDuration(1000);
        TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.5f);
        mytranslateanimup1.setDuration(1000);
        mytranslateanimup1.setStartOffset(1000);
        animup.addAnimation(mytranslateanimup0);
        animup.addAnimation(mytranslateanimup1);
        mImgUp.startAnimation(animup);

        AnimationSet animdn = new AnimationSet(true);
        TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, +0.5f);
        mytranslateanimdn0.setDuration(1000);
        TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.5f);
        mytranslateanimdn1.setDuration(1000);
        mytranslateanimdn1.setStartOffset(1000);
        animdn.addAnimation(mytranslateanimdn0);
        animdn.addAnimation(mytranslateanimdn1);
        mImgDn.startAnimation(animdn);
    }

    public void startVibrato() {
        MediaPlayer player;
        player = MediaPlayer.create(this, R.raw.awe);
        player.setLooping(false);
        player.start();
        //定义震动
        mVibrator.vibrate(new long[]{500, 200, 500, 200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
    }

    public void shake_activity_back(View v) {     //标题栏 返回按钮
        this.finish();
    }

    public void linshi(View v) {     //标题栏
        startAnim();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mShakeListener != null) {
            mShakeListener.stop();
        }
        deleteUsers();
    }

    @Override
    protected void onPause() {
        super.onPause();
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

    public void getPeople() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("latitude", mStartLatitude);
        params.put("longitud", mStartLontitude);
        params.put("type", 2);
        HttpUtil.post(Url.shakeOnePeople, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("附近的人", response.toString());
                    if (response.getInt("code") == 0) {
                        entity = new NearByPeopleListEntity();
                        JSONObject json = response.getJSONObject("dataObject");
                        if (MyApplication.getInstance().UserInfo.getUserId() == json.getInt("userId")) {
                            showToastMsg("暂未寻找到好友");
                            return;
                        }
                        entity.setUserId(json.getInt("userId") + "");
                        entity.setHeadIconURL(json.getString("avatar"));
                        entity.setMySign(json.getString("thermalSignatrue"));
                        entity.setName(json.getString("username"));
                        entity.setDistance(json.getString("distance"));
                        status = true;
                    } else {
                        status = false;
                    }
                    showPop(entity, status);
                } catch (Exception e) {
                    showToastMsg("暂未寻找到好友");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }

    public void getStore() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("latitude", mStartLatitude);
        params.put("longitud", mStartLontitude);
        params.put("type", 2);
        HttpUtil.post(Url.shakeOneBusiness, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("附近的商家", response.toString());
                    if (response.getInt("code") == 0) {
                        entity = new NearByPeopleListEntity();
                        JSONObject json = response.getJSONObject("dataObject");
                        int userId = MyApplication.getInstance().UserInfo.getUserId();
                        int userId1 = json.getInt("userId");
                        if (userId == userId1) {
                            showToastMsg("暂未寻找到附近商家");
                            return;
                        }
                        entity.setUserId(json.getInt("userId") + "");
                        entity.setHeadIconURL(json.getString("avatar"));
                        entity.setMySign(json.getString("thermalSignatrue"));
                        entity.setName(json.getString("username"));
                        entity.setDistance(json.getString("distance"));
                        status = true;
                    } else
                        status = false;
                    showPop(entity, status);
                } catch (Exception e) {
                    showToastMsg("暂未寻找到附近商家");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void showPop(final NearByPeopleListEntity entity, boolean status) {
        if (status) {
            final View popView = getLayoutInflater().inflate(R.layout.popwindow_shake, null);
            popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            RelativeLayout Relative = (RelativeLayout) popView.findViewById(R.id.Relative);
            ImageView imgIcon = (ImageView) popView.findViewById(R.id.nearby_people_list_head_icon);
            TextView tvName = (TextView) popView.findViewById(R.id.nearby_people_list_name);
            TextView tvDistanc = (TextView) popView.findViewById(R.id.nearby_people_list_distance);
            TextView tvSign = (TextView) popView.findViewById(R.id.nearby_people_list_sign);
            if (entity != null) {
                tvName.setText(entity.getName());
                tvDistanc.setText(entity.getDistance());
                String mySign = entity.getMySign();
                if (!TextUtils.isEmpty(mySign) && !"null".equals(mySign)) {
                    tvSign.setText(mySign);
                } else {
                    tvSign.setVisibility(View.INVISIBLE);
                }
                Glide.with(getActivity()).load(entity.getHeadIconURL()).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgIcon);
            }
            Relative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (entity != null) {
                        UserInfo UserInfo = new UserInfo();
                        UserInfo.setUserId(Integer.parseInt(entity.getUserId()));
                        UserInfo.setUsername(entity.getName());
                        UserInfo.setThermalSignatrue(entity.getMySign());
                        UserInfo.setAvatar(entity.getHeadIconURL());
                        UserInfo.setSex("1");
                        Bundle Bundle = new Bundle();
                        Bundle.putSerializable("data", UserInfo);
                        ActivityUtil.next(getActivity(), FriendDetailActivity.class, Bundle);
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        }
                    }
                }
            });
        } else {
            View popView = getLayoutInflater().inflate(R.layout.popwindow_shake, null);
            popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        }
        popupWindow.setOutsideTouchable(true);
        View root = LayoutInflater.from(ShakeActivity.this).inflate(R.layout.shake_activity, null);
        popupWindow.showAtLocation(root, Gravity.CENTER, 0, 0);
    }

    @Override
    public void onBackPressed() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return;
        } else {
            super.onBackPressed();
        }
    }

}

package com.kaichaohulian.baocms.activity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.entity.PickUpBottleBean;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

public class PickupBottleActivity extends Activity {
    private AnimationDrawable ad;
    private ImageView pick_spray1, pick_spray2, voice_msg, close;
    private RelativeLayout pick_up_layout;
    int hour, minute, sec;

    PickUpBottleBean bottle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pick_up_bottle);
        //初始化
        pick_spray1 = (ImageView) findViewById(R.id.pick_spray1);
        pick_spray2 = (ImageView) findViewById(R.id.pick_spray2);
        voice_msg = (ImageView) findViewById(R.id.bottle_picked_voice_msg);
        close = (ImageView) findViewById(R.id.bottle_picked_close);
        pick_up_layout = (RelativeLayout) findViewById(R.id.pick_up_layout);

        //获取系统时间
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        sec = c.get(Calendar.SECOND);
        if (hour >= 18 || hour <= 6) {
            pick_up_layout.setBackgroundResource(R.drawable.bottle_pick_bg_spotlight_night);
        } else {
            pick_up_layout.setBackgroundResource(R.drawable.bottle_pick_bg_spotlight_day);
        }
        close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        voice_msg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击查看消息/语音

            }
        });

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                pick_spray1.setVisibility(View.VISIBLE);
                ad.setOneShot(true);
                ad.start();
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                pick_spray1.setVisibility(View.GONE);
                pick_spray2.setVisibility(View.VISIBLE);
                ad.setOneShot(true);
                ad.start();
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                pick_spray1.setVisibility(View.GONE);
                pick_spray2.setVisibility(View.GONE);
                voice_msg.setVisibility(View.VISIBLE);
                doStartAnimation(R.anim.pick_up_scale);
                close.setVisibility(View.VISIBLE);
            }
        }, 3000);


    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        HttpUtil.post(Url.driftbottle_pickUpBottle, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Log.e("TRACE", "response : " + response);
                            if (response.getInt("code") == 0) {
                                JSONObject dataObject = response.getJSONObject("dataObject");
                                String content = dataObject.getString("content");
                                String creator = dataObject.getString("creator");
                                Intent intent = new Intent(PickupBottleActivity.this, DriftBottleRevActivity.class);
                                intent.putExtra("content", content);
                                intent.putExtra("creator", Long.parseLong(creator));
                                startActivity(intent);
                                finish();
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
                    public void onFailure(int statusCode, Header[] headers, String
                            responseString, Throwable throwable) {
                        DBLog.e("tag", statusCode + ":" + responseString);
                        ShowDialog.dissmiss();
                    }
                }
        );
    }

    private void doStartAnimation(int animId) {
        Animation animation = AnimationUtils.loadAnimation(this, animId);
        voice_msg.startAnimation(animation);
    }

    //播放语音动画
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        ad = (AnimationDrawable) getResources().getDrawable(R.drawable.pick_up_spray);
        if (pick_spray1 != null && pick_spray2 != null) {
            pick_spray1.setBackgroundDrawable(ad);
            pick_spray2.setBackgroundDrawable(ad);
        }

    }


}

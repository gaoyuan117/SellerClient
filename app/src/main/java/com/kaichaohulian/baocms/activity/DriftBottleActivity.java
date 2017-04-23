package com.kaichaohulian.baocms.activity;

import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;


public class DriftBottleActivity extends Activity {

    private PopupWindow popupWindow, pop1;
    private View view;
    private LayoutInflater inflater;
    RelativeLayout bottle_main_layout, bottle_bottom_layout, bottle_night_bottom_layout;
    private ImageView bottle_night_moon, bottle_night_floodlight, bottle_night_floodlight_1, bottle_night_floodlight_2;
    private EditText chuck_edit;
    private Button chuck_keyboard, chuck_speak, bottle_back;
    private ImageView bottle_img, bottle1, bottle2, bottle3, bottle_title;
    private TranslateAnimation ani0, ani1, ani2;
    ImageView chuck_empty2, chuck_empty1, chuck_spray;
    ImageView bottle_night_bottom1, bottle_night_bottom2, bottle_night_bottom3;
    int m = 2;
    int hour, minute, sec;
    AnimationDrawable ad, ad1, ad2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.drift_bottle);

        //初始化
        bottle_main_layout = (RelativeLayout) findViewById(R.id.bottle_main_layout);
        bottle_bottom_layout = (RelativeLayout) findViewById(R.id.bottle_bottom_layout);
        bottle_night_bottom_layout = (RelativeLayout) findViewById(R.id.bottle_night_bottom_layout);
        bottle_night_moon = (ImageView) findViewById(R.id.bottle_night_moon);
        bottle_night_floodlight = (ImageView) findViewById(R.id.bottle_night_floodlight);
        bottle_night_floodlight_1 = (ImageView) findViewById(R.id.bottle_night_floodlight_1);
        bottle_night_floodlight_2 = (ImageView) findViewById(R.id.bottle_night_floodlight_2);
        bottle_night_bottom1 = (ImageView) findViewById(R.id.bottle_night_bottom1);
        bottle_night_bottom2 = (ImageView) findViewById(R.id.bottle_night_bottom2);
        bottle_night_bottom3 = (ImageView) findViewById(R.id.bottle_night_bottom3);
        bottle_night_bottom1.setOnClickListener(listener);
        bottle_night_bottom2.setOnClickListener(listener);
        bottle_night_bottom3.setOnClickListener(listener);

        bottle_img = (ImageView) findViewById(R.id.bottle_img);
        bottle1 = (ImageView) findViewById(R.id.bottle_bottom1);
        bottle1.setOnClickListener(listener);
        bottle2 = (ImageView) findViewById(R.id.bottle_bottom2);
        bottle2.setOnClickListener(listener);
        bottle3 = (ImageView) findViewById(R.id.bottle_bottom3);
        bottle3.setOnClickListener(listener);
        bottle_title = (ImageView) findViewById(R.id.bottle_title);
//        layout = (RelativeLayout) findViewById(R.id.bottle_layout_title);
        bottle_back = (Button) findViewById(R.id.bottle_back);
        bottle_back.setOnClickListener(listener);
        bottle_title.setOnClickListener(listener);
        ani0 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.3f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -0.3f);
        ani1 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.3f, Animation.RELATIVE_TO_PARENT, 0.6f,
                Animation.RELATIVE_TO_SELF, -0.3f, Animation.RELATIVE_TO_SELF, 0.2f);
        ani2 = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.6f, Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.2f, Animation.RELATIVE_TO_SELF, 0.0f);
        ani0.setInterpolator(new AccelerateDecelerateInterpolator());
        ani0.setDuration(15000);
        ani0.setFillEnabled(true);
        ani0.setFillAfter(true);
        ani0.setAnimationListener(animationListener);
        ani1.setInterpolator(new AccelerateDecelerateInterpolator());
        ani1.setDuration(15000);
        ani1.setFillEnabled(true);
        ani1.setFillAfter(true);
        ani1.setAnimationListener(animationListener);
        ani2.setInterpolator(new AccelerateDecelerateInterpolator());
        ani2.setDuration(15000);
        ani2.setFillEnabled(true);
        ani2.setFillAfter(true);
        ani2.setAnimationListener(animationListener);
        bottle_img.startAnimation(ani0);
        //获取系统时间
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        sec = c.get(Calendar.SECOND);
        if (hour >= 18 || hour <= 6) {
            bottle_main_layout.setBackgroundResource(R.drawable.bottle_night_bg);
            bottle_img.setVisibility(View.GONE);
            bottle_bottom_layout.setVisibility(View.GONE);
            bottle_night_moon.setVisibility(View.VISIBLE);
            bottle_night_floodlight.setVisibility(View.VISIBLE);
            bottle_night_bottom_layout.setVisibility(View.VISIBLE);
            //探照灯
//			ad=(AnimationDrawable) getResources().getDrawable(R.anim.bottle_night_floodlight);
//			bottle_night_floodlight.setBackgroundDrawable(ad);
//			ad=(AnimationDrawable) bottle_night_floodlight.getBackground();
//			ad.setOneShot(false);
//			ad.start();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    ad.setOneShot(false);
                    ad.start();
                }
            }, 1000);
        } else {
            bottle_main_layout.setBackgroundResource(R.drawable.bottle_day_bg);
            bottle_img.setVisibility(View.VISIBLE);
            bottle_bottom_layout.setVisibility(View.VISIBLE);
            bottle_night_moon.setVisibility(View.GONE);
            bottle_night_floodlight.setVisibility(View.GONE);
            bottle_night_bottom_layout.setVisibility(View.GONE);
        }
    }

    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bottle_bottom1:
                    initChuckPop();
                    popupWindow.showAtLocation(findViewById(R.id.bottle_bottom1), Gravity.BOTTOM, 0, 0);
                    break;
                case R.id.bottle_bottom2:
                    Intent in = new Intent(DriftBottleActivity.this, PickupBottleActivity.class);
                    startActivity(in);
                    break;
                case R.id.bottle_bottom3:
                    Intent in3 = new Intent(DriftBottleActivity.this, MyBottleActivity.class);
                    startActivity(in3);
                    break;
                case R.id.bottle_night_bottom1:
                    initChuckPop();
                    popupWindow.showAtLocation(findViewById(R.id.bottle_bottom1), Gravity.BOTTOM, 0, 0);
                    break;
                case R.id.bottle_night_bottom2:
                    Intent in1 = new Intent(DriftBottleActivity.this, PickupBottleActivity.class);
                    startActivity(in1);
                    break;
                case R.id.bottle_night_bottom3:
                    Intent in2 = new Intent(DriftBottleActivity.this, MyBottleActivity.class);
                    startActivity(in2);
                    break;
                case R.id.bottle_back:
                    finish();
//				Driftbottle_setting driftbottle_setting=
                    break;
                case R.id.bottle_title:
                    Intent i = new Intent(DriftBottleActivity.this, DriftbottleSettingActivity.class);
                    startActivity(i);
                    break;
                case R.id.chuck_keyboard:
//                    if (v.isClickable()) {
//                        chuck_edit.setVisibility(View.VISIBLE);
//                        chuck_speak.setText("扔出去");
//                        chuck_keyboard.setBackgroundResource(R.drawable.chatting_setmode_voice_btn_normal);
//                    }
//                    if (m % 2 == 0) {
//                        chuck_keyboard.setBackgroundResource(R.drawable.chat_voice);
//                        InputMethodManager inputMethodManager = (InputMethodManager) chuck_edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//                        inputMethodManager.showSoftInput(chuck_edit, InputMethodManager.RESULT_SHOWN);
//                        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//                    } else if (m % 2 != 0) {
//                        chuck_keyboard.setBackgroundResource(R.drawable.chatting_setmode_keyboard_btn_normal);
//                        chuck_edit.setVisibility(View.GONE);
//                        chuck_speak.setText("按住说话");
//                        chuck_toast.setVisibility(View.VISIBLE);
//                    }
//                    m++;
                    break;
                case R.id.chuck_speak:
                    Intent in4 = new Intent(DriftBottleActivity.this, chuckAnimationActivity.class);
                    startActivity(in4);
                    popupWindow.dismiss();
                    if (chuck_edit != null) {
                        stillBottle(chuck_edit.getText().toString().trim());
                    }
                    break;
            }
        }
    };

    private void doStartAnimation(int animId) {
        Animation animation = AnimationUtils.loadAnimation(this, animId);
        chuck_empty1.startAnimation(animation);
        chuck_empty2.startAnimation(animation);
    }

    final AnimationListener animationListener = new AnimationListener() {

        @Override
        public void onAnimationEnd(Animation animation) {
            if (animation == ani0) {
                bottle_img.startAnimation(ani1);
            }
            if (animation == ani1) {
                bottle_img.startAnimation(ani2);
            }
            if (animation == ani2) {
                bottle_img.startAnimation(ani0);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }

        @Override
        public void onAnimationStart(Animation animation) {
        }
    };


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (bottle_night_floodlight != null) {
            ad = (AnimationDrawable) getResources().getDrawable(R.anim.bottle_night_floodlight);
            bottle_night_floodlight.setBackgroundDrawable(ad);
        } else if (bottle_night_floodlight_1 != null) {
            ad1 = (AnimationDrawable) getResources().getDrawable(R.anim.bottle_night_floodlight1);
            bottle_night_floodlight_1.setBackgroundDrawable(ad);
        } else if (bottle_night_floodlight_2 != null) {
            ad2 = (AnimationDrawable) getResources().getDrawable(R.anim.bottle_night_floodlight2);
            bottle_night_floodlight_2.setBackgroundDrawable(ad);
        }
    }

    //扔瓶子的窗口
    public void initChuckPop() {
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.chuck_pop, null);
        popupWindow = new PopupWindow(view, WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        ColorDrawable colorDrawable = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(colorDrawable);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        view.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.chuck_layout1).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (y < height) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                    if (y > height) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
                }
                return true;
            }
        });

        chuck_edit = (EditText) view.findViewById(R.id.chuck_edit);
        chuck_keyboard = (Button) view.findViewById(R.id.chuck_keyboard);
        chuck_speak = (Button) view.findViewById(R.id.chuck_speak);

        chuck_keyboard.setOnClickListener(listener);
        chuck_speak.setOnClickListener(listener);

        chuck_edit.setVisibility(View.VISIBLE);
        chuck_speak.setText("扔出去");
        chuck_keyboard.setVisibility(View.GONE);
    }

    //扔瓶子的动画
    public void initChuckPop1() {
        inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.chuck_pop1, null);
        pop1 = new PopupWindow(view, WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT);
        pop1.setTouchable(true);
        pop1.setOutsideTouchable(true);
        pop1.setFocusable(true);

        view.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.chuck_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (y < height) {
                        pop1.dismiss();
                        pop1 = null;
                    }
                    if (y > height) {
                        pop1.dismiss();
                        pop1 = null;
                    }
                }
                return true;
            }
        });

        chuck_empty2 = (ImageView) view.findViewById(R.id.chuck_empty2);
        chuck_empty1 = (ImageView) view.findViewById(R.id.chuck_empty1);
        chuck_spray = (ImageView) view.findViewById(R.id.chuck_spray);
    }

    //屏幕触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 获取触摸坐标
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
//					//触摸屏幕时刻
            case MotionEvent.ACTION_DOWN:
                m++;
                if (m % 2 == 0) {
//                    layout.setVisibility(View.GONE);
                } else {
//                    layout.setVisibility(View.VISIBLE);
                }
                break;
        }
        return super.onTouchEvent(event);

    }

    public void stillBottle(String content) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("content", content);
        HttpUtil.post(Url.stillBottle, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        String errorDescription = response.getString("errorDescription");
                        ToastUtil.showMessage(errorDescription);
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
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }

}

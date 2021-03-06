package com.kaichaohulian.baocms.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kaichaohulian.baocms.R;

public class chuckAnimationActivity extends Activity {

    private AnimationDrawable ad;

    private ImageView chuck_empty2, chuck_empty1, chuck_spray;
    private RelativeLayout chuck_bottle_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.chuck_pop1);

        chuck_empty2 = (ImageView) findViewById(R.id.chuck_empty2);
        chuck_empty1 = (ImageView) findViewById(R.id.chuck_empty1);
        chuck_spray = (ImageView) findViewById(R.id.chuck_spray);
        chuck_bottle_layout = (RelativeLayout) findViewById(R.id.chuck_bottle_layout);

        Animation animationR = AnimationUtils.loadAnimation(this, R.anim.anim_set);
        Animation animationS = AnimationUtils.loadAnimation(this, R.anim.anim_set);
        Animation animationT = AnimationUtils.loadAnimation(this, R.anim.chuck_bottle_translate);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(animationR);
        set.addAnimation(animationS);
        set.addAnimation(animationT);

        doStartAnimation(set);
        //doStartAnimation(R.anim.chuck_bottle_translate);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                chuck_spray.setVisibility(View.VISIBLE);
                ad.setOneShot(true);
                ad.start();
                chuck_empty1.setVisibility(View.GONE);
                chuck_empty2.setVisibility(View.GONE);
            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(chuckAnimationActivity.this, DriftBottleActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);


    }


    private void doStartAnimation(AnimationSet set) {
//    	chuck_empty1.startAnimation(set);
//    	chuck_empty2.startAnimation(set);
        chuck_bottle_layout.startAnimation(set);
    }

    //播放语音动画
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        ad = (AnimationDrawable) getResources().getDrawable(R.anim.bottle_spray);
        if (chuck_spray != null) {
            chuck_spray.setBackgroundDrawable(ad);
        }

    }

}

package com.kaichaohulian.baocms.activity;

import android.widget.CompoundButton;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.util.SPUtils;
import com.kaichaohulian.baocms.util.Utils;
import com.kaichaohulian.baocms.view.SwitchButton;

public class NewMessageNotificationActivity extends BaseActivity {

    private SwitchButton audio_swtich, shake_swtich, buyer_circle_update;


    @Override
    public void setContent() {
        setContentView(R.layout.new_message_notification);
        audio_swtich = getId(R.id.audio_swtich);
        shake_swtich = getId(R.id.shake_swtich);
        buyer_circle_update = getId(R.id.buyer_circle_update);

    }

    @Override
    public void initData() {
        boolean vibrateStatus = (boolean) SPUtils.get(NewMessageNotificationActivity.this, "isOpenVibrate", false);
        boolean audioStatus = (boolean) SPUtils.get(NewMessageNotificationActivity.this, "isOpenAudio", false);
        boolean circleUpdate = (boolean) SPUtils.get(NewMessageNotificationActivity.this, "circleUpdate", false);
        audio_swtich.setChecked(audioStatus);
        audio_swtich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Utils.makeSoundPool(NewMessageNotificationActivity.this);
                }
                SPUtils.put(NewMessageNotificationActivity.this, "isOpenAudio", isChecked);
            }
        });
        shake_swtich.setChecked(vibrateStatus);
        shake_swtich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Utils.makeVibrate(NewMessageNotificationActivity.this);
                }
                SPUtils.put(NewMessageNotificationActivity.this, "isOpenVibrate", isChecked);
            }
        });
        buyer_circle_update.setChecked(circleUpdate);
        buyer_circle_update.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.put(NewMessageNotificationActivity.this, "circleUpdate", isChecked);
            }
        });
    }

    @Override
    public void initView() {
        setCenterTitle("新消息提示");
    }

    @Override
    public void initEvent() {

    }
}

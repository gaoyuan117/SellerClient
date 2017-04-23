package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.base.BaseActivity;

/**
 * 我的设置 通用界面
 */
public class MeSettingsNormalActivity extends BaseActivity {

    RelativeLayout mLanguage, mFace, mSmallVideo, mStorage, mClean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContent() {
        setContentView(R.layout.me_settings_normal);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        mLanguage = getId(R.id.multi_language_linear);
        mFace = getId(R.id.myface_linear);
        mSmallVideo = getId(R.id.small_video_linear);
        mStorage = getId(R.id.storage_linear);
        mClean = getId(R.id.clean_talking_linear);

        setCenterTitle("通用");

    }

    @Override
    public void initEvent() {
        mSmallVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ActivityUtil.next(getActivity(),SmallVideoNetChooseActivity.class);

            }
        });
        mStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ActivityUtil.next(MeSettingsNormalActivity.this,StorageSpaceActivity.class);
            }
        });
        mClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //               ActivityUtil.next(MeSettingsNormalActivity.this, PrivateSettingActivity.class);
            }
        });
        mLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //              ActivityUtil.next(MeSettingsNormalActivity.this, SayHiActivity.class);
            }
        });

    }
}

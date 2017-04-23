package com.kaichaohulian.baocms.activity;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.view.GlideCircleTransform;

/**
 * Created by LMS on 2017/2/23.
 */

public class BusinessQrCode extends BaseActivity {
    private ImageView two_code;
    private String qrCode;
    @Override
    public void setContent() {
        setContentView(R.layout.activity_business_qrcode);
    }

    @Override
    public void initData() {
        qrCode = getIntent().getStringExtra("shopQrCode");
    }

    @Override
    public void initView() {
        setCenterTitle("二维码");
        two_code=getId(R.id.two_code);
        Glide.with(getActivity()).load(qrCode).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(getActivity())).into(two_code);
    }

    @Override
    public void initEvent() {

    }
}

package com.kaichaohulian.baocms.activity;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.utils.QrCodeUtils;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.google.zxing.WriterException;

public class GroupCodeActivity extends BaseActivity {

    private ImageView imgTwoCode;
    private TextView txtName;
    String code, name;


    @Override
    public void setContent() {
        setContentView(R.layout.activity_group_code);
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            code = getIntent().getStringExtra("code");
            name = getIntent().getStringExtra("name");
        }
        if (StringUtils.isEmpty(code)) {
            finish();
        }
    }

    @Override
    public void initView() {
        boolean isFromGroup = getIntent().getBooleanExtra("isFromGroup", false);
        if (isFromGroup) {
            setCenterTitle("群二维码");
        } else {
            setCenterTitle("我的二维码");
        }
        imgTwoCode = getId(R.id.two_code);
        txtName = getId(R.id.name_text);
        txtName.setText(name);
        Glide.with(getActivity()).load("http://115.29.99.167:8081/SellerNet/" + code).diskCacheStrategy(DiskCacheStrategy.ALL).into(imgTwoCode);
    }

    @Override
    public void initEvent() {
        try {
            Bitmap twoCodeBitmap = QrCodeUtils.Create2DCode(StringUtils.getDataTime());
            imgTwoCode.setImageBitmap(twoCodeBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}

package com.kaichaohulian.baocms.activity;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.QrCodeUtils;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.google.zxing.WriterException;

public class MyTwoCode extends BaseActivity {

    private ImageView imgTwoCode;
    private ImageView imgHeadIcon;
    private ImageView sexy_icon;
    private TextView txtName;
    private TextView txtDistrict;

    @Override
    public void setContent() {
        setContentView(R.layout.my_two_code);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("我的二维码");
        imgTwoCode = getId(R.id.two_code);
        imgHeadIcon = getId(R.id.head_img);
        txtName = getId(R.id.name_text);
        txtDistrict = getId(R.id.address_text);
        sexy_icon = getId(R.id.sexy_icon);
        txtDistrict.setText(MyApplication.getInstance().UserInfo.getDistrictId());
        txtName.setText(MyApplication.getInstance().UserInfo.getUsername());
        Glide.with(getActivity()).
                load(MyApplication.getInstance().UserInfo.getAvatar())
                .error(R.mipmap.default_useravatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgHeadIcon);

        Glide.with(this)
                .load("http://115.126.100.146:8080/ZFishApp/api/qr/userQRCode.do?userId=" + MyApplication.getInstance().UserInfo.getUserId())
                .crossFade()
                .into(imgTwoCode);


        String sexT = MyApplication.getInstance().UserInfo.getSex();
        if (sexT.equals("0"))
            sexy_icon.setImageResource(R.mipmap.men);
        else
            sexy_icon.setImageResource(R.mipmap.women);

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

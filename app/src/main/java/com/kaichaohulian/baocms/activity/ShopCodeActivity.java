package com.kaichaohulian.baocms.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

public class ShopCodeActivity extends BaseActivity {

    private ImageView imgTwoCode;
    private ImageView imgHeadIcon;
    private ImageView sexy_icon;
    private TextView txtName;
    private TextView txtAddr;

    @Override
    public void setContent() {
        setContentView(R.layout.my_two_code);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("店铺二维码");
        imgTwoCode = getId(R.id.two_code);
        imgHeadIcon = getId(R.id.head_img);
        txtName = getId(R.id.name_text);
        sexy_icon = getId(R.id.sexy_icon);
        txtAddr = getId(R.id.address_text);


        String name = getIntent().getStringExtra("shopname");
        String code = getIntent().getStringExtra("shopcode");
        String addr = getIntent().getStringExtra("shopaddr");
        String avatar = getIntent().getStringExtra("shopavatar");

        txtAddr.setText(addr);
        txtName.setText(name);
        Glide.with(getActivity()).load(code).placeholder(R.mipmap.def_shop_bg).into(imgTwoCode);
        Glide.with(getActivity()).load(avatar).placeholder(R.mipmap.default_useravatar).into(imgHeadIcon);

        sexy_icon.setVisibility(View.GONE);

    }

    @Override
    public void initEvent() {
    }

}

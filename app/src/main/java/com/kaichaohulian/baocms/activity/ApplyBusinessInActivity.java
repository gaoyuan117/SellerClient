package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class ApplyBusinessInActivity extends BaseActivity {

    private static final int REQUEST_LICENCE = 11;
    private static final int REQUEST_CORPER  = 12;
    private EditText editName, editAddress, editPhone;
    private ImageView imgLicense, imgCorperPerson;

    private TextView tvComplete;
    private List<String> phoneUrl;

    @Override
    public void setContent() {
        setContentView(R.layout.apply_business_in);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        editAddress = getId(R.id.apply_edt_address);
        editName    = getId(R.id.apply_edt_name);
        editPhone   = getId(R.id.apply_edt_phone);
        imgCorperPerson = getId(R.id.apply_img_corperate_image);
        imgLicense  = getId(R.id.apply_img_license);
        setCenterTitle("商家入驻");
        tvComplete = setRightTitle("完成");
    }

    @Override
    public void initEvent() {
        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastMsg("已保存店铺信息");
                finish();
            }
        });
        imgLicense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(ApplyBusinessInActivity.this);
                intent.setPhotoCount(1);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_LICENCE);
            }
        });
        imgCorperPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(ApplyBusinessInActivity.this);
                intent.setShowCamera(true);
                intent.setPhotoCount(1);
                startActivityForResult(intent, REQUEST_CORPER);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case  REQUEST_LICENCE:
                    if (data != null){
                        phoneUrl = new ArrayList<String>();
                        phoneUrl = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                        Glide.with(getActivity())
                                .load(phoneUrl.get(0))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imgLicense);
                    }
                    break;
                case  REQUEST_CORPER:
                    if (data != null){
                        phoneUrl = new ArrayList<String>();
                        phoneUrl = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                        Glide.with(getActivity())
                                .load(phoneUrl.get(0))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(imgCorperPerson);
                    }
                    break;
            }
        }
    }
}

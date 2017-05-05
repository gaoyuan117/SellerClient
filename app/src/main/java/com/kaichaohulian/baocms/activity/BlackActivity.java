package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.util.TitleUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BlackActivity extends BaseActivity {

    @BindView(R.id.tv_black_title)
    TextView tvBlackTitle;
    @BindView(R.id.et_black_content)
    EditText tvPayMoney;
    @BindView(R.id.ll_black_content)
    LinearLayout llBlackContent;
    @BindView(R.id.img_black_pic)
    ImageView imgBlackPic;
    @BindView(R.id.img_black_updown)
    ImageView imgBlackUpdown;
    @BindView(R.id.cb_black_xl)
    CheckBox cbBlackXl;
    @BindView(R.id.ll_black_xl)
    LinearLayout llBlackXl;
    @BindView(R.id.cb_black_weixie)
    CheckBox cbBlackWeixie;
    @BindView(R.id.ll_black_weixie)
    LinearLayout llBlackWeixie;

    private String type;//上传类型 1 加入黑名单  2 删除好友
    private String chooseType; //选择的类型 1 下流  2 威胁
    private boolean isShow = true;//选择是否打开  默认打开

    @Override
    public void setContent() {
        setContentView(R.layout.activity_black);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        type = getIntent().getStringExtra("type");
        if (type.equals("1")) {
            tvBlackTitle.setText("拉入黑名单");
            new TitleUtils(this).setTitle("拉入黑名单");
        } else if (type.equals("2")) {
            tvBlackTitle.setText("删除好友");
            new TitleUtils(this).setTitle("删除好友");
        }
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }



    @OnClick({R.id.ll_black_xl, R.id.ll_black_weixie,R.id.ll_black_content, R.id.img_black_pic,R.id.ll_black_choose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_black_xl:
                cbBlackXl.setChecked(true);
                cbBlackWeixie.setChecked(false);
                chooseType = "1";
                break;
            case R.id.ll_black_weixie:
                cbBlackXl.setChecked(false);
                cbBlackWeixie.setChecked(true);
                chooseType = "2";
                break;
            case R.id.ll_black_content:
                break;
            case R.id.img_black_pic:
                break;
            case R.id.ll_black_choose:
                isShow = !isShow;
                if(isShow){
                    llBlackXl.setVisibility(View.VISIBLE);
                    llBlackWeixie.setVisibility(View.VISIBLE);
                    imgBlackUpdown.setImageResource(R.mipmap.arrow_up2);
                }else {
                    llBlackXl.setVisibility(View.GONE);
                    llBlackWeixie.setVisibility(View.GONE);
                    imgBlackUpdown.setImageResource(R.mipmap.arrow_down2);
                }
                break;
        }
    }
}

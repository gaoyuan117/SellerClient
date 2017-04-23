package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.base.BaseActivity;

public class SendingRedBagNextActivity extends BaseActivity {

    ImageView imgClose;
    TextView txtSend;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_sending_red_bag_next);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        imgClose = getId(R.id.img_close);
        txtSend = getId(R.id.send);
    }

    @Override
    public void initEvent() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                ActivityUtil.next(SendingRedBagNextActivity.this,SelectContactActionActivity.class,bundle,2);
            }
        });
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode,Intent intent){
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case 2:
                    Intent backIntent = new Intent();
                    backIntent.putExtra("mRecipients",intent.getStringExtra("mRecipients"));
                    setResult(RESULT_OK,backIntent);
                    finish();
                    break;

            }

        }else {
            showToastMsg("密码错误！发红包失败！");
        }


    }
}

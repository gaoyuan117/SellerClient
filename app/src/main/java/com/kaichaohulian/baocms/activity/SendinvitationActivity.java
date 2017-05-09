package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendinvitationActivity extends BaseActivity {


    @BindView(R.id.edt_invitiontitle)
    EditText edtInvitiontitle;
    @BindView(R.id.edt_InvitionPay)
    EditText edtInvitionPay;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_sendinvitation);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("发送邀请");
        TextView tv = setRightTitle("确定");
        tv.setBackgroundResource(R.mipmap.rounded_rectangle);
        tv.setTextColor(getResources().getColor(R.color.blue));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               
            }
        });
    }

    @Override
    public void initEvent() {

    }



    @OnClick({R.id.rl_reciver_sendInvition, R.id.rl_sendInvitionNum, R.id.rl_InvitionPay, R.id.rl_Invitiontime, R.id.rl_responsetime, R.id.rl_Activity_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_reciver_sendInvition:
                break;
            case R.id.rl_sendInvitionNum:
                break;
            case R.id.rl_InvitionPay:
                break;
            case R.id.rl_Invitiontime:
                break;
            case R.id.rl_responsetime:
                break;
            case R.id.rl_Activity_location:
                break;
        }
    }
}

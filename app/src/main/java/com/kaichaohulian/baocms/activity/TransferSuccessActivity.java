package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.UserInfoManager;
import com.kaichaohulian.baocms.base.BaseActivity;

public class TransferSuccessActivity extends BaseActivity {
    @Override
    public void setContent() {
        setContentView(R.layout.activity_transfer_success);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setTitle("转账详情");
        UserInfoManager.getInstance().updateUserCache(getActivity());
        String amount = getIntent().getStringExtra("amount");
        TextView amountTV = (TextView) findViewById(R.id.tv_transfer_amount);
        TextView mypckBtn = (TextView) findViewById(R.id.tv_transfer_mypck);
        amountTV.setText("¥" + amount);
        mypckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyChangeActivity.class);
                getActivity().startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void initEvent() {

    }
}

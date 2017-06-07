package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.UserInfoManager;
import com.kaichaohulian.baocms.base.BaseActivity;

public class TransferSuccessActivity extends BaseActivity {

    private String amount, uid;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_transfer_success);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("转账详情");
        UserInfoManager.getInstance().updateUserCache(getActivity());
        amount = getIntent().getStringExtra("amount");
        uid = getIntent().getStringExtra("uid");
        Log.e("gy", "哈哈：" + uid);
        if (!TextUtils.isEmpty(uid)) {
//            searchUser(uid);
        }
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

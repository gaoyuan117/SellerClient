package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;

public class MyChangeActivity extends BaseActivity {

    Button withdraw;
    Button chongzhi;
    TextView myRestMoney;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_my_change);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("我的零钱");
        setRightTitle("零钱明细").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyChangeActivity.this, SmallMoneyDetailActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
            }
        });
        withdraw = getId(R.id.btn_withdraw);
        chongzhi = getId(R.id.btn_chongzhi);
        myRestMoney = getId(R.id.my_change_number);
    }

    @Override
    public void initEvent() {
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), WithdrawApplyActivity.class);
            }
        });
        chongzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), ChangeRechargeActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        myRestMoney.setText(MyApplication.getInstance().UserInfo.getBalance());
    }

    public void back(View view) {
        finish();
    }

}



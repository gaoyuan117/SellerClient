package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.base.BaseActivity;

public class BusinessApplyOneActivity extends BaseActivity {

    public static BusinessApplyOneActivity BusinessApplyOneActivity;

    private EditText shopName;
    private EditText techIncome;
    private Button btnNext;

    @Override
    public void setContent() {
        setContentView(R.layout.apply_business_in_1);
    }

    @Override
    public void initData() {
        BusinessApplyOneActivity = this;
    }

    @Override
    public void initView() {
        setCenterTitle("申请商家入驻");
        shopName = getId(R.id.apply_edt_name);
        techIncome = getId(R.id.apply_edt_tech);
        btnNext = getId(R.id.btn_next);
    }

    @Override
    public void initEvent() {

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shopName.getText().toString().equals("") || techIncome.getText().toString().equals("")) {
                    showToastMsg("请填写完整");
                    return;
                }
                if (Integer.parseInt(techIncome.getText().toString()) < 5) {
                    showToastMsg("技术服务费不能低于5%");
                    return;
                }
                Bundle Bundle = new Bundle();
                Bundle.putString("shopName", shopName.getText().toString());
                Bundle.putString("techIncome", techIncome.getText().toString());
                ActivityUtil.next(getActivity(), BusinessApplyTwoActivity.class, Bundle);
            }
        });
    }
}

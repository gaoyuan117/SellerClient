package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.base.BaseActivity;

public class BusinessApplyTwoActivity extends BaseActivity {

    public static BusinessApplyTwoActivity BusinessApplyTwoActivity;

    private EditText edtAddr;
    private EditText edtPhone;
    private EditText edtContact;
    private Button btnNext;

    private Intent intent;

    @Override
    public void setContent() {
        setContentView(R.layout.apply_business_in_2);
    }

    @Override
    public void initData() {
        BusinessApplyTwoActivity = this;
        intent = getIntent();
    }

    @Override
    public void initView() {
        setCenterTitle("申请商家入驻");
        edtAddr = getId(R.id.apply_edt_addr);
        edtPhone = getId(R.id.apply_edt_phone);
        edtContact = getId(R.id.apply_edt_contact);
        btnNext = getId(R.id.btn_next);
    }

    @Override
    public void initEvent() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtAddr.getText().toString().equals("") || edtPhone.getText().toString().equals("") || edtContact.getText().toString().equals("")) {
                    showToastMsg("请填写完整");
                    return;
                }
                Bundle Bundle = new Bundle();
                Bundle.putString("shopName", intent.getStringExtra("shopName"));
                Bundle.putString("techIncome", intent.getStringExtra("techIncome"));
                Bundle.putString("addr", edtAddr.getText().toString());
                Bundle.putString("phone", edtPhone.getText().toString());
                Bundle.putString("contact", edtContact.getText().toString());
                ActivityUtil.next(getActivity(), BusinessApplyThressActivity.class, Bundle);
            }
        });

    }
}

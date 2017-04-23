package com.kaichaohulian.baocms.activity;


import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

public class PersonalSignActivity extends BaseActivity {

    EditText edtSigh;
    Button   btnSave;


    @Override
    public void setContent() {
        setContentView(R.layout.personal_sign_layout);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        edtSigh = getId(R.id.personal_sign_edit);
        btnSave = getId(R.id.btn_save_sign);
        setCenterTitle("个性签名");
    }

    @Override
    public void initEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(TextUtils.isEmpty(edtSigh.getText().toString().trim()))){
                    String sign = edtSigh.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("sign", sign);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

    }
}

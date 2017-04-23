package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.MeAddressEntity;

/**
 * 添加我的地址
 */
public class MeAddAddressActivity extends BaseActivity {

    private EditText editTextName, editTextPhone, editTextArea, editTextDetailAddress, editTextYouZheng;
    private Button btnCommit;
    private MeAddressEntity entity;
    private boolean ifChange = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContent() {
        setContentView(R.layout.me_settings_change_address);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

        editTextName = getId(R.id.change_address_shou_huo_ren_edit);
        editTextPhone = getId(R.id.change_address_phone_number_edit);
        editTextArea = getId(R.id.change_address_area_information);
        editTextDetailAddress = getId(R.id.change_address_street_gate_edit);
        editTextYouZheng = getId(R.id.change_address_postcode);
        btnCommit = getId(R.id.add_address_btn);
        setCenterTitle("添加我的地址");
        Intent intent = getIntent();
        if (intent != null) {
            changeInitData(intent);
        }
    }

    public boolean changeInitData(Intent intent) {
        entity = (MeAddressEntity) intent.getSerializableExtra("change");
        if (entity != null) {
            editTextName.setText(entity.getShouHuoname());
            editTextDetailAddress.setText(entity.getAddress());
            editTextPhone.setText(entity.getShouHuoPhone());
            editTextArea.setText(entity.getArea());
            editTextYouZheng.setText(entity.getPostCode());
        }
        ifChange = true;
        return true;
    }

    @Override
    public void initEvent() {
        editTextName.addTextChangedListener(watcher);
        editTextDetailAddress.addTextChangedListener(watcher);
        editTextPhone.addTextChangedListener(watcher);
        editTextArea.addTextChangedListener(watcher);
        editTextYouZheng.addTextChangedListener(watcher);
        btnCommit.setOnClickListener(new BtnListener());
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (TextUtils.isEmpty(s.toString())) {
                btnCommit.setEnabled(false);
            } else {
                btnCommit.setEnabled(true);
            }
        }
    };


    class BtnListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String name = editTextName.getText().toString().trim();
            String phone = editTextPhone.getText().toString().trim();
            String address = editTextDetailAddress.getText().toString().trim();
            String area = editTextArea.getText().toString().trim();
            String postCode = editTextYouZheng.getText().toString().trim();
            if ((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(phone)) && (!TextUtils.isEmpty(address))) {

                MeAddressEntity result = new MeAddressEntity();
                result.setAddress(address);
                result.setShouHuoname(name);
                result.setShouHuoPhone(phone);
                result.setArea(area);
                result.setPostCode(postCode);
                if (ifChange) {
                    if (result != null && entity != null) {
                        result.setAddressId(entity.getAddressId());
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("result", result);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                return;
            }
        }
    }
}

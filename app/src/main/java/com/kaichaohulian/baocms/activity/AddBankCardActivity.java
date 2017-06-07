package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

/**
 * 添加银行卡
 */
public class AddBankCardActivity extends BaseActivity {

    private Spinner editTextOpenBank;
    private EditText editTextName, editTextPhone, editTextCardNumber, add_bankcard_real_idcard_edit;
    private Button btnConfirm;

    private String[] data = {"工商银行", "农业银行", "建设银行", "中信银行", "中国银行", "招商银行"};
    private int index = 0;

    @Override
    public void setContent() {
        setContentView(R.layout.add_bank_card_layout);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("添加银行卡");
        editTextName = getId(R.id.add_bankcard_realname_edit);
        editTextPhone = getId(R.id.add_bankcard_real_phone_edit);
        editTextCardNumber = getId(R.id.add_bankcard_real_cardnumbers);
        editTextOpenBank = getId(R.id.add_bankcard_real_banks_edit);
        btnConfirm = getId(R.id.add_bankcard_btn);
        add_bankcard_real_idcard_edit = getId(R.id.add_bankcard_real_idcard_edit);

        editTextCardNumber.setInputType(EditorInfo.TYPE_CLASS_NUMBER);

//        editTextCardNumber.addTextChangedListener(new MyTextWatcher());
//        editTextPhone.addTextChangedListener(new MyTextWatcher());
//        editTextName.addTextChangedListener(new MyTextWatcher());
//        add_bankcard_real_idcard_edit.addTextChangedListener(new MyTextWatcher());

        editTextOpenBank.setAdapter(new Adapter());
    }

    @Override
    public void initEvent() {
        editTextOpenBank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                index = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String phoneNumber = editTextPhone.getText().toString();
                String cardnumber = editTextCardNumber.getText().toString();
                String openBank = data[index];
                if (TextUtils.isEmpty(add_bankcard_real_idcard_edit.getText().toString())) {
                    showToastMsg("请输入身份证号");
                    return;
                } else if (TextUtils.isEmpty(name)) {
                    showToastMsg("请输入姓名");
                    return;
                } else if (TextUtils.isEmpty(phoneNumber)) {
                    showToastMsg("请输入预留手机号");
                    return;
                } else if (TextUtils.isEmpty(cardnumber)) {
                    showToastMsg("请输入银行卡号");
                    return;
                }
                if(cardnumber.length()<4){
                    showToastMsg("银行卡号最少四位");
                    return;
                }
                btnConfirm.setEnabled(false);
                Intent intent = new Intent();
                intent.putExtra("username", name);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("cardNo", cardnumber);
                intent.putExtra("openbank", openBank);
                intent.putExtra("cardType", "0");
                intent.putExtra("idcard", add_bankcard_real_idcard_edit.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

//    class MyTextWatcher implements TextWatcher {
//        @Override
//        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            boolean s1 = editTextCardNumber.getText().length() >= 16;
//            boolean s2 = editTextName.getText().length() > 0;
//            boolean s3 = add_bankcard_real_idcard_edit.getText().length() == 18;
//            boolean s4 = editTextPhone.getText().length() == 11;
//            if (s1 && s2 && s3 && s4) {
//                btnConfirm.setEnabled(true);
//                btnConfirm.setBackground(getResources().getDrawable(R.mipmap.deepgreen_bar));
//            } else {
//                btnConfirm.setEnabled(false);
//                btnConfirm.setBackgroundColor(getResources().getColor(R.color.action_bar_tittle_color));
//            }
//        }
//
//        @Override
//        public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//        }
//
//        @Override
//        public void afterTextChanged(Editable s) {
//
//
//        }
//    }

    public class Adapter extends BaseAdapter {
        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public String getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_text, null);
            TextView title = (TextView) convertView.findViewById(R.id.tv_title);
            title.setText(data[position]);
            return convertView;
        }
    }
}

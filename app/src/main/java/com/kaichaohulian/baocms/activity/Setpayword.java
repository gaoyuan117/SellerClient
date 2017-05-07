package com.kaichaohulian.baocms.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.view.PasswordEdittext;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Setpayword extends BaseActivity {
    @BindView(R.id.sign_paypassword_edt)
    PasswordEdittext edt1;
    @BindView(R.id.again_paypassword_edt)
    PasswordEdittext edt2;
    @BindView(R.id.setpayword_cash_btn)
    Button setpaywordCashBtn;
    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
//            if (edt1.getEditableText().length() == 6 && edt2.getEditableText().length() == 6 && edt3.getEditableText().length() == 6) {
//                changpaywordCashBtn.setBackgroundResource(R.mipmap.deeporange_bar_part);
//            } else {
//                changpaywordCashBtn.setBackgroundResource(R.mipmap.deeporange_bar_normal);
//            }
            if (edt1.getEditableText().length() == 6) {
                edt2.setEnabled(true);
                edt2.setFocusable(true);
                edt2.setFocusableInTouchMode(true);
                edt2.requestFocus();
            } else {
                edt2.setEnabled(false);
                edt2.setFocusable(false);
                edt2.setFocusableInTouchMode(false);

            }

            if (edt2.getEditableText().length() == 6) {
                setpaywordCashBtn.setBackgroundResource(R.mipmap.deeporange_bar_part);
                setpaywordCashBtn.setClickable(true);
            } else {
                setpaywordCashBtn.setBackgroundResource(R.mipmap.deeporange_bar_normal);
                setpaywordCashBtn.setClickable(false);
            }
        }
    };


    @Override
    public void setContent() {
        setContentView(R.layout.activity_setpayword);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        edt1.addTextChangedListener(mTextWatcher);
        edt2.addTextChangedListener(mTextWatcher);

        edt1.setFocusableInTouchMode(true);
        edt1.setFocusable(true);
        edt1.requestFocus();

        edt2.setEnabled(false);
        edt2.setFocusable(false);
        edt2.setFocusableInTouchMode(false);
    }
    @OnClick(R.id.setpayword_cash_btn)
    public void onViewClicked() {
        if(map==null){
            map=new HashMap<>();
        }else{
            map.clear();
        }
        if(!edt1.getText().toString().trim().equals(edt2.getText().toString().trim())){
            edt2.getEditableText().clear();
            return;
        }
        map.put("id", MyApplication.getInstance().UserInfo.getUserId()+"");
        map.put("password",edt1.getText().toString().trim());
        RetrofitClient.getInstance().createApi().setPayWord(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        Toast.makeText(Setpayword.this, "设置成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

}

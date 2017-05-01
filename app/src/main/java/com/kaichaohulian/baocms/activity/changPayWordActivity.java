package com.kaichaohulian.baocms.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.view.PasswordEdittext;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class changPayWordActivity extends BaseActivity {


    @BindView(R.id.edt_paypassword_changpayword)
    PasswordEdittext edt1;
    @BindView(R.id.edt_newpaypassword_changpayword)
    PasswordEdittext edt2;
    @BindView(R.id.edt_againpaypassword_changpayword)
    PasswordEdittext edt3;
    @BindView(R.id.changpayword_cash_btn)
    Button changpaywordCashBtn;
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
                edt3.setEnabled(true);
                edt3.setFocusable(true);
                edt3.setFocusableInTouchMode(true);
                edt3.requestFocus();
            } else {
                edt3.setEnabled(false);
                edt3.setFocusable(false);
                edt3.setFocusableInTouchMode(false);

            }
            if (edt3.getEditableText().length() == 6) {
                changpaywordCashBtn.setBackgroundResource(R.mipmap.deeporange_bar_part);
            } else {
                changpaywordCashBtn.setBackgroundResource(R.mipmap.deeporange_bar_normal);
            }
        }
    };

    @Override
    public void setContent() {
        setContentView(R.layout.activity_chang_pay_word);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        map=new HashMap<>();
    }

    @Override
    public void initView() {
        setCenterTitle("修改支付密码");

        edt1.setFocusableInTouchMode(true);
        edt1.setFocusable(true);
        edt1.requestFocus();

        edt2.setEnabled(false);
        edt2.setFocusable(false);
        edt2.setFocusableInTouchMode(true);
        edt3.setEnabled(false);
        edt3.setFocusable(false);
        edt2.setFocusableInTouchMode(true);
        edt1.addTextChangedListener(mTextWatcher);
        edt2.addTextChangedListener(mTextWatcher);
        edt3.addTextChangedListener(mTextWatcher);
    }

    @Override
    public void initEvent() {

    }

    private void setPayword(){
        map.clear();
        map.put("id",MyApplication.getInstance().UserInfo.getId()+"");
        map.put("password",edt3.getText().toString().trim());
        RetrofitClient.getInstance().createApi().setPayWord(map)
                .compose(RxUtils.<HttpResult>io_main())
                .subscribe(new Observer<HttpResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult value) {
                        if(value.errorDescription.equals("设置支付密码成功!")){
                            Toast.makeText(changPayWordActivity.this, "修改支付密码成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(changPayWordActivity.this, "修改失败，请稍候再试", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @OnClick(R.id.changpayword_cash_btn)
    public void onClick() {
        map.clear();
        map.put("id", MyApplication.getInstance().UserInfo.getUserId()+"");
        map.put("password",edt1.getText().toString().trim());
        if (!edt3.getText().toString().trim().equals(edt2.getText().toString().trim())) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            edt3.getText().clear();
            return;
        }
        RetrofitClient.getInstance().createApi().verificatPayword(map)
                .compose(RxUtils.<HttpResult>io_main())
                .subscribe(new Observer<HttpResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HttpResult value) {
                        Log.e(TAG, "onNext: "+value.errorDescription );
                        if(value.errorDescription.equals("支付密码验证成功!")){
                            setPayword();
                        }else{
                            Toast.makeText(changPayWordActivity.this, "原密码输入错误", Toast.LENGTH_SHORT).show();
                            edt1.getText().clear();
                            edt2.getText().clear();
                            edt3.getText().clear();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}

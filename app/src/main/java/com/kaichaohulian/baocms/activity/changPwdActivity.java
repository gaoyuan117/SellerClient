package com.kaichaohulian.baocms.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class changPwdActivity extends BaseActivity {


    @BindView(R.id.edt_original_password)
    EditText edtOriginalPassword;
    @BindView(R.id.edt_new_password)
    EditText edtNewPassword;
    @BindView(R.id.edt_againnew_password)
    EditText edtAgainnewPassword;
    @BindView(R.id.btn_changePwd)
    Button btnChangePwd;
    private TextWatcher textWatcher;
    @Override
    public void setContent() {
        setContentView(R.layout.activity_chang_pwd);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        btnChangePwd.setClickable(false);
        btnChangePwd.setBackgroundResource(R.mipmap.deeporange_bar_normal);
        if(textWatcher==null){
            textWatcher=new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(edtAgainnewPassword.getText().length()>6&&edtNewPassword.getText().length()>6&&edtOriginalPassword.getText().length()>6){
                        btnChangePwd.setBackgroundResource(R.mipmap.deeporange_bar_part);
                        btnChangePwd.setClickable(true);
                    }else{
                        btnChangePwd.setBackgroundResource(R.mipmap.deeporange_bar_normal);
                        btnChangePwd.setClickable(true);
                    }
                }
            };
        }
        edtOriginalPassword.addTextChangedListener(textWatcher);
        edtNewPassword.addTextChangedListener(textWatcher);
        edtAgainnewPassword.addTextChangedListener(textWatcher);
    }

    @Override
    public void initEvent() {

    }

    @OnClick(R.id.btn_changePwd)
    public void onClick() {
        if(edtNewPassword.getText().toString().trim().equals(edtAgainnewPassword.getText().toString().trim())){
            if(map==null){
                map=new HashMap<>();
            }else{
                map.clear();
            }
            map.put("oldPassword",edtOriginalPassword.getText().toString().trim());
            map.put("newPassword",edtNewPassword.getText().toString().trim());
            map.put("reNewPassword",edtAgainnewPassword.getText().toString().trim());
            map.put("phoneNumber", MyApplication.getInstance().UserInfo.getPhoneNumber());

            RetrofitClient.getInstance().createApi().ChangePassWord(map)
                    .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                    .subscribe(new BaseObjObserver<CommonEntity>(getActivity()) {
                        @Override
                        protected void onHandleSuccess(CommonEntity commonEntity) {
                            Toast.makeText(changPwdActivity.this, "修改密码成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }else{
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
        }
    }



}

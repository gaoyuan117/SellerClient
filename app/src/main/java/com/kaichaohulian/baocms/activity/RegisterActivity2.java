package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 注册页
 */
public class RegisterActivity2 extends BaseActivity {
    private TextView et_usertel, code_time, iv_back;
    private EditText et_code;
    private Button btn_register;
    private String phoneNumber;
    private Timer mTimer = null;
    private int mTime;

    @Override
    public void setContent() {
        setContentView(R.layout.register2_activity);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        btn_register = (Button) findViewById(R.id.btn_register);
        et_usertel = getId(R.id.et_usertel);
        et_code = getId(R.id.et_code);
        code_time = getId(R.id.code_time);
        iv_back = getId(R.id.iv_back);

        if (getIntent() != null) {
            phoneNumber = getIntent().getStringExtra("phoneNumber");
        }

        if (mTimer == null) {
            mTimer = new Timer();
            mTime = 60;
            code_time.setText("接收短信大约需要" + mTime + "秒");
        }

        et_usertel.setText(phoneNumber + "");
    }

    @Override
    public void initEvent() {
        et_code.addTextChangedListener(new TextChange());
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_register.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Bundle Bundle = new Bundle();
                Bundle.putString("phoneNumber", phoneNumber);
                Bundle.putString("code", et_code.getText().toString().trim());
                ActivityUtil.next(getActivity(), RegisterActivity3.class, Bundle);
            }
        });

        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        mTime--;
                        code_time.setText("接收短信大约需要" + mTime + "秒");
                        if (mTime == 0) {
                            mTimer.cancel();
                            mTimer = null;
                        }
                    }
                });
            }
        }, 0, 1000);
    }


    // EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {
            boolean Sign2 = et_code.getText().length() >= 6;
            if (Sign2) {
                btn_register.setEnabled(true);
            } else {
                btn_register.setEnabled(false);
            }
        }

    }

    public void back(View view) {
        finish();
    }

}

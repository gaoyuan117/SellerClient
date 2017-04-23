package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.AppManager;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.BankCardEntity;
import com.kaichaohulian.baocms.entity.GroupMMEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimq on 2017/3/3.
 */
public class RelativeMMGroupActivity extends BaseActivity {
    private EditText et_number_1;
    private EditText et_number_2;
    private EditText et_number_3;
    private EditText et_number_4;
    private String mCode;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_relative_group);
        setCenterTitle("面对面建群");
        et_number_1 = getId(R.id.et_number_1);
        et_number_2 = getId(R.id.et_number_2);
        et_number_3 = getId(R.id.et_number_3);
        et_number_4 = getId(R.id.et_number_4);
        et_number_1.setFocusable(true);
        et_number_1.setFocusableInTouchMode(true);
        et_number_1.requestFocus();
        viewFocusSetting();
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    private void viewFocusSetting() {
        et_number_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    et_number_2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        et_number_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    et_number_3.requestFocus();
                } else {
                    et_number_1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_number_2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (et_number_2.getText().length() == 0) {
                        et_number_1.requestFocus();
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });


        et_number_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    et_number_4.requestFocus();
                } else {
                    et_number_2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        et_number_3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_DEL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (et_number_3.getText().length() == 0) {
                        et_number_2.requestFocus();
                        return true;
                    } else {
                        return false;
                    }
                }
                return false;
            }
        });
        et_number_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0) {
                    mCode = et_number_1.getText().toString().trim()
                            + et_number_2.getText().toString().trim()
                            + et_number_3.getText().toString().trim()
                            + et_number_4.getText().toString().trim();
                    Intent intent = new Intent(RelativeMMGroupActivity.this, RelativeMMGroupActivity2.class);
                    intent.putExtra("code", mCode);
                    startActivity(intent);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}

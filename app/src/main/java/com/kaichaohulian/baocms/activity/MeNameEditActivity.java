package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.utils.StringUtils;

/**
 * 我的姓名 编辑页面
 */
public class MeNameEditActivity extends BaseActivity {
    EditText editTextName;
    Button btnSave;
    String mTitleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContent() {
        setContentView(R.layout.me_name_edit);
    }

    @Override
    public void initData() {
        if (getIntent() != null) {
            mTitleName = getIntent().getStringExtra("mTitleName");
        }

    }

    @Override
    public void initView() {
        editTextName = getId(R.id.me_name_edittext);
        btnSave = getId(R.id.btn_save);
        switch (mTitleName){
            case "setName":
                setCenterTitle("我的名字");
//                editTextName.setHint("请输入你的名字\n");
                break;
            case "setJob":
                setCenterTitle("我的职业");
                editTextName.setHint("请输入您的职业");
                break;
            case "setHobby":
                setCenterTitle("我的爱好");
                editTextName.setHint("请输入您的爱好");
                break;
            default:
                setCenterTitle("修改" + mTitleName);
                if (mTitleName.equals("群公告")) {
                    editTextName.getLayoutParams().height *= 3;
                    editTextName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(500)});
                }
                editTextName.setHint("请输入" + mTitleName);
                break;
        }

    }

    @Override
    public void initEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(TextUtils.isEmpty(editTextName.getText()))) {
                    String txt = editTextName.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("result", txt);
                    setResult(RESULT_OK, intent);

                    finish();
                }
            }
        });
    }

}

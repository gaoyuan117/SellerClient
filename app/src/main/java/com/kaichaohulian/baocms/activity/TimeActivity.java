package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeActivity extends BaseActivity {


    @BindView(R.id.et_time_hour)
    EditText etTimeHour;
    @BindView(R.id.et_time_m)
    EditText etTimeM;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_time);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        setCenterTitle("响应时间");
        setRightTitle("确定").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = etTimeHour.getText().toString();
                String min = etTimeM.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("hour", hour);
                if(min==""){

                }else{
                    intent.putExtra("min", min);
                }
                setResult(110, intent);
                finish();
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

}

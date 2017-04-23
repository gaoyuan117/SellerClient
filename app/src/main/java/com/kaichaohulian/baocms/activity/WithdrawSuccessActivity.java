package com.kaichaohulian.baocms.activity;

import android.view.View;
import android.widget.Button;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

/**
 * Created by HuaSao1024 on 2017/2/4.
 */

public class WithdrawSuccessActivity extends BaseActivity {

    private Button sure;

    @Override
    public void setContent() {
        setContentView(R.layout.withdraw_success_activity);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("成功");
        sure = getId(R.id.sure);
    }

    @Override
    public void initEvent() {
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

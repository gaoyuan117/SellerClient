package com.kaichaohulian.baocms.activity;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

public class AdverDetailActivity extends BaseActivity {

    @Override
    public void setContent() {
        setContentView(R.layout.activity_adver_detail);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {
        openRedPackageDialog();
    }

    private void openRedPackageDialog(){
        View view = View.inflate(this,R.layout.pop_adver_detail_red,null);
        Dialog dialog = new Dialog(this,R.style.dialog_type);
        dialog.setContentView(view);
        dialog.show();
    }
}

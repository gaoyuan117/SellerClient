package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RemarkActivity extends BaseActivity {

    @BindView(R.id.et_remark)
    EditText etRemark;

    private String friendId;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_remark);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        setCenterTitle("设置备注");
        friendId = getIntent().getStringExtra("friendId");
        Log.e("gy", "好友id:" + friendId);
        setRightTitle("确定").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String remark = etRemark.getText().toString();
                if (TextUtils.isEmpty(remark)) {
                    ToastUtil.showMessage("请输入备注");
                }
                setRemark(remark);

            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    private void setRemark(final String remark) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", MyApplication.getInstance().UserInfo.getUserId());
        map.put("friendId", friendId);
        map.put("remark", remark);
        RetrofitClient.getInstance().createApi().setRemark(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(this, "请稍后") {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        ToastUtil.showMessage("设置成功");
                        Intent intent = new Intent();
                        intent.putExtra("remark",remark);
                        setResult(111,intent);
                        finish();
                    }
                });
    }

}

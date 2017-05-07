package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.util.Log;
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

public class AddfriendPay extends BaseActivity {


    @BindView(R.id.btn_setPayNeed)
    Button btnSetPayNeed;
    @BindView(R.id.edt_addfriendPay)
    EditText edtAddfriendPay;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_addfriend_pay);
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

    }

    @OnClick(R.id.btn_setPayNeed)
    public void onViewClicked() {
        int pay=0;
        if(!edtAddfriendPay.getText().toString().trim().equals("")){
            try{
                pay= Integer.parseInt(edtAddfriendPay.getText().toString().trim());
            }catch (ClassCastException e){
                Toast.makeText(this, "请输入正确金额", Toast.LENGTH_SHORT).show();
                edtAddfriendPay.getEditableText().clear();
                return;
            }
        }
        if(map==null){
            map=new HashMap<>();
        }else{
            map.clear();
        }
        if(pay==0){
            Toast.makeText(this, "金额不能为0", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.e(TAG, "onViewClicked: "+pay );
        map.put("id", MyApplication.getInstance().UserInfo.getUserId()+"");
        map.put("money",pay+"");
        RetrofitClient.getInstance().createApi().SetNeedPay(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        Toast.makeText(AddfriendPay.this, "设置成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }


}

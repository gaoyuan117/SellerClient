package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

/**
 * 我的姓名 编辑页面
 */
public class ShopDurationEditActivity extends BaseActivity {
    EditText editTextName;
    Button btnSave;
    String mTitleName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContent() {
        setContentView(R.layout.aty_shop_duration);
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
        setCenterTitle("商铺营业时间");
    }

    @Override
    public void initEvent() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateShop();
            }
        });
    }

    public void updateShop() {
        RequestParams params = new RequestParams();
        params.put("shopid", getIntent().getStringExtra("shopid"));
        params.put("buinessTime", editTextName.getText().toString().trim());
        DBLog.e("params", params.toString());
        HttpUtil.post(Url.business_updateShop, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("修改商家信息：", response.toString());
                    if (response.getInt("code") == 0) {
                        ToastUtil.showMessage("修改商户信息成功");
                        Intent intent = new Intent();
                        intent.putExtra("buinessTime", editTextName.getText().toString().trim());
                        setResult(ShopInfoActivity.RESULT_OK, intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ShowDialog.dissmiss();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }
}

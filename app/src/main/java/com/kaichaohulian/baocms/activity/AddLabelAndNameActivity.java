package com.kaichaohulian.baocms.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加标签界面
 * Created by Administrator on 2016/12/14 0014.
 */

public class AddLabelAndNameActivity extends BaseActivity implements View.OnClickListener {
    private TextView mTvSave;
    private EditText mEtLabel, mEtName, mEtPhone, mEtDesc;
    private long friendId;


    @Override
    public void setContent() {
        setContentView(R.layout.addlabel_and_name_activity);
    }

    @Override
    public void initData() {
        friendId = getIntent().getLongExtra("friendId", 0);

    }


    @Override
    public void initView() {
        setCenterTitle("标签");
        mTvSave = getId(R.id.tv_right_text);
        mTvSave.setText("保存");
        mEtLabel = getId(R.id.et_label);
        mEtName = getId(R.id.et_name);
        mEtPhone = getId(R.id.et_phone_number);
        mEtDesc = getId(R.id.et_desc);
    }

    @Override
    public void initEvent() {
        mTvSave.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right_text:
                if (StringUtils.isEmpty(mEtLabel.getText().toString().trim())) {
                    showToastMsg("请输入标签名称");
                    return;
                }
                if (StringUtils.isEmpty(mEtName.getText().toString().trim())) {
                    showToastMsg("请输入备注名");
                    return;
                }
                if (StringUtils.isEmpty(mEtPhone.getText().toString().trim())) {
                    showToastMsg("请输入电话号码");
                    return;
                }
                if (StringUtils.isEmpty(mEtDesc.getText().toString().trim())) {
                    showToastMsg("请输入描述");
                    return;
                }
                createLabel(mEtLabel.getText().toString().trim(), mEtName.getText().toString().trim(),
                        mEtPhone.getText().toString().trim(), mEtDesc.getText().toString().trim());
                break;
        }
    }

    /**
     * 创建标签
     */
    private void createLabel(String label, String name, String phone, String desc) {
        try {
            JSONArray JSONArray = new JSONArray();
            JSONArray.put(phone);
            RequestParams params = new RequestParams();
            JSONObject JSONObject = new JSONObject();
            JSONObject.put("id", MyApplication.getInstance().UserInfo.getUserId());
            JSONObject.put("friendId", friendId);
            JSONObject.put("remark", name);
            JSONObject.put("lableName", label);
            JSONObject.put("describe", desc);
            JSONObject.put("phoneNumbers", JSONArray);
            params.put("JsonString", JSONObject);
            HttpUtil.post(Url.setUplable, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Log.e("保存：", response.toString());
                        if (response.getInt("code") == 0) {
                            showToastMsg("保存成功");
                            finish();
                        }
                    } catch (Exception e) {
                        showToastMsg("保存失败");
                        e.printStackTrace();
                    } finally {
                        ShowDialog.dissmiss();
                    }
                }

                @Override
                public void onFinish() {
                    ShowDialog.dissmiss();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    showToastMsg("请求服务器失败");
                    DBLog.e("tag", statusCode + ":" + responseString);
                    ShowDialog.dissmiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

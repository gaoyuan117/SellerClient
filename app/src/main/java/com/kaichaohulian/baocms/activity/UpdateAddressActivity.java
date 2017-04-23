package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.kaichaohulian.baocms.widget.AddressSelectorWheelView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;


/***
 * 所在地UI
 */
public class UpdateAddressActivity extends BaseActivity implements View.OnClickListener {
    private AddressSelectorWheelView mAddressSWV;
    private DataHelper mDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContent() {
        setContentView(R.layout.activity_update_address);
        mDataHelper = new DataHelper(getActivity());
        setCenterTitle("修改所在地");
        mAddressSWV = getId(R.id.address_aswv);
    }

    @Override
    public void initData() {
        findViewById(R.id.commit_btn).setOnClickListener(this);
        mAddressSWV.setRlTitleVis(false);
        mAddressSWV.setAddressLLVis(true);
        mAddressSWV.setWheellView(Color.WHITE);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commit_btn:
                RequestParams params = new RequestParams();
                params.put("id", MyApplication.getInstance().UserInfo.getUserId());
                params.put("districtName", mAddressSWV.getSelectAddress());
                HttpUtil.get(Url.changePersonalInformation, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            DBLog.e("修改个人资料：", response.toString());
                            if (response.getInt("code") == 0) {
                                MyApplication.getInstance().UserInfo.setDistrictId(mAddressSWV.getSelectAddress());
                                mDataHelper.UpdateUserInfo(MyApplication.getInstance().UserInfo);
                            }
                            Intent intent = new Intent();
                            intent.putExtra("loc", mAddressSWV.getSelectAddress());
                            setResult(PersonalActivity.RESULT_OK, intent);
                            finish();
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
                break;
        }
    }
}

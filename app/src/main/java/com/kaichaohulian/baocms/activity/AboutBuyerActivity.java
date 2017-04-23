package com.kaichaohulian.baocms.activity;

import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.entity.GroupDetail;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Jimq on 2017/2/18.
 */
public class AboutBuyerActivity extends BaseActivity {
    private TextView about_content;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_about_buyer);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        setCenterTitle("关于买家");
        about_content = getId(R.id.about_content);
        getContent();
    }

    @Override
    public void initEvent() {

    }

    public void getContent() {
        HttpUtil.post(Url.about, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取买家信息：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONObject jsonObject = response.getJSONObject("dataObject");
                        about_content.setText(jsonObject.getString("content"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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

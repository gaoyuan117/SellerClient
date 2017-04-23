package com.kaichaohulian.baocms.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.SmallMoneyAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.SmallMoneyBean;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SmallMoneyDetailActivity extends BaseActivity {

    private ListView listView;
    private SmallMoneyAdapter smallMoneyAdapter;
    private List<SmallMoneyBean> data = new ArrayList<SmallMoneyBean>();
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setContent() {
        setContentView(R.layout.small_money_detail_list);
        type = getIntent().getStringExtra("type");
    }

    @Override
    public void initData() {


    }

    @Override
    public void initView() {

        listView = getId(R.id.small_money_list);
        setCenterTitle("零钱明细");
    }

    @Override
    public void initEvent() {
        getdata();

    }

    /**
     * 测试数据
     */
    public void getdata() {
        ShowDialog.showDialog(getActivity(), "正在获取零钱明细...", false, null);
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getId());
        if (type.equals("1")) {
            params.put("type", "R");
        } else {
            params.put("type", "SMALL");
        }
        params.put("page", "1");
        HttpUtil.post(Url.getRecharge, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("零钱明细：", response.toString());
                    if (response.getInt("code") == 0) {
                        DBLog.e("登录：", response.toString());
//                        JSONObject JSONObject = response.getJSONObject("dataObject");
                        org.json.JSONArray array = response.getJSONArray("dataObject");
                        data = JSONArray.parseArray(array.toString(), SmallMoneyBean.class);
                        listView.setAdapter(new SmallMoneyAdapter(getApplicationContext(), data));
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    showToastMsg("获取数据失败");
                    e.printStackTrace();
                } finally {
                    ShowDialog.dissmiss();
                }

            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, org.apache.http.Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }

        });

    }
}

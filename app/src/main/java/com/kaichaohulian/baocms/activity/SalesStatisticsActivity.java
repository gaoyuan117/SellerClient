package com.kaichaohulian.baocms.activity;

import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.SaleOrderAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.SaleOrder;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SalesStatisticsActivity extends BaseActivity {

    TextView tvSaleNum;
    ListView list;
    SaleOrderAdapter adapter;
    List<SaleOrder> data = new ArrayList<>();

    @Override
    public void setContent() {
        setContentView(R.layout.activity_sales_statistics);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {
        setCenterTitle("销售统计");
        tvSaleNum = getId(R.id.tv_sale_num);
        list = getId(R.id.list);
    }

    @Override
    public void initEvent() {

        adapter = new SaleOrderAdapter(this, R.layout.sale_statistics_item,data);
        list.setAdapter(adapter);

        RequestParams params = new RequestParams();
        params.put("shopid", getIntent().getStringExtra("shopid"));
        params.put("page", 1);
        HttpUtil.get(Url.business_getSalesStatusByShopId, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        JSONObject jsonObject = response.getJSONObject("dataObject");
                        int total = jsonObject.getInt("totalMoney");
                        String orders = jsonObject.getString("orders");
                        if(null!= orders){
                            JSONArray array = response.getJSONArray("orders");
                            data = JSON.parseArray(array.toString(),SaleOrder.class);
                        }
                        tvSaleNum.setText(total);
                        adapter.notifyDataSetChanged();
                    }
                    showToastMsg(response.getString("errorDescription"));
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    ShowDialog.dissmiss();
                }
            }

            @Override
            public void onFinish() {}

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }
}

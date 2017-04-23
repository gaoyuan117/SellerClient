package com.kaichaohulian.baocms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.UnpayAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseFragment;
import com.kaichaohulian.baocms.entity.ScanListEntity;
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

/**
 * Created by liuyu on 2017/1/16.
 */

@SuppressLint("ValidFragment")
public class UnpayFragment extends BaseFragment {
    UnpayAdapter adapter;
    List<ScanListEntity> data;
    ListView listView;

    public UnpayFragment(MyApplication myApplication, Activity activity, Context context) {
        super(myApplication, activity, context);
    }

    @Override
    public void setContent() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_unpay,null);
    }

    @Override
    public void initData() {
        data = new ArrayList<ScanListEntity>();
//        testData();


    }

    @Override
    public void initView() {
        listView = getId(R.id.unpay_list);
//        adapter = new UnpayAdapter(getContext(),data);
//        listView.setAdapter(adapter);
    }

    @Override
    public void initEvent() {

    }
    public void testData(){
//        for (int i = 1; i<= 9; i++){
//            ScanListEntity entity = new ScanListEntity();
//            entity.setAccount(260.50);
//            data.add(entity);
//        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getShopPayOrders();
    }

    public void getShopPayOrders(){
        RequestParams params = new RequestParams();
        params.put("shopid", Long.valueOf(getArguments().getString("shopid")));
        params.put("status", 0);
        params.put("page", 1);
        HttpUtil.get(Url.business_canCodeOrders, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取订单信息：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray array = response.getJSONArray("dataObject");
                        data = JSON.parseArray(array.toString(),ScanListEntity.class);
//                        adapter.notifyDataSetChanged();
                        adapter = new UnpayAdapter(getContext(),data);
                        listView.setAdapter(adapter);

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

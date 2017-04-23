package com.kaichaohulian.baocms.activity;

import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.SendRedBagListAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.SendRedBagListEntity;
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

public class SendRedBagActivity extends BaseActivity {

    private ListView listView;
    private List<SendRedBagListEntity> data;
    private SendRedBagListAdapter adapter;

    private int redCount;
    private double redsum;
    private String years;

    @Override
    public void setContent() {
        setContentView(R.layout.sent_rebag);
    }

    @Override
    public void initData() {
        data = new ArrayList<SendRedBagListEntity>();
        adapter = new SendRedBagListAdapter(getActivity(), data);
    }

    @Override
    public void initView() {
        setCenterTitle("发出的红包");
        listView = getId(R.id.sent_redbag_list);
        listView.setAdapter(adapter);
    }

    @Override
    public void initEvent() {
        getData();
    }

    private int page = 1;

    private void getData() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("page", page);
        HttpUtil.post(Url.moneyreds_getUserRedOutList, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("用户发出的红包记录：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONObject JSONObject = response.getJSONObject("dataObject");
                        redCount = JSONObject.getInt("redCount");
                        redsum = JSONObject.getDouble("redsum");
                        years = JSONObject.getString("years");
                        JSONArray JSONArray = JSONObject.getJSONArray("reds");
                        for (int i = 0; i < JSONArray.length(); i++) {
                            JSONObject json = JSONArray.getJSONObject(i);
                            SendRedBagListEntity SendRedBagListEntity = new SendRedBagListEntity();
                            SendRedBagListEntity.setCreatedTime(json.getString("createdTime"));
                            SendRedBagListEntity.setLeftRedCount(json.getInt("leftRedCount"));
                            SendRedBagListEntity.setRedCount(json.getInt("redCount"));
                            SendRedBagListEntity.setRedtype(json.getString("redtype"));
                            SendRedBagListEntity.setStatus(json.getString("status"));
                            SendRedBagListEntity.setSum(json.getDouble("sum"));
                            SendRedBagListEntity.setType(json.getInt("type"));
                            data.add(SendRedBagListEntity);
                        }
                        adapter.setHead(redCount, redsum, years);
                        adapter.notifyDataSetChanged();
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
                ShowDialog.dissmiss();
            }
        });
    }
}

package com.kaichaohulian.baocms.activity;

import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.ReceviedRedBagListAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.ReceivedRedBagListEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.DateUtil;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReceviedRedBagListActivity extends BaseActivity {

    private ReceviedRedBagListAdapter adapter;
    private ListView listView;
    private List<ReceivedRedBagListEntity> data;

    private int redCount;
    private double redsum;
    private String time;
    private int bests;

    @Override
    public void setContent() {
        setContentView(R.layout.received_redbag_list);

    }

    @Override
    public void initData() {
        data = new ArrayList<ReceivedRedBagListEntity>();
        adapter = new ReceviedRedBagListAdapter(getActivity(), data);
    }

    @Override
    public void initView() {
        setCenterTitle("收到的红包");
        listView = getId(R.id.received_redbag_list);

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
        params.put("year", DateUtil.getCurrDateWithFormat("yyyy"));
        HttpUtil.post(Url.moneyreds_getUserRedInList, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("用户收到的红包记录：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONObject JSONObject = response.getJSONObject("dataObject");
                        redCount = JSONObject.getInt("redCount");
                        redsum = JSONObject.getDouble("redsum");
                        time = JSONObject.getString("time");
                        bests = JSONObject.getInt("bests");
                        JSONArray JSONArray = JSONObject.getJSONArray("reds");
                        for (int i = 0; i < JSONArray.length(); i++) {
                            JSONObject json = JSONArray.getJSONObject(i);
                            ReceivedRedBagListEntity ReceivedRedBagListEntity = new ReceivedRedBagListEntity();
                            ReceivedRedBagListEntity.setCreatedTime(json.getString("createdTime"));
                            ReceivedRedBagListEntity.setAvatar(json.getString("avatar"));
                            ReceivedRedBagListEntity.setBest(json.getBoolean("best"));
                            ReceivedRedBagListEntity.setUseracount(json.getDouble("useracount"));
                            ReceivedRedBagListEntity.setUserName(json.getString("userName"));
                            data.add(ReceivedRedBagListEntity);
                        }
                        adapter.setHead(redCount, redsum, time, bests);
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

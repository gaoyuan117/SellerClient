package com.kaichaohulian.baocms.activity;

import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.WithdrawDetailAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.WithdrawDetail;
import com.kaichaohulian.baocms.entity.WithdrawDetailEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.DateUtil;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WithdrawDetailActivity extends BaseActivity {

    ListView listView;
    TextView txtTotalNum;
    List<WithdrawDetailEntity> data;
    WithdrawDetail detail;
    WithdrawDetailAdapter adapter;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_withdraw_detail_list);
    }

    @Override
    public void initData() {
        data = new ArrayList<WithdrawDetailEntity>();
//        testData();
        setCenterTitle("提现记录");
        adapter = new WithdrawDetailAdapter(getApplicationContext(), data);
    }

    @Override
    public void initView() {
        txtTotalNum = getId(R.id.tv_withdraw_account);
        ((TextView) getId(R.id.tv_date)).setText(DateUtil.getCurrDateWithFormat("yyyy年MM月"));

        listView = getId(R.id.list);
        listView.setAdapter(adapter);

    }

    @Override
    public void initEvent() {
        getWithdrawDetailData();
    }

    public void testData() {
        for (int i = 0; i <= 8; i++) {
            WithdrawDetailEntity entity = new WithdrawDetailEntity();
            entity.setAccount(350.50 + i);
            entity.setDate("11月2" + i + "日");
            entity.setTime("12:30");
            entity.setWhichBank("中信银行");
            entity.setLastFourNumber("5589");
            data.add(entity);
        }
    }

    public void getWithdrawDetailData() {
        if (MyApplication.getInstance().UserInfo == null) {
            return;
        }
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getId());
        params.put("page", 1);
        params.put("year", DateUtil.getCurrDateWithFormat("yyyy-MM"));
        HttpUtil.post(Url.moneyreds_getUserCash, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取提现记录：", response.toString());
                    if (response.getInt("code") == 0) {
                        refreshUserCash(response);
                    }
                } catch (Exception e) {
                    showToastMsg("获取提现记录失败");
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

    private void refreshUserCash(JSONObject response) {

        detail = JSON.parseObject(response.toString(), WithdrawDetail.class);

        if (detail != null) {
            txtTotalNum.setText(detail.getTotalMoney() + "");

            if (detail.getUserCashs() != null) {
                data = detail.getUserCashs();
                adapter.notifyDataSetChanged();
            }
        }
    }

}

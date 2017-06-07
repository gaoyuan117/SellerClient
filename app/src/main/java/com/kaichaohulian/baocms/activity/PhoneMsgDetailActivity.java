package com.kaichaohulian.baocms.activity;

import android.widget.AbsListView;
import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.PhoneMsgDetailAdapter;
import com.kaichaohulian.baocms.adapter.WithDrawlsDetailAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.PhoneMsgDetailEntity;
import com.kaichaohulian.baocms.entity.WithDrawMsgEntity;
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
 * Created by Jimq on 2017/3/12.
 */

public class PhoneMsgDetailActivity extends BaseActivity {
    private ListView mListView;
    private List<PhoneMsgDetailEntity> mList;
    private PhoneMsgDetailAdapter mAdapter;
    private int mPage = 1;
    boolean isLoadFinish;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_withdrawals_detail);
        mListView = getId(R.id.withdraw_detail_msg);
        setCenterTitle("充值消息");
        mList = new ArrayList<>();
        mAdapter = new PhoneMsgDetailAdapter(getApplicationContext(), mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (mList.size() != 0 && mListView.getLastVisiblePosition() != 0 && mListView.getLastVisiblePosition() == (mListView.getCount() - 1)) {
                            getMsgList(mPage);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        getMsgList(mPage);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    public void getMsgList(int page) {
        if (isLoadFinish) return;
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("page", page);
        HttpUtil.get(Url.getRechargeMessage, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("手机充值消息详情：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray JSONArray = response.getJSONArray("dataObject");
                        if (JSONArray.length() == 0) {
                            if (mList.size() == 0) {
                                ToastUtil.showMessage("您还未进行过充值");
                            }
                            isLoadFinish = true;
                            return;
                        }
                        for (int i = 0; i < JSONArray.length(); i++) {
                            PhoneMsgDetailEntity phoneMsgDetailEntity = new PhoneMsgDetailEntity();
                            JSONObject jsonObject = JSONArray.getJSONObject(i);
                            phoneMsgDetailEntity.setActualamount(jsonObject.getString("actualamount"));
                            phoneMsgDetailEntity.setTimeStamp(jsonObject.getInt("timeStamp"));
                            phoneMsgDetailEntity.setPhoneNumber(jsonObject.getString("phoneNumber"));
                            phoneMsgDetailEntity.setCreatedTime(jsonObject.getString("createdTime"));
                            mList.add(phoneMsgDetailEntity);
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                    mPage++;
                    showToastMsg(response.getString("errorDescription"));
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

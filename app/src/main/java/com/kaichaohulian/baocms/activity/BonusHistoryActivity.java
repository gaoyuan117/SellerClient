package com.kaichaohulian.baocms.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BonusHistoryActivity extends Activity {

    private ListView bonusLV;
    private int mPage=1;
    boolean isLoadFinish;
    private BottleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bonus);
        bonusLV = (ListView) findViewById(R.id.lv_bonus);
        TextView titleTV = (TextView) findViewById(R.id.center_title_tv);
        LinearLayout exit_layout = (LinearLayout) findViewById(R.id.exit_layout);
        titleTV.setText("系统分红");
        exit_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mAdapter = new BottleAdapter();
        bonusLV.setAdapter(mAdapter);

        bonusLV.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (mList.size() != 0 && bonusLV.getLastVisiblePosition() != 0 && bonusLV.getLastVisiblePosition() == (bonusLV.getCount() - 1)) {
                            fenhong(mPage);
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        fenhong(mPage);
    }

    class BonusInfo {
        String content;
        String date;
    }

    class BottleAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            BonusInfo bottleInfo = mList.get(position);

            View view = null;
            ViewHolder holder = null;
            if (convertView == null) {
                view = View.inflate(BonusHistoryActivity.this, R.layout.item_bonus, null);
                holder = new ViewHolder();

                holder.tv_bonus_date = (TextView) view.findViewById(R.id.tv_bonus_date);
                holder.tv_bonus_message = (TextView) view.findViewById(R.id.tv_bonus_message);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            holder.tv_bonus_date.setText(bottleInfo.date);
            holder.tv_bonus_message.setText(bottleInfo.content);
            return view;
        }

        class ViewHolder {
            TextView tv_bonus_date;
            TextView tv_bonus_message;
        }
    }

    List<BonusInfo> mList = new ArrayList<>();

    public void fenhong(int page) {
        if (isLoadFinish) return;
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("page", page);
        HttpUtil.post(Url.fenhongOrders, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
                        SimpleDateFormat formator = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                        String errorDescription = response.getString("errorDescription");
                        JSONArray dataObject = response.getJSONArray("dataObject");
                        if (dataObject.length() == 0) {
                            isLoadFinish = true;
                            if (mList.size()==0){
                                ToastUtil.showMessage("您还未有系统分红");
                            }
                            return;
                        }
                        List<BonusInfo> tempList = new ArrayList<>();
                        BonusInfo bonusInfo = null;
                        for (int i = 0; i < dataObject.length(); i++) {
                            bonusInfo = new BonusInfo();
                            JSONObject object = (JSONObject) dataObject.get(i);
                            double amount = object.getDouble("amount") / 100;
                            BigDecimal b = new BigDecimal(amount);
                            double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                            bonusInfo.content = "系统给你分红" + f1 + "元";
                            long createTime = object.getLong("createTime")*1000;
                            bonusInfo.date = formator.format(new Date(createTime));
                            tempList.add(bonusInfo);
                            bonusInfo = null;
                        }
                        mList.addAll(tempList);
                        mAdapter.notifyDataSetChanged();
                    }
                    mPage++;
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
                ToastUtil.showMessage("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }
}

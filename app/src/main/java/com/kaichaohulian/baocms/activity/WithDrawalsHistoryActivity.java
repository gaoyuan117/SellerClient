package com.kaichaohulian.baocms.activity;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.WithDrawalsBean;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.util.Utils;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jimq on 2017/2/19.
 */
public class WithDrawalsHistoryActivity extends BaseActivity {
    private ListView mWithdrawList;
    private Adapter mAdapter;
    private List<WithDrawalsBean> data = new ArrayList<WithDrawalsBean>();

    @Override
    public void setContent() {
        setContentView(R.layout.activity_draw_history);
        setCenterTitle("提现记录");
    }

    @Override
    public void initData() {
        mAdapter = new Adapter();
    }

    @Override
    public void initView() {
        mWithdrawList = getId(R.id.withdraw_list);
    }

    @Override
    public void initEvent() {
        getWithDrawalsList();
    }

    public void getWithDrawalsList() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("page", "1");
        HttpUtil.post(Url.withdrawalsById, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("提现记录：", response.toString());
                    if (response.getInt("code") == 0) {
                        org.json.JSONArray array = response.getJSONArray("dataObject");
                        data = JSONArray.parseArray(array.toString(), WithDrawalsBean.class);
                        mWithdrawList.setAdapter(mAdapter);
                    }
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

    public class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public WithDrawalsBean getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_with_drawals, null);
            TextView title = (TextView) convertView.findViewById(R.id.withdraw_title);
            if (data.get(position).getZfbAccount() != null) {
                title.setText(data.get(position).getZfbAccount() + "    " + ((double) (data.get(position).getMoney())) / 100 + "元");
            } else if (data.get(position).getWeixinAccount() != null) {
                title.setText(data.get(position).getWeixinAccount() + "    " + ((double) (data.get(position).getMoney()) / 100) + "元");
            } else if (data.get(position).getBankName() != null) {
                title.setText(data.get(position).getBankName() + "    " + ((double) (data.get(position).getMoney())) / 100 + "元");
            }
            TextView time = (TextView) convertView.findViewById(R.id.withdraw_time);
            TextView status = (TextView) convertView.findViewById(R.id.status);
            if (data.get(position).getStatus()) {
                status.setText("已处理");
            } else {
                status.setText("未处理");
            }
//            title.setText(data.get(position).get + "(" + data.get(position).getCardNo().substring(data.get(position).getCardNo().length() - 4, data.get(position).getCardNo().length()) + ")");
            String timeDate = Utils.stampToDate(data.get(position).getAddtime());
            time.setText(timeDate);
            return convertView;
        }
    }

}

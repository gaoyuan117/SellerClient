package com.kaichaohulian.baocms.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Build;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
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

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Jimq on 2017/2/19.
 */
public class WithDrawalsHistoryActivity extends BaseActivity {
    private ListView mWithdrawList;
    private Adapter mAdapter;
    private List<WithDrawalsBean> data = new ArrayList<WithDrawalsBean>();
    private Dialog timeselect;
    private View timeSelectView;
    private TextView tv_starttime, tv_stoptime;
    private String date;
    private String selectType;
    private DecimalFormat mDecimalFormat = new DecimalFormat("#.00");

    @Override
    public void setContent() {
        setContentView(R.layout.activity_draw_history);
        setCenterTitle("提现记录");
//        setIm1_view(R.mipmap.ic_action_search).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (timeselect == null) {
//                    timeSelectView = View.inflate(WithDrawalsHistoryActivity.this, R.layout.alert_timeselect, null);
//                    tv_starttime = (TextView) timeSelectView.findViewById(R.id.tv_timeselect_start);
//                    tv_stoptime = (TextView) timeSelectView.findViewById(R.id.tv_timeselect_stop);
//                    timeSelectView.findViewById(R.id.img_with_draw_dialog).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            timeselect.dismiss();
//                        }
//                    });
//                    timeSelectView.findViewById(R.id.rl_timeselect_start).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            selectType = "start";
//                            openDateDialog();
//                        }
//                    });
//                    timeSelectView.findViewById(R.id.rl_timeselect_stop).setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            selectType = "stop";
//                            openDateDialog();
//                        }
//                    });
//                    timeselect = new Dialog(getActivity(), R.style.dialog_type);
//                    timeselect.setCancelable(true);
//                    timeselect.setContentView(timeSelectView);
//                    timeselect.show();
//                } else {
//                    timeselect.show();
//                }
//            }
//        });
    }

    @Override
    public void initData() {
        mAdapter = new Adapter();
    }

    @Override
    public void initView() {
        mWithdrawList = getId(R.id.withdraw_list);

    }

    /**
     * 打开日期对话框
     */
    private void openDateDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = year + ":" + (monthOfYear + 1) + ":" + dayOfMonth;
                Log.e("gy", "日期：" + date);
                if (selectType.equals("start")) {
                    tv_starttime.setText(date);
                } else {
                    tv_stoptime.setText(date);
                }

            }
        }, calendar.get(Calendar.YEAR), calendar
                .get(Calendar.MONTH), calendar
                .get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setCancelable(true);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();

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

        public double add(double v1, double v2) {
            BigDecimal b1 = new BigDecimal(Double.toString(v1));
            BigDecimal b2 = new BigDecimal(Double.toString(v2));
            return b1.add(b2).doubleValue();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_with_drawals, null);
            }
            try {

                TextView title = (TextView) convertView.findViewById(R.id.withdraw_title);
//            if (data.get(position).getZfbAccount() != null) {
//                title.setText(data.get(position).getZfbAccount() + "    " + ((double) (data.get(position).getMoney())) / 100 + "元");
//            } else if (data.get(position).getWeixinAccount() != null) {
//                title.setText(data.get(position).getWeixinAccount() + "    " + ((double) (data.get(position).getMoney()) / 100) + "元");
//            } else if (data.get(position).getBankName() != null) {
//                title.setText(data.get(position).getBankName() + "    " + ((double) (data.get(position).getMoney())) / 100 + "元");
//            }
                if (data.get(position).getZfbAccount() != null || data.get(position).getWeixinAccount() != null || data.get(position).getBankName() != null) {
                    Log.d("Adapter", "data.get(position).getMoney():" + data.get(position).getMoney());
                    Log.d("Adapter", "data.get(position).getRealmoney():" + data.get(position).getRealmoney());
                    double i =add(data.get(position).getMoney() ,data.get(position).getRealmoney());

                    title.setText("申请提现    " + i+ "元");
                }
                TextView time = (TextView) convertView.findViewById(R.id.withdraw_time);
                TextView status = (TextView) convertView.findViewById(R.id.status);
                if (data.get(position).getStatus() != 0) {
                    status.setText("已处理");
                    status.setTextColor(Color.RED);
                } else {
                    status.setText("未处理");
                    status.setTextColor(Color.GREEN);
                }
//            title.setText(data.get(position).get + "(" + data.get(position).getCardNo().substring(data.get(position).getCardNo().length() - 4, data.get(position).getCardNo().length()) + ")");
//                String timeDate = Utils.stampToDate(data.get(position).getAddtime());
                time.setText(data.get(position).getAddtime());

            } catch (Exception e) {
                Log.e("gy", e.toString());
            }
            return convertView;

        }
    }


}

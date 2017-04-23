package com.kaichaohulian.baocms.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.Label;
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
 * 标签界面
 * Created by ljl on 2016/12/14 0014.
 */

public class LabelActivity extends BaseActivity {
    private TextView AddLabel;
    private ListView listView;
    private List<Label> mLabelList;
    private BaseAdapter adapter;
    private ReceiveBroadCast ReceiveBroadCast;


    @Override
    public void setContent() {
        setContentView(R.layout.label_activity);
    }

    @Override
    public void initData() {
        mLabelList = new ArrayList<>();
        receiveLabel();
    }

    /**
     * 刷新列表
     */
    private void receiveLabel() {
        ReceiveBroadCast = new ReceiveBroadCast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("save_label");    //只有持有相同的action的接受者才能接收此广播
        getActivity().registerReceiver(ReceiveBroadCast, filter);
    }

    public class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getLabel();
        }
    }


    @Override
    public void initView() {
        setCenterTitle("标签");
        AddLabel = getId(R.id.tv_addLabel);
        listView = getId(R.id.ListView);
        getLabel();
    }

    /**
     * 获取所有标签
     */
    private void getLabel() {
        ShowDialog.showDialog(getActivity(), "请稍后...", false, null);
        try {
            RequestParams params = new RequestParams();
            params.put("id", MyApplication.getInstance().UserInfo.getUserId());
            HttpUtil.post(Url.All_LABEL, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        Log.e("获取所有标签：", response.toString());
                        if (response.getInt("code") == 0) {
                            mLabelList.clear();
                            JSONArray jsonArray = response.getJSONArray("dataObject");
                            for (int k = 0; k < jsonArray.length(); k++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(k);
                                Label label = new Label();
                                label.setLabelId(jsonObject.getInt("lableId"));
                                label.setName(jsonObject.getString("name"));
                                label.setLabelCounts(jsonObject.getInt("lableCounts"));
                                mLabelList.add(label);
                                adapter = new BaseAdapter();
                                listView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    } catch (Exception e) {
                        showToastMsg("获取所有标签失败");
                        e.printStackTrace();
                    } finally {
                        ShowDialog.dissmiss();
                    }
                }

                @Override
                public void onFinish() {
                    ShowDialog.dissmiss();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    showToastMsg("请求服务器失败");
                    DBLog.e("tag", statusCode + ":" + responseString);
                    ShowDialog.dissmiss();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initEvent() {
        AddLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), AddLabelActivity.class);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LabelActivity.this,LabelSeeActivity.class);
                intent.putExtra("lableName",mLabelList.get(position).getName());
                intent.putExtra("lableId",mLabelList.get(position).getLabelId());
                intent.putExtra("lableCount",mLabelList.get(position).getLabelCounts());
                startActivity(intent);
            }
        });
    }

    public class BaseAdapter extends android.widget.BaseAdapter {

        @Override
        public int getCount() {
            return mLabelList.size();
        }

        @Override
        public Object getItem(int position) {
            return mLabelList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.label_listitem, null);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.content = (TextView) convertView.findViewById(R.id.content);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            Label label = mLabelList.get(position);
            if (label != null) {
                viewHolder.title.setText(label.getName() + "（" + label.getLabelCounts() + "）");
                viewHolder.content.setText(label.getName());
            }
            return convertView;
        }

        class ViewHolder {
            TextView title;
            TextView content;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(ReceiveBroadCast);
    }
}

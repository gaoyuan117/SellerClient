package com.kaichaohulian.baocms.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyBottleActivity extends Activity {

    private Button back;
    private ListView bottleLV;
    private BottleAdapter mAdapter;
    private int mPage;
    boolean isLoadFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_bottle);

        back = (Button) findViewById(R.id.my_bottle_back);
        bottleLV = (ListView) findViewById(R.id.my_bottle_list);
        back.setOnClickListener(listener);
        mAdapter = new BottleAdapter();
        bottleLV.setAdapter(mAdapter);
        bottleLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BottleInfo bottleInfo = mList.get(position);

                Bundle Bundle = new Bundle();
                Bundle.putString("userId", bottleInfo.phoneNumber);
                Bundle.putString("userNick", bottleInfo.username);
                Bundle.putString("userAvatar", bottleInfo.avatar);
                CCPAppManager.startChattingAction(MyBottleActivity.this, bottleInfo.phoneNumber, bottleInfo.username, true);
            }
        });

        bottleLV.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
                        // 判断滚动到底部
                        if (mList.size() != 0 && bottleLV.getLastVisiblePosition() != 0 && bottleLV.getLastVisiblePosition() == (bottleLV.getCount() - 1)) {
                            findMyBottle(mPage);
                            isLoadFinish = true;
                        }
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        findMyBottle(1);
    }

    private OnClickListener listener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.my_bottle_back:
                    finish();
                    break;
            }
        }
    };

    class BottleInfo {
        String content;
        String avatar;
        long creator;
        String username;
        String phoneNumber;
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
            BottleInfo bottleInfo = mList.get(position);

            View view = null;
            ViewHolder holder = null;
            if (convertView == null) {
                view = View.inflate(MyBottleActivity.this, R.layout.item_mybottle, null);
                holder = new ViewHolder();

                holder.avatarIV = (ImageView) view.findViewById(R.id.iv_mybottle_avatar);
                holder.nameTV = (TextView) view.findViewById(R.id.tv_mybottle_name);
                holder.cotentTV = (TextView) view.findViewById(R.id.tv_mybottle_content);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            ImageLoader.getInstance().displayImage(bottleInfo.avatar, holder.avatarIV);
            holder.nameTV.setText(bottleInfo.username);
            holder.cotentTV.setText(bottleInfo.content);
            return view;
        }

        class ViewHolder {
            ImageView avatarIV;
            TextView nameTV;
            TextView cotentTV;
        }
    }

    List<BottleInfo> mList = new ArrayList<>();

    public void findMyBottle(int page) {
        if (isLoadFinish) return;
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("page", page);
        HttpUtil.post(Url.findMyBottle, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        String errorDescription = response.getString("errorDescription");
                        JSONArray dataObject = response.getJSONArray("dataObject");
                        List<BottleInfo> tempList = new ArrayList<>();
                        BottleInfo bottleInfo = null;
                        for (int i = 0; i < dataObject.length(); i++) {
                            bottleInfo = new BottleInfo();
                            JSONObject object = (JSONObject) dataObject.get(i);
                            bottleInfo.creator = object.getLong("creator");
                            bottleInfo.username = object.getString("username");
                            bottleInfo.avatar = object.getString("avatar");
                            bottleInfo.content = object.getString("content");
                            bottleInfo.phoneNumber = object.getString("phoneNumber");
                            tempList.add(bottleInfo);
                            bottleInfo = null;
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

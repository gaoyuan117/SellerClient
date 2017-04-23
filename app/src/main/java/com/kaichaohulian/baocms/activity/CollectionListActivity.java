package com.kaichaohulian.baocms.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.CollectionListAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.CollectionEntity;
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

public class CollectionListActivity extends BaseActivity {

    CollectionListAdapter adapter;
    List<CollectionEntity> data;
    ListView listView;
    private EditText searchET;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_collection);

    }

    @Override
    public void initData() {
        data = new ArrayList<CollectionEntity>();
        getData();
        adapter = new CollectionListAdapter(getApplicationContext(), data);
    }

    @Override
    public void initView() {
        listView = getId(R.id.listview_collection);
        searchET = getId(R.id.et_search);
        listView.setAdapter(adapter);
        setCenterTitle("收藏");
    }

    @Override
    public void initEvent() {
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String aftertext = s.toString();
                List<CollectionEntity> list=new ArrayList<>();
                for (CollectionEntity collect : data) {
                    if (collect.getContent().contains(aftertext)||collect.getUserName().contains(aftertext)){
                        list.add(collect);
                    }
                }
                adapter = new CollectionListAdapter(getApplicationContext(), list);
                listView.setAdapter(adapter);
            }
        });
    }

    public void getData() {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("page", "1");
        HttpUtil.post(Url.obtainCollection, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("获取用户收藏：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray JSONArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < JSONArray.length(); i++) {
                            JSONObject json = JSONArray.getJSONObject(i);
                            CollectionEntity collectionEntity = new CollectionEntity();
                            collectionEntity.setUserName(json.getString("username"));
                            collectionEntity.setDate(json.getString("createTime"));
                            collectionEntity.setBigPicture(json.getString("images"));
                            collectionEntity.setContent(json.getString("images"));
                            collectionEntity.setHeadIcon(json.getString("avatar"));
                            collectionEntity.setWordType(json.getString("type"));
                            data.add(collectionEntity);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    showToastMsg("获取用户收藏失败");
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

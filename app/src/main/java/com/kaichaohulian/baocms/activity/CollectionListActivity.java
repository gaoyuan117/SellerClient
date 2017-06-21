package com.kaichaohulian.baocms.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.CollectionListAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.CollectionEntity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CollectionListActivity extends BaseActivity implements AdapterView.OnItemLongClickListener {

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
        listView.setOnItemLongClickListener(this);
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
                List<CollectionEntity> list = new ArrayList<>();
                for (CollectionEntity collect : data) {
                    if (collect.getImages().contains(aftertext) || collect.getUsername().contains(aftertext)) {
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
                            collectionEntity.setUsername(json.getString("username"));
                            collectionEntity.setCreateTime(json.getString("createTime"));
//                            collectionEntity.set(json.getString("images"));
                            collectionEntity.setImages(json.getString("images"));
                            collectionEntity.setAvatar(json.getString("avatar"));
                            collectionEntity.setType(json.getString("type"));
                            collectionEntity.setId(json.optInt("id"));
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
    public void deleteCollections(final int i){
        HashMap<String,String> map=new HashMap<>();
        map.put("id",MyApplication.getInstance().UserInfo.getUserId()+"");
        map.put("collectId",data.get(i).getId()+"");
        RetrofitClient.getInstance().createApi().DeleteCollections(map)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        data.remove(i);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(CollectionListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        new AlertDialog.Builder(getActivity())
                .setMessage("是否删除？")
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                                deleteCollections(i);
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).setCancelable(true).create().show();

        return false;
    }
}

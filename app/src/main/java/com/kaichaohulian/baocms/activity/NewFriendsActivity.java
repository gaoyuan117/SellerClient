package com.kaichaohulian.baocms.activity;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.NewFriendsAdapter;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.ApplyAndNoticeEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 申请与通知
 */
public class NewFriendsActivity extends BaseActivity {
    private ListView listView;
    private TextView et_search, tv_add;

    private List<ApplyAndNoticeEntity> List;
    private NewFriendsAdapter adapter;

    private JSONArray ContactsArray;

    @Override
    public void setContent() {
        setContentView(R.layout.newfriendsmsg_activity);
    }

    @Override
    public void initData() {
        ContactsArray = new JSONArray();
//        try {
//            testReadAllContacts();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        List = new ArrayList<>();
        adapter = new NewFriendsAdapter(getActivity(), List);
    }

    @Override
    public void initView() {
        listView = (ListView) findViewById(R.id.listview);
        et_search = (TextView) findViewById(R.id.et_search);
        tv_add = (TextView) findViewById(R.id.tv_add);

        listView.setAdapter(adapter);

        try {
            getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initEvent() {
        et_search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), AddFriendsTwoActivity.class);
            }

        });
        tv_add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), AddFriendsOneActivity.class);
            }

        });
    }

    public void back(View v) {
        finish();
    }

    private int page = 1;

    public void getData() throws JSONException {
        JSONObject httpJson = new JSONObject();
        RequestParams params = new RequestParams();
        httpJson.put("id", MyApplication.getInstance().UserInfo.getUserId());
        httpJson.put("type", 1);
        //2017-03-25 客户修改不在传入mailList
//        httpJson.put("mailList", ContactsArray);
        httpJson.put("page", page);
        params.put("JsonString", httpJson);
        DBLog.e("params", params.toString());
        HttpUtil.post(Url.requests_all, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("通知与申请：", response.toString());
                    if (response.getInt("code") == 0) {
                        JSONArray JSONArray = response.getJSONArray("dataObject");
                        for (int i = 0; i < JSONArray.length(); i++) {
                            ApplyAndNoticeEntity ApplyAndNoticeEntity = new ApplyAndNoticeEntity();
                            JSONObject jsonObject = JSONArray.getJSONObject(i);
                            try {
                                ApplyAndNoticeEntity.setId(jsonObject.getInt("id"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ApplyAndNoticeEntity.setAuditedBy(jsonObject.getInt("auditedBy"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ApplyAndNoticeEntity.setAvatar(jsonObject.getString("avatar"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ApplyAndNoticeEntity.setCreatedTime(jsonObject.getString("createdTime"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ApplyAndNoticeEntity.setMessage(jsonObject.getString("message"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ApplyAndNoticeEntity.setNickname(jsonObject.getString("nickname"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ApplyAndNoticeEntity.setRequestType(jsonObject.getString("requestType"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ApplyAndNoticeEntity.setStatus(jsonObject.getString("status"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ApplyAndNoticeEntity.setTargetId(jsonObject.getInt("targetId"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ApplyAndNoticeEntity.setType(jsonObject.getString("type"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ApplyAndNoticeEntity.setUserId(jsonObject.getInt("userId"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            List.add(ApplyAndNoticeEntity);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }

    private int index = 0;

}

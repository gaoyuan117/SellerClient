package com.kaichaohulian.baocms.activity;

import android.util.Log;
import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.MyAlbumAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.circledemo.bean.HeadInfo;
import com.kaichaohulian.baocms.entity.MyAlbumEntity;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 相册
 * Created by ljl on 2016/12/1 0001.
 */
public class MyAlbumActivity extends BaseActivity {
    public static final String IS_FRIEND = "IS_FRIEND";
    public static final String FRIEND_ID = "FRIEND_ID";
    @BindView(R.id.listView)
    ListView listView;

    private List<MyAlbumEntity> List;
    private MyAlbumAdapter adapter;

    private boolean mIsFriend;
    private String mFriendId;

    @Override
    public void setContent() {
        setContentView(R.layout.myalbum_activity);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        addHttpData();
    }


    @Override
    public void initView() {
        setCenterTitle("相册");
        mIsFriend = getIntent().getBooleanExtra(IS_FRIEND, false);
        mFriendId = getIntent().getStringExtra(FRIEND_ID);

    }

    @Override
    public void initEvent() {
    }

    private int limit = 1;

    public void addHttpData() {
        List=new ArrayList<>();
        adapter=new MyAlbumAdapter(getActivity(),List);
        listView.setAdapter(adapter);
        RequestParams params = new RequestParams();
        if (mIsFriend) {
            params.put("id", mFriendId);
        } else {
            params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        }
        params.put("page", limit + "");
        HttpUtil.post(Url.MyAlbum, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {

                try {
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
                        String avatar = response.getString("avatar");
                        String nickName = response.getString("nikeName");
                        String backAvatar = response.getString("backAvatar");

                        HeadInfo headInfo = new HeadInfo();
                        headInfo.nickname = nickName;
                        headInfo.avatar = avatar;
                        headInfo.bg = backAvatar;
                        adapter.SetHeadInfo(headInfo);
                        JSONArray JSONArray = response.getJSONArray("experiences");
                        for (int i = 0; i < JSONArray.length(); i++) {
                            JSONObject JSONObject = JSONArray.getJSONObject(i);
                            MyAlbumEntity MyAlbumEntity = new MyAlbumEntity();
                            MyAlbumEntity.setCreateTime(JSONObject.getString("createdTime"));
                            MyAlbumEntity.setContent(JSONObject.getString("content"));
                            List<String> list = new ArrayList<String>();

                            String photos = JSONObject.getString("images");
                            photos = photos.substring(0, photos.length());
                            if (photos != null && !photos.equals("null")) {
                                JSONArray imageArray = new JSONArray(photos);
                                for (int j = 0; j < imageArray.length(); j++) {
                                    list.add(imageArray.getString(j));
                                }
                            }
                            MyAlbumEntity.setList(list);
                            Log.e(TAG, "onSuccess: "+MyAlbumEntity.toString() );
                            List.add(MyAlbumEntity);
                        }

                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    showToastMsg("数据解析异常...");
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
                ShowDialog.dissmiss();
            }
        });
    }

}

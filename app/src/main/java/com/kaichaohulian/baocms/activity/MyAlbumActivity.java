package com.kaichaohulian.baocms.activity;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.MyAlbumAdapter;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.circledemo.bean.HeadInfo;
import com.kaichaohulian.baocms.entity.AblumEntity;
import com.kaichaohulian.baocms.entity.MyAlbumEntity;
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

    private List<AblumEntity.ExperiencesBean> List;
    private MyAlbumAdapter adapter;
    private View headView;
    public ImageView head;
    public TextView name;
    public ImageView create;
    private boolean mIsFriend;
    private String mFriendId;
    private int index = 1;

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

    public void addHttpData() {
        List = new ArrayList<>();

        RetrofitClient.getInstance().createApi().GetUserPhoto(MyApplication.getInstance().UserInfo.getUserId(), index + "")
                .compose(RxUtils.<HttpResult<AblumEntity>>io_main())
                .subscribe(new BaseObjObserver<AblumEntity>(getActivity(), "获取中...") {
                    @Override
                    protected void onHandleSuccess(AblumEntity ablumEntity) {
                        List.addAll(ablumEntity.experiences);
                        if (headView == null) {
                            headView = View.inflate(getActivity(), R.layout.head_circle, null);
                            head = (ImageView) headView.findViewById(R.id.head);
                            name = (TextView) headView.findViewById(R.id.name);
                            create = (ImageView) headView.findViewById(R.id.create_photo);
                            ImageView bg = (ImageView) headView.findViewById(R.id.head_bg);
                            name.setText(ablumEntity.nikeName);
                            create.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityUtil.next(getActivity(), ReleaseTalkActivity.class);
                                }
                            });
                            Glide.with(getActivity()).load(ablumEntity.avatar).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.album_bg).into(head);
                            Glide.with(getActivity()).load(ablumEntity.backAvatar).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.album_bg).into(bg);
                        }
                        listView.addHeaderView(headView);
                        adapter = new MyAlbumAdapter(MyAlbumActivity.this, List);
                        listView.setAdapter(adapter);
                    }
                });

    }

}

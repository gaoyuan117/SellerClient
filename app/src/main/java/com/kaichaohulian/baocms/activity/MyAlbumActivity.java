package com.kaichaohulian.baocms.activity;

import android.content.Intent;
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
import com.kaichaohulian.baocms.entity.AblumEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

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
    private int mFriendId;
    private int index = 1;
    private boolean firstLoad = true;

    @Override
    public void setContent() {
        setContentView(R.layout.myalbum_activity);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        List = new ArrayList<>();
        String url="";
        mIsFriend = getIntent().getBooleanExtra(IS_FRIEND, false);
        try{
            mFriendId = getIntent().getIntExtra(FRIEND_ID,0);
        }catch (ClassCastException e){
            url=getIntent().getStringExtra(FRIEND_ID);
        }
        if(mFriendId==0){
            mFriendId=MyApplication.getInstance().UserInfo.getUserId();
        }
        addHttpData();
    }


    @Override
    public void initView() {
        setCenterTitle("相册");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            addHttpData();
        }
    }

    @Override
    public void initEvent() {

    }

    private void dealwitdData(AblumEntity ablumEntity) {
        List.clear();
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
            Glide.with(getActivity()).load(ablumEntity.avatar).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.default_useravatar).into(head);
//            Glide.with(getActivity()).load(ablumEntity.backAvatar).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.album_bg).into(bg);
            listView.addHeaderView(headView);
            if(getIntent().getStringExtra("excuseMe")!=null){
                adapter = new MyAlbumAdapter(MyAlbumActivity.this, List,false);
            }else{
                adapter = new MyAlbumAdapter(MyAlbumActivity.this, List,true);
            }
            listView.setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
    }

    public void addHttpData() {

        RetrofitClient.getInstance().createApi().GetUserPhoto(mFriendId, index + "")
                .compose(RxUtils.<HttpResult<AblumEntity>>io_main())
                .subscribe(new BaseObjObserver<AblumEntity>(getActivity(), "获取中...") {
                    @Override
                    protected void onHandleSuccess(AblumEntity ablumEntity) {
                        dealwitdData(ablumEntity);
                        firstLoad = false;
                    }
                });

    }

}
package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.GreetAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.GreetBean;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GreetActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener {


    @BindView(R.id.img_greet_back)
    ImageView imgGreetBack;
    @BindView(R.id.img_greet_clear)
    TextView imgGreetClear;
    @BindView(R.id.rv_greet)
    RecyclerView mRecyclerView;

    private GreetAdapter mAdapter;
    private List<GreetBean.RequestDTOs1Bean> mList;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_greet);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        loadGreetList();
    }

    @Override
    public void initView() {
        mList = new ArrayList<>();
        mAdapter = new GreetAdapter(R.layout.item_greet, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initEvent() {
        mAdapter.setOnItemChildClickListener(this);
    }

    @OnClick({R.id.img_greet_back, R.id.img_greet_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_greet_back:
                finish();
                break;
            case R.id.img_greet_clear://清空

                break;
        }
    }

    private void loadGreetList() {
        map.put("id", MyApplication.getInstance().UserInfo.getUserId() + "");
        map.put("page", "1");
        RetrofitClient.getInstance().createApi().greetList(map)
                .compose(RxUtils.<HttpResult<GreetBean>>io_main())
                .subscribe(new BaseObjObserver<GreetBean>(this, false) {
                    @Override
                    protected void onHandleSuccess(GreetBean greetBean) {
                        if (greetBean.getRequestDTOs1() != null) {
                            mList.clear();
                            mList.addAll(greetBean.getRequestDTOs1());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void agreeAdd(int requstId) {
        Map<String, Object> maps = new HashMap<>();
        maps.put("id", MyApplication.getInstance().UserInfo.getUserId());
        maps.put("requestId", requstId);
        maps.put("action", "ACCEPT");
        maps.put("addFriend", true);
        RetrofitClient.getInstance().createApi().handlerApplication(maps)
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(this) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        ToastUtil.showMessage("添加成功");
                        loadGreetList();
                    }
                });
    }


    @Override
    public boolean onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        agreeAdd(mList.get(position).getTargetId());
        return false;
    }


}

package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.AdverAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.HasGetAdverBean;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.util.TitleUtils;
import com.kaichaohulian.baocms.view.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class AdverActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.tv_adver_notify_info)
    TextView tvAdverNotifyInfo;
    @BindView(R.id.img_adver_close_notify)
    ImageView imgAdverCloseNotify;
    @BindView(R.id.ll_adver_notify)
    LinearLayout llAdverNotify;
    @BindView(R.id.rv_adver)
    RecyclerView mRecyclerView;

    private List<HasGetAdverBean.AdvertListBean> mList;
    private AdverAdapter mAdapter;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_adver);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAdverList(1);

    }

    @Override
    public void initView() {
        new TitleUtils(this).setTitle("广告信息");
        mList = new ArrayList<>();
        mAdapter = new AdverAdapter(R.layout.item_adver, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new RecyclerViewDivider(
                this, LinearLayoutManager.VERTICAL, 10, ContextCompat.getColor(this, R.color.bg_color_gray)));
    }

    @Override
    public void initEvent() {
        mAdapter.setOnItemClickListener(this);
    }


    @OnClick({R.id.img_adver_close_notify, R.id.rv_adver})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_adver_close_notify:
                llAdverNotify.setVisibility(View.GONE);
                break;
            case R.id.rv_adver:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this, AdverDetailActivity.class);
        intent.putExtra("adverId", mList.get(position).getId() + "");
        startActivity(intent);
    }

    private void getAdverList(int page) {
        map.put("userId", MyApplication.getInstance().UserInfo.getUserId() + "");
        map.put("page", page + "");
        RetrofitClient.getInstance().createApi().hasGetAdver(map)
                .compose(RxUtils.<HttpResult<HasGetAdverBean>>io_main())
                .subscribe(new BaseObjObserver<HasGetAdverBean>(this, "加载中") {
                    @Override
                    protected void onHandleSuccess(HasGetAdverBean hasGetAdverBean) {
                        if (hasGetAdverBean.getAdvertList() == null) {
                            return;
                        }
                        mList.clear();
                        mList.addAll(hasGetAdverBean.getAdvertList());
                        mAdapter.notifyDataSetChanged();
                        tvAdverNotifyInfo.setText("您有" + hasGetAdverBean.getNoReadCount() + "条未读消息");
                    }
                });
    }
}

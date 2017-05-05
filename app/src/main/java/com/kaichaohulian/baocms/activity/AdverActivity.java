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
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.view.RecyclerViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdverActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.tv_adver_notify_info)
    TextView tvAdverNotifyInfo;
    @BindView(R.id.img_adver_close_notify)
    ImageView imgAdverCloseNotify;
    @BindView(R.id.ll_adver_notify)
    LinearLayout llAdverNotify;
    @BindView(R.id.rv_adver)
    RecyclerView mRecyclerView;

    private List<String> mList;
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
    public void initView() {
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add("");
        }
        mAdapter = new AdverAdapter(R.layout.item_adver,mList);
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
                break;
            case R.id.rv_adver:
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(this,AdverDetailActivity.class);
        startActivity(intent);
    }
}

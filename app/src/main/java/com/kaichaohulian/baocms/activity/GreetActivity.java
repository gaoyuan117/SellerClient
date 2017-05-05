package com.kaichaohulian.baocms.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.GreetAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GreetActivity extends BaseActivity {


    @BindView(R.id.img_greet_back)
    ImageView imgGreetBack;
    @BindView(R.id.img_greet_clear)
    TextView imgGreetClear;
    @BindView(R.id.rv_greet)
    RecyclerView mRecyclerView;

    private GreetAdapter mAdapter;
    private List<String> mList;


    @Override
    public void setContent() {
        setContentView(R.layout.activity_greet);
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
        mAdapter = new GreetAdapter(R.layout.item_greet, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initEvent() {

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
}

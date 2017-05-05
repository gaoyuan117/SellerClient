package com.kaichaohulian.baocms.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.NearbyListAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.util.TitleUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearbyListActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rv_nearby_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_near_list)
    LinearLayout linearLayout;

    private NearbyListAdapter mAdapter;
    private List<String> mList;
    private TextView tvGirl, tvBoy, tvAll, tvGreet, tvClear, tvCancel;
    private LinearLayout popLayout;
    private PopupWindow popupWindow;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_nearby_list);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        new TitleUtils(this).setTitle("附近的人")
                .showRight()
                .setRightListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openBottomPopWindow();
                    }
                });
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add("");
        }
        mAdapter = new NearbyListAdapter(R.layout.item_neayby_list, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initEvent() {

    }

    private void openBottomPopWindow() {
        View view = View.inflate(this, R.layout.pop_nearby_list, null);
        popLayout = (LinearLayout) view.findViewById(R.id.ll_pop_near_out);
        tvGirl = (TextView) view.findViewById(R.id.ll_pop_near_girl);
        tvBoy = (TextView) view.findViewById(R.id.ll_pop_near_boy);
        tvAll = (TextView) view.findViewById(R.id.ll_pop_near_all);
        tvGreet = (TextView) view.findViewById(R.id.ll_pop_near_greet);
        tvClear = (TextView) view.findViewById(R.id.ll_pop_near_clear);
        tvCancel = (TextView) view.findViewById(R.id.ll_pop_near_cancel);

        popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(linearLayout, Gravity.BOTTOM, 0, 0);

        popLayout.setOnClickListener(this);
        tvAll.setOnClickListener(this);
        tvBoy.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvGirl.setOnClickListener(this);
        tvGreet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_pop_near_out:
                popupWindow.dismiss();
                break;
            case R.id.ll_pop_near_girl:
                ToastUtil.showMessage("只看女生");
                break;
            case R.id.ll_pop_near_boy:
                ToastUtil.showMessage("只看男生");
                break;
            case R.id.ll_pop_near_all:
                ToastUtil.showMessage("全部查看");
                break;
            case R.id.ll_pop_near_greet:
                ToastUtil.showMessage("附近打招呼的人");
                break;
            case R.id.ll_pop_near_clear:
                ToastUtil.showMessage("清空位置信息");
                break;
            case R.id.ll_pop_near_cancel:
                popupWindow.dismiss();
                break;
        }
    }
}

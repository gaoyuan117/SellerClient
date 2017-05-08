package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.NearbyListAdapter;
import com.kaichaohulian.baocms.app.AppManager;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.NearbyBean;
import com.kaichaohulian.baocms.entity.UserInfoBean;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.util.TitleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class NearbyListActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.rv_nearby_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.rl_near_list)
    LinearLayout linearLayout;

    private NearbyListAdapter mAdapter;
    private List<NearbyBean> mList;
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
        mAdapter = new NearbyListAdapter(R.layout.item_neayby_list, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initEvent() {
        getNearUser(1, 2);
        mAdapter.setOnItemClickListener(this);
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
                break;
            case R.id.ll_pop_near_girl:
                getNearUser(1, 1);
                break;
            case R.id.ll_pop_near_boy:
                getNearUser(1, 0);
                break;
            case R.id.ll_pop_near_all:
                getNearUser(1, 2);
                break;
            case R.id.ll_pop_near_greet:
                startActivity(new Intent(this, GreetActivity.class));
                break;
            case R.id.ll_pop_near_clear:
                clearLocation();
                break;
            case R.id.ll_pop_near_cancel:
                popupWindow.dismiss();
                break;
        }
        popupWindow.dismiss();
    }


    private void getNearUser(int page, int sex) {
        Map<String, String> map = new HashMap<>();
        map.put("id", MyApplication.getInstance().UserInfo.getUserId() + "");
        map.put("longitud", MyApplication.lng);
        map.put("latitude", MyApplication.lat);
        map.put("page", page + "");
        map.put("sex", sex + "");//0 男 1女 2全部
        RetrofitClient.getInstance().createApi().getNearUser(map)
                .compose(RxUtils.<HttpArray<NearbyBean>>io_main())
                .subscribe(new BaseListObserver<NearbyBean>(this, "加载中") {
                    @Override
                    protected void onHandleSuccess(List<NearbyBean> list) {
                        mList.clear();
                        mList.addAll(list);
                        mAdapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(getActivity(),FriendInfoActivity.class);
        intent.putExtra("phone",mList.get(position).getPhone()+"");
        intent.putExtra("friendId",mList.get(position).getUserId()+"");
        intent.putExtra("type","3");
        startActivity(intent);
    }

    /**
     * 清空位置信息
     */
    private void clearLocation() {
        RetrofitClient.getInstance().createApi()
                .clearLocation(MyApplication.getInstance().UserInfo.getUserId())
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(this, "清除信息中") {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        ToastUtil.showMessage("清除位置信息成功");
                        AppManager.getAppManager().finishActivity(NearbyZyActivity.class);
                        finish();
                    }
                });
    }

}

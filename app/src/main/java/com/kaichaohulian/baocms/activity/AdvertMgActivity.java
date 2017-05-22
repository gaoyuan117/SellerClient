package com.kaichaohulian.baocms.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.AdvertmasslistAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.AdviertisementEntity;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvertMgActivity extends BaseActivity {


    @BindView(R.id.lv_advertmanager)
    ListView lvAdvertmanager;
    private int index = 1;
    private List<AdviertisementEntity.AdvertListBean> DataList;
    private AdvertmasslistAdapter adapter;


    @Override
    public void setContent() {
        setContentView(R.layout.activity_advert_mg);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        map = new HashMap<>();
        DataList = new ArrayList<>();
        adapter = new AdvertmasslistAdapter(getActivity(), DataList);
        adapter.setLayoutIds(R.layout.item_advertmasslist);
        lvAdvertmanager.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        map.put("userId", MyApplication.getInstance().UserInfo.getUserId() + "");
        map.put("page", index + "");
        RetrofitClient.getInstance().createApi().GetMyadviertisement(map)
                .compose(RxUtils.<HttpResult<AdviertisementEntity>>io_main())
                .subscribe(new BaseObjObserver<AdviertisementEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(AdviertisementEntity adviertisementEntity) {
                        if (adviertisementEntity.advertList != null) {
                            DataList.clear();
                            DataList.addAll(adviertisementEntity.advertList);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(AdvertMgActivity.this, "暂无广告", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
        ;
    }

    @Override
    public void initView() {
        setCenterTitle("广告管理");
    }

    @Override
    public void initEvent() {
        lvAdvertmanager.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), AdverDetailActivity.class);
                intent.putExtra("adverId", DataList.get(i).id + "");
                startActivity(intent);

            }
        });
        lvAdvertmanager.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final String[] cities = {"删除",};
                //    设置一个下拉的列表选择项
                final String advertId = DataList.get(i).id + "";

                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeleteAdvert(advertId, i);
                    }
                });
                builder.show();
                return false;
            }
        });
    }

    private void DeleteAdvert(String advertId, final int position) {
        RetrofitClient.getInstance().createApi().DeleteAdvert(MyApplication.getInstance().UserInfo.getUserId(), Long.parseLong(advertId))
                .compose(RxUtils.<HttpResult<CommonEntity>>io_main())
                .subscribe(new BaseObjObserver<CommonEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(CommonEntity commonEntity) {
                        DataList.remove(position);
                        Toast.makeText(AdvertMgActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    }
                });
    }

}

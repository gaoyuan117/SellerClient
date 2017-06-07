package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.AdvertMassSelectAdapter;
import com.kaichaohulian.baocms.adapter.AdvertmasslistAdapter;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.AdviertisementEntity;
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
import butterknife.OnClick;

public class AdvertmassActivity extends BaseActivity {


    @BindView(R.id.lv_advertmass)
    ListView lvAdvertmass;
    private AdvertmasslistAdapter adapter;
    private ArrayList<AdviertisementEntity.AdvertListBean> Datalist;
    private int index = 1;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_advertmass);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        map = new HashMap<>();
        Datalist = new ArrayList<>();
        adapter = new AdvertmasslistAdapter(getActivity(), Datalist, R.layout.item_advertmasslist);
        lvAdvertmass.setAdapter(adapter);

        lvAdvertmass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), AdvertDetail.class);
                intent.putExtra("advertId", Datalist.get(i).id);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        map.put("userId", MyApplication.getInstance().UserInfo.getUserId() + "");
        map.put("page", 1 + "");
        RetrofitClient.getInstance().createApi().GetMyadviertisement(map)
                .compose(RxUtils.<HttpResult<AdviertisementEntity>>io_main())
                .subscribe(new BaseObjObserver<AdviertisementEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(AdviertisementEntity adviertisementEntity) {
                        if (adviertisementEntity.advertList != null) {
                            Datalist.clear();
                            Datalist.addAll(adviertisementEntity.advertList);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(AdvertmassActivity.this, "暂无广告", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void initView() {
        setCenterTitle("群发广告");

    }

    @Override
    public void initEvent() {

    }

    @OnClick({R.id.ll_friend_advertmass, R.id.ll_other_advertmass})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_friend_advertmass:
                Intent intent = new Intent(this, AdvertMassSelectActivity.class);
                intent.putExtra("JustSelect", false);
                startActivity(intent);
//                ActivityUtil.next(this, AdvertMassSelectActivity.class);
                break;
            case R.id.ll_other_advertmass:
                ActivityUtil.next(this, AdvertOtherActivity.class);
                break;
        }
    }
}

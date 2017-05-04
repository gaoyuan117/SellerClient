package com.kaichaohulian.baocms.activity;

import android.widget.ListView;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.AdvertmasslistAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.AdviertisementEntity;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvertMgActivity extends BaseActivity {


    @BindView(R.id.lv_advertmanager)
    ListView lvAdvertmanager;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_advert_mg);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        map=new HashMap<>();
        map.put("userId", MyApplication.getInstance().UserInfo.getUserId()+"");
        map.put("page", "1");
        RetrofitClient.getInstance().createApi().GetMyadviertisement(map)
                .compose(RxUtils.<HttpArray<AdviertisementEntity>>io_main())
                .subscribe(new BaseListObserver<AdviertisementEntity>(getActivity(),"获取广告中...") {
                    @Override
                    protected void onHandleSuccess(List<AdviertisementEntity> list) {
                        if(list!=null){
                            AdvertmasslistAdapter adapter=new AdvertmasslistAdapter(getActivity(),list);
                            adapter.setLayoutIds(R.layout.item_advertmasslist);
                            lvAdvertmanager.setAdapter(adapter);
                        }else{
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

    }


}

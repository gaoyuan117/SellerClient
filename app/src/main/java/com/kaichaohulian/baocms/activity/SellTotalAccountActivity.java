package com.kaichaohulian.baocms.activity;

import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.SellTotalAccountAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.SellEntity;

import java.util.ArrayList;
import java.util.List;

public class SellTotalAccountActivity extends BaseActivity {

    ListView listView;
    List<SellEntity>  data;
    SellTotalAccountAdapter adapter;

    @Override
    public void setContent() {
        setCenterTitle("查看总销售额");
        setContentView(R.layout.activity_sell_total_account);
    }

    @Override
    public void initData() {
        data = new ArrayList<SellEntity>();
        testData();
        adapter = new SellTotalAccountAdapter(getApplicationContext(),data);
    }

    @Override
    public void initView() {

        listView = getId(R.id.sell_list);
        listView.setAdapter(adapter);
    }

    @Override
    public void initEvent() {

    }
    public void testData(){

        for (int i = 0; i<= 20; i++){
            SellEntity entity = new SellEntity();
            entity.setName("2016款打款裤子羽绒腹部身裹长习");
            entity.setDate("11月12日 13：00");
            entity.setIncome(56.67);
            data.add(entity);
        }
    }

}

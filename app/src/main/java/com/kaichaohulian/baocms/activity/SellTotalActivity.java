package com.kaichaohulian.baocms.activity;

import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.SellTotalAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.SellEntity;

import java.util.ArrayList;
import java.util.List;

public class SellTotalActivity extends BaseActivity {

    ListView listView;
    TextView mAccount;
    List<SellEntity> data;
    SellTotalAdapter adapter;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_sell_total);
        setCenterTitle("销售统计");
    }

    @Override
    public void initData() {

        data = new ArrayList<SellEntity>();
        testData();
        adapter = new SellTotalAdapter(getApplicationContext(),data);
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

package com.kaichaohulian.baocms.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.WhoCanSeeMeListAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;

public class WhoCanSeeMeActivity extends BaseActivity {


    ListView listView;
    WhoCanSeeMeListAdapter adapter;
    String rightOption[];
    String rightOptionTip[];



    @Override
    public void setContent() {
        setContentView(R.layout.who_can_see_me);

    }

    @Override
    public void initData() {
        rightOption = getResources().getStringArray(R.array.right_option);
        rightOptionTip = getResources().getStringArray(R.array.right_option_tip);
        adapter = new WhoCanSeeMeListAdapter(rightOption, rightOptionTip, getApplicationContext());




    }

    @Override
    public void initView() {
        listView = getId(R.id.who_see_me_listview);
        setCenterTitle("谁可以看我");

    }

    @Override
    public void initEvent() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WhoCanSeeMeListAdapter.selected = position;
                adapter.notifyDataSetChanged();
            }
        });


    }
}

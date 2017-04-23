package com.kaichaohulian.baocms.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.SmallVideoNetChooseAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;

public class SmallVideoNetChooseActivity extends BaseActivity {

    ListView listView;
    SmallVideoNetChooseAdapter adapter;
    String[] choice;

    @Override
    public void setContent() {
        setContentView(R.layout.small_video_settings_layout);

    }

    @Override
    public void initData() {
        choice = getResources().getStringArray(R.array.small_video_choice);
        adapter = new SmallVideoNetChooseAdapter(choice,getApplicationContext());
    }

    @Override
    public void initView() {
        listView = getId(R.id.small_video_list);
        setCenterTitle("朋友圈小视频");


    }

    @Override
    public void initEvent() {
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.flag = position;
                adapter.notifyDataSetChanged();
            }
        });
    }
}

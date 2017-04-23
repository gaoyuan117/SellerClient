package com.kaichaohulian.baocms.activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

import java.util.List;
import java.util.Map;

/**
 * 信息列表
 * Created by ljl on 2016/12/14 0014.
 */

public class InformationListActivity extends BaseActivity {

    private ListView listView;

    private List<Map<Integer, String>> list;

    private BaseAdapter adapter;

    @Override
    public void setContent() {
        setContentView(R.layout.informationlist_activity);
    }

    @Override
    public void initData() {
        adapter = new BaseAdapter();
    }

    @Override
    public void initView() {
        listView = getId(R.id.listView);

        listView.setAdapter(adapter);
    }

    @Override
    public void initEvent() {

    }

    public class BaseAdapter extends android.widget.BaseAdapter{

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.informationlist_listitem, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();

            return convertView;
        }

        public class ViewHolder{

            public ViewHolder(View convertView){
                title = (TextView) convertView.findViewById(R.id.title);
                time = (TextView) convertView.findViewById(R.id.time);
            }

            TextView title;
            TextView time;
        }

    }
}

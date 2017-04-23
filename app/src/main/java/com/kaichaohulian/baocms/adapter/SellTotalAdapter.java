package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.SellEntity;

import java.util.List;

/**
 * Created by liuyu on 2017/1/14.
 */

public class SellTotalAdapter extends BaseAdapter {
    Context context;
    List<SellEntity> data;
    LayoutInflater inflater;

    public SellTotalAdapter(Context context, List<SellEntity> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.sell_total_list_item,null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.goods_name);
            viewHolder.date = (TextView) convertView.findViewById(R.id.sell_date_time);
            viewHolder.income = (TextView) convertView.findViewById(R.id.sell_per_income);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.date.setText(data.get(position).getDate());
        viewHolder.income.setText("+"+data.get(position).getIncome());
        viewHolder.name.setText(data.get(position).getName());
        return convertView;
    }

    class ViewHolder{
        ImageView head;
        TextView  name;
        TextView  date;
        TextView  time;
        TextView  income;
    }
}
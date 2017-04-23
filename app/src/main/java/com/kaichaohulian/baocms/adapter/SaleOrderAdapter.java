package com.kaichaohulian.baocms.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.SaleOrder;

import java.util.List;

/**
 * Created by kenton on 2017/2/9.
 */

public class SaleOrderAdapter extends ArrayAdapter<SaleOrder> {

    List<SaleOrder> userList;
    private LayoutInflater layoutInflater;
    private int res;

    @SuppressLint("SdCardPath")
    public SaleOrderAdapter(Context context, int resource, List<SaleOrder> objects) {
        super(context, resource, objects);
        this.res = resource;
        this.userList = objects;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(res, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.tv_title);
        TextView fee = (TextView) convertView.findViewById(R.id.txt_sale_fee);
        TextView time = (TextView) convertView.findViewById(R.id.txt_sale_time);

        SaleOrder order = getItem(position);
        if (order != null) {

            title.setText(order.getGoodName());
            fee.setText(order.getMoney());
            time.setText(order.getCreateTime());

        }

        return convertView;
    }

    @Override
    public SaleOrder getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }
}

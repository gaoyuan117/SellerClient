package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;

/**
 * Created by liuyu on 2016/12/24.
 */

public class RechargeGridViewAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    String[] markPrice;
    String[] sellPrice;

    public RechargeGridViewAdapter(Context context, String[] markPrice, String[] sellPrice) {
        this.context = context;
        this.markPrice = markPrice;
        this.sellPrice = sellPrice;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return markPrice.length;
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

        View view = inflater.inflate(R.layout.recharge_gridview_item, null);
        TextView txtMarkPrice = (TextView) view.findViewById(R.id.recharge_price_biaojia);
        TextView txtSellPrice = (TextView) view.findViewById(R.id.recharge_price_shoujia);
        txtMarkPrice.setText(markPrice[position]);
        txtSellPrice.setText(sellPrice[position]);
        return view;
    }

}

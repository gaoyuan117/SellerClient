package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.WithdrawDetailEntity;

import java.util.List;

/**
 * Created by liuyu on 2017/1/16.
 */

public class WithdrawDetailAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<WithdrawDetailEntity> data;

    public WithdrawDetailAdapter(Context context, List<WithdrawDetailEntity> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.withdraw_detail_list_item,null);
            viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.img_bank_icon);
            viewHolder.account = (TextView) convertView.findViewById(R.id.tv_account);
            viewHolder.dateAndTime = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.lastFour = (TextView) convertView.findViewById(R.id.tv_tixian_weihao);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.lastFour.setText(data.get(position).getBankNum());
        viewHolder.dateAndTime.setText(data.get(position).getAddtime());
        viewHolder.account.setText(data.get(position).getMoney()+"");

        return convertView;
    }

    class ViewHolder{
        ImageView imgIcon;
        TextView  lastFour;
        TextView  dateAndTime;
        TextView  account;
    }
}

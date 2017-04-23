package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.ScanListEntity;
import com.kaichaohulian.baocms.utils.DateUtil;

import java.util.List;

/**
 * Created by liuyu on 2017/1/16.
 */

public class PayedAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<ScanListEntity> data;

    public PayedAdapter(Context context, List<ScanListEntity> data) {
        this.context = context;
        this.data = data;
        inflater  = LayoutInflater.from(context);
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
            convertView = inflater.inflate(R.layout.scan_list_item_pay,null);
            viewHolder.tvAccount = (TextView) convertView.findViewById(R.id.sell_per_income);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.goods_name);
            viewHolder.tvNumber = (TextView) convertView.findViewById(R.id.number);
            viewHolder.tvStatus = (TextView) convertView.findViewById(R.id.tv_if_payed);
            viewHolder.tvDateAndTime = (TextView) convertView.findViewById(R.id.sell_date_time);
            convertView.setTag(viewHolder);
        }else {
            viewHolder  = (ViewHolder) convertView.getTag();
        }
        ScanListEntity item = data.get(position);

        viewHolder.tvAccount.setText("￥"+data.get(position).getAmount());
        viewHolder.tvDateAndTime.setText("创建时间："+ DateUtil.getCurrDateWithFormat("yyyyMMdd HHmmss",item.getCreateTime()));

        return convertView;
    }
    class ViewHolder{
        ImageView imgIcon;
        TextView   tvName;
        TextView   tvAccount;
        TextView   tvDateAndTime;
        TextView   tvStatus;
        TextView   tvNumber;
    }
}

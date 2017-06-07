package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.PhoneMsgDetailEntity;
import com.kaichaohulian.baocms.entity.WithDrawMsgEntity;
import com.kaichaohulian.baocms.util.Utils;

import java.util.List;

public class PhoneMsgDetailAdapter extends BaseAdapter {
    public List<PhoneMsgDetailEntity> mList;
    private Context mContext;
    LayoutInflater inflater;

    public PhoneMsgDetailAdapter(Context context, List<PhoneMsgDetailEntity> list) {
        this.mList = list;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }


    @Override
    public PhoneMsgDetailEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_withdrawals_detail, null);
            viewHolder.msgTime = (TextView) convertView.findViewById(R.id.withdraw_msg_time);
            viewHolder.msgContent = (TextView) convertView.findViewById(R.id.withdraw_msg_content);
            viewHolder.headTime = (TextView) convertView.findViewById(R.id.tixian_time);
            viewHolder.status = (TextView) convertView.findViewById(R.id.tv_tixian_status);
            viewHolder.money = (TextView) convertView.findViewById(R.id.withdraw_msg_money);
            viewHolder.type = (TextView) convertView.findViewById(R.id.withdraw_msg_type);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PhoneMsgDetailEntity bean = mList.get(position);
        if (bean.getStatus().equals("0")) {
            viewHolder.status.setText("充值失败");
        } else {
            viewHolder.status.setText("充值成功");
        }
        viewHolder.msgTime.setText(Utils.stampToDate(mList.get(position).getTimeStamp() + ""));
        viewHolder.money.setText("充值金额："+bean.getAmount()+"元");
        if (mList.get(position).getActualamount() != null && !mList.get(position).getActualamount().equals("null")) {
            viewHolder.msgContent.setText(mList.get(position).getPhoneNumber() + "已经成功充值" + mList.get(position).getActualamount() + "元");
        }
        return convertView;
    }

    class ViewHolder {
        TextView msgTime,headTime,status,money,type;
        TextView msgContent;
    }
}

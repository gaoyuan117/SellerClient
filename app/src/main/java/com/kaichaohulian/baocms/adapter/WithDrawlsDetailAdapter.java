package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.WithDrawMsgEntity;
import com.kaichaohulian.baocms.util.Utils;

import java.util.List;

public class WithDrawlsDetailAdapter extends BaseAdapter {
    public List<WithDrawMsgEntity> mList;
    private Context mContext;
    LayoutInflater inflater;

    public WithDrawlsDetailAdapter(Context context, List<WithDrawMsgEntity> list) {
        this.mList = list;
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }


    @Override
    public WithDrawMsgEntity getItem(int position) {
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
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.msgTime.setText(Utils.stampToDate(mList.get(position).getAddtime() + ""));
        if (mList.get(position).getWeixinAccount() != null && !mList.get(position).getWeixinAccount().equals("null")) {
            viewHolder.msgContent.setText(mList.get(position).getWeixinAccount() + "提现" + mList.get(position).getMoney() + "元");
        } else if (mList.get(position).getZfbAccount() != null && !mList.get(position).getZfbAccount().equals("null")) {
            viewHolder.msgContent.setText(mList.get(position).getZfbAccount() + "提现" + mList.get(position).getMoney() + "元");
        } else if (mList.get(position).getBankName() != null && !mList.get(position).getBankName().equals("null")) {
            viewHolder.msgContent.setText(mList.get(position).getBankName() + "提现" + mList.get(position).getMoney() + "元");
        }
        return convertView;
    }

    class ViewHolder {
        TextView msgTime;
        TextView msgContent;
    }
}

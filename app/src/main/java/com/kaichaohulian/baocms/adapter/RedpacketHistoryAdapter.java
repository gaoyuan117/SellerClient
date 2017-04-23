package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.RedpacketHistory;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.math.BigDecimal;
import java.util.List;

public class RedpacketHistoryAdapter extends BaseAdapter {
    List<RedpacketHistory> mData = null;
    Context mContext;

    public RedpacketHistoryAdapter(List<RedpacketHistory> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RedpacketHistory redpacketHistory = mData.get(position);
        ViewHolder viewHolder = null;
        View view;
        if (convertView == null) {
            view = View.inflate(mContext, R.layout.item_redpacket_history, null);
            viewHolder = new ViewHolder();
            viewHolder.nameTV = (TextView) view.findViewById(R.id.sent_redbag_bagtype);
            viewHolder.amountTV = (TextView) view.findViewById(R.id.sent_redbag_item_account);
            viewHolder.timeTV = (TextView) view.findViewById(R.id.sent_redbag_item_date);
            viewHolder.avatarIV = (ImageView) view.findViewById(R.id.riv_redpacket_history);
        } else {
            view = convertView;
        }

        viewHolder.nameTV.setText(redpacketHistory.name);

        BigDecimal bd = new BigDecimal(redpacketHistory.useracount);
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        viewHolder.amountTV.setText(bd.toString());

        viewHolder.timeTV.setText(redpacketHistory.time);
        ImageLoader.getInstance().displayImage(redpacketHistory.avatar, viewHolder.avatarIV);
        return view;
    }

    class ViewHolder {
        TextView nameTV;
        TextView amountTV;
        TextView timeTV;
        ImageView avatarIV;
    }

}

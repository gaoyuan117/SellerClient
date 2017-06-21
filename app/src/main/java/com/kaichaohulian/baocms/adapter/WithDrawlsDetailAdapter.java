package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.text.TextUtils;
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
            viewHolder.headTime = (TextView) convertView.findViewById(R.id.tixian_time);
            viewHolder.status = (TextView) convertView.findViewById(R.id.tv_tixian_status);
            viewHolder.money = (TextView) convertView.findViewById(R.id.withdraw_msg_money);
            viewHolder.no = (TextView) convertView.findViewById(R.id.withdraw_msg_no);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        WithDrawMsgEntity bean = mList.get(position);
        if (bean.getStatus() == 2) {
            viewHolder.status.setText("提现失败");
        } else if (bean.getStatus() == 1) {
            viewHolder.status.setText("提现成功");
        } else if (bean.getStatus() == 0) {
            viewHolder.status.setText("提现处理中");
        } else{
            viewHolder.status.setText("提现成功");
        }

        viewHolder.msgTime.setText(bean.getAddtime());
        viewHolder.money.setText("提现金额：" + bean.getMoney() + "元");
        String addtime = bean.getAddtime();
        String[] split = addtime.split(" ");
        viewHolder.headTime.setText(split[0]);


        if (mList.get(position).getWeixinAccount() != null && !mList.get(position).getWeixinAccount().equals("null")) {
            viewHolder.no.setText("到账微信号：" + bean.getWeixinAccount());
        } else if (mList.get(position).getZfbAccount() != null && !mList.get(position).getZfbAccount().equals("null")) {
            viewHolder.no.setText("到账支付宝号：" + bean.getZfbAccount());
        } else if (mList.get(position).getBankName() != null && !mList.get(position).getBankName().equals("null")) {
            String bankNum = bean.getBankNum();
            String substring = bankNum.substring(bankNum.length() - 4, bankNum.length());
            viewHolder.no.setText("到账卡号：" + bean.getBankName() + "(" + substring + ")");
        }
        if (!TextUtils.isEmpty(bean.getReason()) && !bean.getReason().equals("null")) {
            viewHolder.msgContent.setText("备注：" + bean.getReason());
        } else {
            viewHolder.msgContent.setText("备注：");
        }

        return convertView;
    }

    class ViewHolder {
        TextView msgTime, headTime, money, no, status;
        TextView msgContent;
    }
}

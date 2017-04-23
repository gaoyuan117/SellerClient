package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.SmallMoneyBean;

import java.util.List;


/**
 * Created by liuyu on 2016/12/15.
 */

public class SmallMoneyAdapter extends BaseAdapter {
    private Context mContext;
    private List<SmallMoneyBean> data;

    private LayoutInflater inflater;

    public SmallMoneyAdapter(Context mContext, List<SmallMoneyBean> data) {
        this.mContext = mContext;
        this.data = data;
        inflater = LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder viewHoder;
        if (view == null) {
            viewHoder = new ViewHoder();
            view = inflater.inflate(R.layout.small_money_list_item, null);
            viewHoder.txtRedBag = (TextView) view.findViewById(R.id.smallmoney_type);
            viewHoder.txtMoney = (TextView) view.findViewById(R.id.smallmoney_money);
            viewHoder.txtTime = (TextView) view.findViewById(R.id.smallmoney_time);
            view.setTag(viewHoder);

        } else {
            viewHoder = (ViewHoder) view.getTag();
        }

        SmallMoneyBean bean = data.get(i);
        if (bean.getActive().equals("ALI_X_ZFWC")) {
            viewHoder.txtMoney.setText("+" + bean.getAmount());
            viewHoder.txtRedBag.setText("支付宝充值");
        } else if (bean.getActive().equals("WX_X_ZFWC")) {
            viewHoder.txtMoney.setText("+" + bean.getAmount());
            viewHoder.txtRedBag.setText("微信充值");
        } else if (bean.getActive().equals("SMALL_R_ZFWC")) {
            viewHoder.txtMoney.setText("-" + bean.getAmount());
            viewHoder.txtRedBag.setText("手机充值");
        } else if (bean.getActive().equals("SMALL_RED_ADD")) {
            viewHoder.txtMoney.setText("+" + bean.getAmount());
            viewHoder.txtRedBag.setText("领取红包");
        } else if (bean.getActive().equals("SMALL_RED_REDUCE")) {
            viewHoder.txtMoney.setText("-" + bean.getAmount());
            viewHoder.txtMoney.setTextColor(mContext.getResources().getColor(R.color.black));
            viewHoder.txtRedBag.setText("发送红包");
        } else if (bean.getActive().equals("SMALL_ALI_X_ZFWC")) {
            viewHoder.txtRedBag.setText("支付宝充值");
            viewHoder.txtMoney.setText("+" + bean.getAmount());
            viewHoder.txtMoney.setTextColor(mContext.getResources().getColor(R.color.black));
        } else if (bean.getActive().equals("SMALL_WX_X_ZFWC")) {
            viewHoder.txtRedBag.setText("微信充值");
            viewHoder.txtMoney.setText("+" + bean.getAmount());
            viewHoder.txtMoney.setTextColor(mContext.getResources().getColor(R.color.black));
        } else if (bean.getActive().equals("SMALL_TRANSFER_ADD")) {
            viewHoder.txtMoney.setText("+" + bean.getAmount());
            viewHoder.txtRedBag.setText("收到转账");
        } else if (bean.getActive().equals("SMALL_TRANSFER_REDUCE")) {
            viewHoder.txtMoney.setText("-" + bean.getAmount());
            viewHoder.txtRedBag.setText("转账");
        }
        viewHoder.txtTime.setText(bean.getCreatedTime());
        return view;
    }

    class ViewHoder {
        TextView txtRedBag;
        TextView txtTime;
        TextView txtMoney;

    }
}

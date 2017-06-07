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
    private List<SmallMoneyBean.DataObjectBean> data;

    private LayoutInflater inflater;

    public SmallMoneyAdapter(Context mContext, List<SmallMoneyBean.DataObjectBean> data) {
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

        SmallMoneyBean.DataObjectBean bean = data.get(i);

        viewHoder.txtMoney.setText( bean.getMoney());
        viewHoder.txtRedBag.setText(bean.getName());

        viewHoder.txtTime.setText(bean.getTime());
        return view;
    }

    class ViewHoder {
        TextView txtRedBag;
        TextView txtTime;
        TextView txtMoney;

    }
}

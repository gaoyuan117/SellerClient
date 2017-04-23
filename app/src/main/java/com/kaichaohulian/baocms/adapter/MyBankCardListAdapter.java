package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.BankCardEntity;

import java.util.List;

/**
 * Created by liuyu on 2016/12/24.
 */

public class MyBankCardListAdapter extends BaseAdapter {
    public static final int BANK_CARD_TYPE = 0;
    public static final int ADD_BANKCARD_TYPE = 1;

    Context context;
    LayoutInflater inflater;
    List<BankCardEntity> data;

    public MyBankCardListAdapter(Context context, List<BankCardEntity> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < data.size()) {
            return BANK_CARD_TYPE;
        } else
            return ADD_BANKCARD_TYPE;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    @Override
    public int getCount() {
        return data.size() + 1;
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

        int type = getItemViewType(position);
        ViewHolder viewHolder;
        switch (type) {
            case BANK_CARD_TYPE:
                try {
                    if (convertView == null) {
                        viewHolder = new ViewHolder();
                        convertView = inflater.inflate(R.layout.my_bankcard_list_item1, null);
                        viewHolder.yhk_bg = (RelativeLayout) convertView.findViewById(R.id.yhk_bg);
                        viewHolder.name = (TextView) convertView.findViewById(R.id.my_bankcard_name);
                        viewHolder.cardType = (TextView) convertView.findViewById(R.id.my_bankcard_type);
                        viewHolder.lastFourNumber = (TextView) convertView.findViewById(R.id.my_bankcard_last_number);
                        convertView.setTag(viewHolder);
                    } else {
                        viewHolder = (ViewHolder) convertView.getTag();
                    }
                    viewHolder.name.setText(data.get(position).getBankName());
                    viewHolder.lastFourNumber.setText(data.get(position).getCardNo().substring(data.get(position).getCardNo().length() - 4, data.get(position).getCardNo().length()));
//                viewHolder.cardType.setText(data.get(position).getBankCardType());
                    switch (data.get(position).getBankName()) {
                        case "建设银行":
                            viewHolder.yhk_bg.setBackgroundResource(R.mipmap.jianshe_logo);
                            break;
                        case "交通银行":
                            viewHolder.yhk_bg.setBackgroundResource(R.mipmap.jiaotong_logo);
                            break;
                        case "招商银行":
                            viewHolder.yhk_bg.setBackgroundResource(R.mipmap.zhaoshang_logo);
                            break;
                        case "农业银行":
                            viewHolder.yhk_bg.setBackgroundResource(R.mipmap.nongye_logo);
                            break;
                        case "中国银行":
                            viewHolder.yhk_bg.setBackgroundResource(R.mipmap.zhongguo_logo);
                            break;
                        case "中信银行":
                            viewHolder.yhk_bg.setBackgroundResource(R.mipmap.zhongxin_logo);
                            break;
                        case "工商银行":
                            viewHolder.yhk_bg.setBackgroundResource(R.mipmap.gongshang_logo);
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case ADD_BANKCARD_TYPE:
                convertView = inflater.inflate(R.layout.my_bankcard_list_item2, null);
                break;
        }
        return convertView;
    }

    class ViewHolder {
        RelativeLayout yhk_bg;
        TextView name;
        TextView cardType;
        TextView lastFourNumber;
    }
}

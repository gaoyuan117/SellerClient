package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;

/**
 * Created by liuyu on 2016/12/23.
 */

public class WhoCanSeeMeListAdapter extends BaseAdapter {

    String rightOption[];
    String rightOptionTip[];
    Context context;
    LayoutInflater inflater;
    public static int selected;

    public WhoCanSeeMeListAdapter(String[] rightOption, String[] rightOptionTip, Context context) {
        this.rightOption = rightOption;
        this.rightOptionTip = rightOptionTip;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return rightOption.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.who_can_see_me_list_item,null);
            viewHolder.option = (TextView) convertView.findViewById(R.id.right_option);
            viewHolder.optionTip = (TextView) convertView.findViewById(R.id.right_option_tip);
            viewHolder.imgSelected = (ImageView) convertView.findViewById(R.id.right_selected_icon);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.option.setText(rightOption[position]);
        viewHolder.optionTip.setText(rightOptionTip[position]);
        if (position == selected){
            viewHolder.imgSelected.setImageResource(R.mipmap.selected);
            viewHolder.imgSelected.setVisibility(View.VISIBLE);

        }else {
            viewHolder.imgSelected.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder{
        TextView option;
        TextView optionTip;
        ImageView imgSelected;
    }
}

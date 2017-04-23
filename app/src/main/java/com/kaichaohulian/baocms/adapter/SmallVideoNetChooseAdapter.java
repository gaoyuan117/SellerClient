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
 * Created by liuyu on 2016/12/9.
 */

public class SmallVideoNetChooseAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mFlater;

    public static int flag = 0;

    private String[]data ;

    public SmallVideoNetChooseAdapter(String[] data, Context context) {

            this.mContext = context;
            this.data = data;
            mFlater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return data[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null){
            viewHolder = new ViewHolder();
            view = mFlater.inflate(R.layout.small_video_list_item2,null);
            viewHolder.text_choice = (TextView) view.findViewById(R.id.small_video_list_item_text);
            viewHolder.img_gou     = (ImageView) view.findViewById(R.id.small_video_list_item_image);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();

        }

        if (flag == i){
            viewHolder.img_gou.setVisibility(View.VISIBLE);
            viewHolder.img_gou.setImageResource(R.mipmap.selected);
        }else {
            viewHolder.img_gou.setVisibility(View.GONE);
        }
        viewHolder.text_choice.setText(data[i]);
        System.out.print("I am going to getView");

        return view;
    }
    static class ViewHolder{

        TextView text_choice;
        ImageView img_gou;
    }
}

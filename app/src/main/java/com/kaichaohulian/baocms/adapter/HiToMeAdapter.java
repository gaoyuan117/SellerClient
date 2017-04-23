package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.HiToMeEntity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyu on 2016/12/13.
 */

public class HiToMeAdapter extends BaseAdapter {

    private List<HiToMeEntity> data = new ArrayList<HiToMeEntity>();

    private Context mContext;
    private LayoutInflater mFlater;

    public HiToMeAdapter(Context mContext, List<HiToMeEntity> data) {
        this.mContext = mContext;
        this.data = data;
        mFlater = LayoutInflater.from(mContext);
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
        final ViewHolder viewHolder;
        if (view == null)
        {
            viewHolder = new ViewHolder();
            view = mFlater.inflate(R.layout.hi_to_me_list_item,null);
            viewHolder.textView = (TextView) view.findViewById(R.id.name_text);
            viewHolder.img      = (ImageView) view.findViewById(R.id.head_icon);
            viewHolder.btn      = (Button) view.findViewById(R.id.accept_btn);

            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.textView.setText(data.get(i).getName());
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.btn.getBackground().setAlpha(0);
                viewHolder.btn.setText("已添加");
                viewHolder.btn.setTextColor(mContext.getResources().getColor(R.color.light_gray));
            }
        });
        return view;
    }

    static class ViewHolder{
        ImageView img;
        TextView textView;
        Button btn;

    }
}


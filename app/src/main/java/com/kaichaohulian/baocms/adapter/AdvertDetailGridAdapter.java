package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzwzz on 2017/5/16.
 */

public class AdvertDetailGridAdapter extends BaseListAdapter {
    public AdvertDetailGridAdapter(Context context, @Nullable List data) {
        super(context, data);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view==null){
            view=View.inflate(context,layoutIds[0],null);
            vh=new ViewHolder(view);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        String url= (String) getItem(i);
        Log.d("AdvertDetailGridAdapter", url);
        Glide.with(context).load(url).into(vh.img);
        return view;
    }
    class ViewHolder{
        @BindView(R.id.img_item_advert_detail)
        ImageView img;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}

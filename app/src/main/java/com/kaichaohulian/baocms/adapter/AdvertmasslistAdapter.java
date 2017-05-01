package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseListAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzwzz on 2017/5/1.
 */

public class AdvertmasslistAdapter extends BaseListAdapter{
    public AdvertmasslistAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    public int getCount() {
        if(data==null){
            return 5;
        }else{
            return data.size()==0?1:data.size();
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AdvertmassHolder vh;
        if(view==null){
            view=View.inflate(context,layoutIds[0],null);
            vh=new AdvertmassHolder(view);
            view.setTag(vh);
        }else{
            vh= (AdvertmassHolder) view.getTag();
        }
        return view;
    }

    public class AdvertmassHolder{
        @BindView(R.id.title_advertmass)
        TextView title;
        @BindView(R.id.content_advertmass)
        TextView content;
        @BindView(R.id.img_advertmass)
        ImageView img;
        @BindView(R.id.time_advertmass)
        TextView time;

        public AdvertmassHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }

}

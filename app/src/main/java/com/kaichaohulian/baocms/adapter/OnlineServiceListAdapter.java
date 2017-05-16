package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseListAdapter;
import com.kaichaohulian.baocms.entity.OnlineServiceEntity;

import java.util.List;

/**
 * Created by xzwzz on 2017/5/1.
 */

public class OnlineServiceListAdapter extends BaseListAdapter<OnlineServiceEntity>{

    public OnlineServiceListAdapter(Context context, List<OnlineServiceEntity> data) {
        super(context, data);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        OnlineServiceListViewHoler vh;
        if(view==null){
            view=View.inflate(context,layoutIds[0],null);
            vh=new OnlineServiceListViewHoler(view);
            view.setTag(vh);
        }else{
            vh= (OnlineServiceListViewHoler) view.getTag();
        }
        vh.nameText.setText(data.get(i).getName());
        Glide.with(context).load(data.get(i).getAvatar()).error(R.mipmap.default_onlinehead).into(vh.headImg);
        return view;
    }

    @Override
    public void setLayoutIds(@LayoutRes int... layoutIds) {
        super.setLayoutIds(layoutIds);
    }

    public class OnlineServiceListViewHoler{
        ImageView headImg;
        TextView nameText;

        public OnlineServiceListViewHoler(View view) {
            headImg= (ImageView) view.findViewById(R.id.img_itemonlinehead);
            nameText= (TextView) view.findViewById(R.id.tv_itemonlinename);
        }
    }

}

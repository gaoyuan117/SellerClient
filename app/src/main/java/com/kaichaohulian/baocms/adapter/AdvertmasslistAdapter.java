package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseListAdapter;
import com.kaichaohulian.baocms.entity.AdviertisementEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzwzz on 2017/5/1.
 */

public class AdvertmasslistAdapter extends BaseListAdapter {
    String url="";
    public AdvertmasslistAdapter(Context context, List data, @LayoutRes int... layoutIds) {
        super(context, data);
        this.layoutIds = layoutIds;
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        } else {
            return data.size() == 0 ? 0 : data.size();
        }
    }

    private List getList(){
        return data;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        AdvertmassHolder vh;
        if (view == null) {
            view = View.inflate(context, layoutIds[0], null);
            vh = new AdvertmassHolder(view);
            view.setTag(vh);
        } else {
            vh = (AdvertmassHolder) view.getTag();
        }
        AdviertisementEntity entity = (AdviertisementEntity) data.get(i);
        vh.content.setText((String) entity.context);
        vh.title.setText((String) entity.title);
        StringBuffer buffer = new StringBuffer();
        if (entity.readNum.equals("null")) {
            buffer.append("阅读数 " + 0);
        } else {
            buffer.append("阅读数 " + entity.readNum);
        }
        if (entity.hasGetMoney.equals("null")) {
            buffer.append(" 已领取红包 " + 0);
        } else {
            buffer.append(" 已领取红包 " + entity.hasGetMoney);
        }
        if (entity.receive.equals("null")) {
            buffer.append(" 发布费用 "+0);
        } else {
            buffer.append(" 发布费用 "+entity.pay);
        }
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 H:m");
        Date date = new Date(Long.parseLong(entity.timeStamp));
        url="http://oez2a4f3v.bkt.clouddn.com/" + entity.image;
        url=url.replace(",","");
        Glide.with(context).load(url).error(R.mipmap.default_advertmass).into(vh.img);
        vh.time.setText(format.format(date));
        vh.content.setText(buffer.toString());

        return view;
    }

    public class AdvertmassHolder {
        @BindView(R.id.title_advertmass)
        TextView title;
        @BindView(R.id.content_advertmass)
        TextView content;
        @BindView(R.id.img_advertmass)
        ImageView img;
        @BindView(R.id.time_advertmass)
        TextView time;

        public AdvertmassHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

package com.kaichaohulian.baocms.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.ShopDetailActivity;
import com.kaichaohulian.baocms.activity.ShopManageActivity;
import com.kaichaohulian.baocms.entity.MessageShopEntity;

import java.util.List;

/**
 * 简单的好友Adapter实现
 * Created by Administrator on 2016/12/13 0013.
 */
public class MessageShopAdapter extends ArrayAdapter<MessageShopEntity> {
    private LayoutInflater layoutInflater;
    List<MessageShopEntity> userList;
    private int res;
    Context mContext;

    @SuppressLint("SdCardPath")
    public MessageShopAdapter(Context context, int resource, List<MessageShopEntity> objects) {
        super(context, resource, objects);
        mContext = context;
        this.res = resource;
        this.userList = objects;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(res, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.shop_title);
        TextView date = (TextView) convertView.findViewById(R.id.shop_subtitle);
        TextView content = (TextView) convertView.findViewById(R.id.shop_desc);
        ImageView bgIV = (ImageView) convertView.findViewById(R.id.show_bg);
        LinearLayout rootLL = (LinearLayout) convertView.findViewById(R.id.ll_shop_card_container);

        final MessageShopEntity shopEntity = getItem(position);
        if (shopEntity != null) {
            title.setText(shopEntity.getTitle());
            date.setText(shopEntity.getSubTitle());
            content.setText(shopEntity.getContent());
            Glide.with(mContext).load(shopEntity.getPath()).placeholder(R.mipmap.def_shop_bg).centerCrop().into(bgIV);
        }
        if (shopEntity.getTitle().equals("商家介绍")) {
            content.setText("查看全文");
            rootLL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ShopDetailActivity.class);
                    intent.putExtra("shop_detail", shopEntity.getDetails());
                    mContext.startActivity(intent);
                }
            });
        }
        return convertView;
    }

    @Override
    public MessageShopEntity getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }


}

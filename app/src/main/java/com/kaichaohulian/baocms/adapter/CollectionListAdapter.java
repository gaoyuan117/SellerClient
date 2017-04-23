package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.CollectionEntity;
import com.kaichaohulian.baocms.utils.StringUtils;

import java.util.List;
import java.util.Random;

/**
 * Created by liuyu on 2016/12/29.
 */

public class CollectionListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<CollectionEntity> data;

    public CollectionListAdapter(Context context, List<CollectionEntity> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listciew_collection_item, null);
            viewHolder.userName = (TextView) convertView.findViewById(R.id.collection_name_txt);
            viewHolder.date = (TextView) convertView.findViewById(R.id.collection_date_txt);
            viewHolder.headIcon = (ImageView) convertView.findViewById(R.id.collection_head_icon);
            viewHolder.content = (TextView) convertView.findViewById(R.id.tv_collect_container);
            viewHolder.bigPicture = (ImageView) convertView.findViewById(R.id.collection_img_info);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String wordType = data.get(position).getWordType();
        if ("wordType".equals(wordType)) {
            viewHolder.content.setVisibility(View.VISIBLE);
            viewHolder.bigPicture.setVisibility(View.GONE);
            if (!StringUtils.isEmpty(data.get(position).getContent())) {
                viewHolder.content.setText(data.get(position).getContent());
            }
        } else if ("pageType".equals(wordType)) {
            viewHolder.content.setVisibility(View.GONE);
            viewHolder.bigPicture.setVisibility(View.VISIBLE);
            if (!StringUtils.isEmpty(data.get(position).getBigPicture())) {
                Glide.with(context).load(data.get(position).getBigPicture()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.bigPicture);
            }
        } else if ("vodeoType".equals(wordType)) {
            viewHolder.content.setVisibility(View.GONE);
            viewHolder.bigPicture.setVisibility(View.VISIBLE);
        }

        viewHolder.userName.setText(data.get(position).getUserName());
        viewHolder.date.setText(data.get(position).getDate());
        if (!StringUtils.isEmpty(data.get(position).getHeadIcon())) {
            Glide.with(context).load(data.get(position).getHeadIcon()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.headIcon);
        }

        return convertView;
    }

    class ViewHolder {
        ImageView headIcon;
        TextView userName;
        TextView date;
        TextView content;
        ImageView bigPicture;
    }

    public int randomInt(int max) {

        return new Random().nextInt(max);
    }
}

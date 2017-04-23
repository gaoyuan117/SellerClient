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
import com.kaichaohulian.baocms.entity.GroupMMEntity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by Administrator on 2017/3/3.
 */

public class GroupMMAdapter extends BaseAdapter {
    private Context mContext;
    private List<GroupMMEntity> mData;
    private LayoutInflater inflater;

    public GroupMMAdapter(Context context, List<GroupMMEntity> data) {
        this.mContext = context;
        this.mData = data;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        GroupMMEntity groupMMEntity = mData.get(position);
        GroupMMAdapter.ViewHolder viewHolder = null;
//        if (convertView == null) {
        viewHolder = new GroupMMAdapter.ViewHolder();
        convertView = inflater.inflate(R.layout.item_group_mm, null, false);
        viewHolder.iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
//            ImageLoader.getInstance().displayImage(groupMMEntity.getAvator(), viewHolder.iv_avatar);
        Glide.with(mContext).load(groupMMEntity.getAvator()).placeholder(R.mipmap.default_useravatar).into(viewHolder.iv_avatar);
        viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        convertView.setTag(viewHolder);
//        }
        viewHolder = (GroupMMAdapter.ViewHolder) convertView.getTag();

        viewHolder.tv_name.setText(groupMMEntity.getUsername());
        return convertView;
    }

    public class ViewHolder {
        ImageView iv_avatar;
        TextView tv_name;
    }
}

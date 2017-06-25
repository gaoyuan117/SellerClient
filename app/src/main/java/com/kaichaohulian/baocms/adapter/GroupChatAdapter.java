package com.kaichaohulian.baocms.adapter;

/**
 * 群聊列表
 * Created by ljl on 2017/1/1.
 */

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.GroupEntity;

import java.util.List;


public class GroupChatAdapter extends android.widget.BaseAdapter {
    private Context mContext;
    private LayoutInflater inflater;

    private List<GroupEntity> List;

    public GroupChatAdapter(Context mContext, List<GroupEntity> List) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        this.List = List;
    }

    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public GroupEntity getItem(int position) {
        try {
            return List.get(position);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

//    @Override
//    public int getItemViewType(int position) {
//        return position;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 6;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupEntity GroupEntity = getItem(position);
        List<String> avatar = GroupEntity.getAvatar();
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            switch (avatar.size()) {
                case 1:
                    convertView = inflater.inflate(R.layout.item_conversation_group1, null, false);
                    viewHolder.iv_avatar1 = (ImageView) convertView.findViewById(R.id.iv_avatar1);
                    Glide.with(mContext).load(avatar.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar1);
                    break;
                case 2:
                    convertView = inflater.inflate(R.layout.item_conversation_group2, null, false);
                    viewHolder.iv_avatar1 = (ImageView) convertView.findViewById(R.id.iv_avatar1);
                    viewHolder.iv_avatar2 = (ImageView) convertView.findViewById(R.id.iv_avatar2);
                    Glide.with(mContext).load(avatar.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar1);
                    Glide.with(mContext).load(avatar.get(1)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar2);
                    break;
                case 3:
                    convertView = inflater.inflate(R.layout.item_conversation_group3, null, false);
                    viewHolder.iv_avatar1 = (ImageView) convertView.findViewById(R.id.iv_avatar1);
                    viewHolder.iv_avatar2 = (ImageView) convertView.findViewById(R.id.iv_avatar2);
                    viewHolder.iv_avatar3 = (ImageView) convertView.findViewById(R.id.iv_avatar3);
                    Glide.with(mContext).load(avatar.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar1);
                    Glide.with(mContext).load(avatar.get(1)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar2);
                    Glide.with(mContext).load(avatar.get(2)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar3);
                    break;
                case 4:
                    convertView = inflater.inflate(R.layout.item_conversation_group4, null, false);
                    viewHolder.iv_avatar1 = (ImageView) convertView.findViewById(R.id.iv_avatar1);
                    viewHolder.iv_avatar2 = (ImageView) convertView.findViewById(R.id.iv_avatar2);
                    viewHolder.iv_avatar3 = (ImageView) convertView.findViewById(R.id.iv_avatar3);
                    viewHolder.iv_avatar4 = (ImageView) convertView.findViewById(R.id.iv_avatar4);
                    Glide.with(mContext).load(avatar.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar1);
                    Glide.with(mContext).load(avatar.get(1)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar2);
                    Glide.with(mContext).load(avatar.get(2)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar3);
                    Glide.with(mContext).load(avatar.get(3)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar4);
                    break;
                case 5:
                    convertView = inflater.inflate(R.layout.item_conversation_group5, null, false);
                    viewHolder.iv_avatar1 = (ImageView) convertView.findViewById(R.id.iv_avatar1);
                    viewHolder.iv_avatar2 = (ImageView) convertView.findViewById(R.id.iv_avatar2);
                    viewHolder.iv_avatar3 = (ImageView) convertView.findViewById(R.id.iv_avatar3);
                    viewHolder.iv_avatar4 = (ImageView) convertView.findViewById(R.id.iv_avatar4);
                    viewHolder.iv_avatar5 = (ImageView) convertView.findViewById(R.id.iv_avatar5);
                    Glide.with(mContext).load(avatar.get(0)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar1);
                    Glide.with(mContext).load(avatar.get(1)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar2);
                    Glide.with(mContext).load(avatar.get(2)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar3);
                    Glide.with(mContext).load(avatar.get(3)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar4);
                    Glide.with(mContext).load(avatar.get(4)).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar5);
                    break;

                default:
                    convertView = inflater.inflate(R.layout.item_conversation_group1, null, false);
                    viewHolder.iv_avatar1 = (ImageView) convertView.findViewById(R.id.iv_avatar1);
                    Glide.with(mContext).load("").diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(viewHolder.iv_avatar1);
                    break;
            }
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            viewHolder.tv_unread = (TextView) convertView.findViewById(R.id.tv_unread);
            viewHolder.msg_state = (ImageView) convertView.findViewById(R.id.msg_state);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tv_name.setText(GroupEntity.getName());
        Log.e("gy", "群组吗：" + GroupEntity.getName());
        viewHolder.tv_time.setVisibility(View.GONE);
        viewHolder.tv_content.setVisibility(View.GONE);
        viewHolder.tv_unread.setVisibility(View.GONE);
        return convertView;
    }

    public class ViewHolder {

        TextView tv_name;
        TextView tv_content;
        TextView tv_time;
        TextView tv_unread;
        ImageView msg_state;


        ImageView iv_avatar;
        ImageView iv_avatar1;
        ImageView iv_avatar2;
        ImageView iv_avatar3;
        ImageView iv_avatar4;
        ImageView iv_avatar5;
    }
}

package com.kaichaohulian.baocms.adapter;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.GroupDetail;
import com.kaichaohulian.baocms.utils.StringUtils;

import java.util.List;

public class ChenYuanAdapter extends BaseAdapter {
    public List<GroupDetail.DataObject.Members> members;

    @Override
    public int getCount() {

        return members == null ? 1 : members.size() < 8 ? members.size() + 2 : 8;
//        return members == null ? 0 : members.size() < 8 ? members.size() : 8;
    }

    public void setMembers(List<GroupDetail.DataObject.Members> members) {
        this.members = members;
        notifyDataSetChanged();
    }

    @Override
    public GroupDetail.DataObject.Members getItem(int position) {
        try {
            return members.get(position);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);
        ImageView ImageView = (android.widget.ImageView) convertView.findViewById(R.id.grid_item_image);
        TextView TextView = (android.widget.TextView) convertView.findViewById(R.id.grid_item_label);

        if (true) {

            if (getCount() - 1 == position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ImageView.setImageDrawable(parent.getContext().getDrawable(R.mipmap.icon_releasetalk_del));
                } else {
                    ImageView.setImageBitmap(null);
                    ImageView.setBackgroundResource(R.mipmap.icon_releasetalk_del);
                }
                TextView.setText("");
            }

            if (getCount() - 2 == position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ImageView.setImageDrawable(parent.getContext().getDrawable(R.mipmap.icon_releasetalk_add));
                } else {
                    ImageView.setImageBitmap(null);
                    ImageView.setBackgroundResource(R.mipmap.icon_releasetalk_add);
                }
                TextView.setText("");
            } else {
                GroupDetail.DataObject.Members Item = getItem(position);
                if (Item != null) {
                    Glide.with(parent.getContext()).load(Item.avatar).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(ImageView);
                    if (mDisplayName == 1) {
                        if (StringUtils.isEmpty(Item.nameInGroup)) {
                            TextView.setText("未命名");
                        } else {
                            TextView.setText(Item.nameInGroup);
                        }
                    } else {
                        if (StringUtils.isEmpty(Item.username)) {
                            TextView.setText("未命名");
                        } else {
                            TextView.setText(Item.username);
                        }
                    }
                }
            }
        } else {
            GroupDetail.DataObject.Members Item = getItem(position);
            if (Item != null) {
                Glide.with(parent.getContext()).load(Item.avatar).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(ImageView);
                if (mDisplayName == 1) {
                    if (StringUtils.isEmpty(Item.nameInGroup)) {
                        TextView.setText("未命名");
                    } else {
                        TextView.setText(Item.nameInGroup);
                    }
                } else {
                    if (StringUtils.isEmpty(Item.username)) {
                        TextView.setText("未命名");
                    } else {
                        TextView.setText(Item.username);
                    }
                }
            }

        }

        return convertView;
    }

    public void setDisplayName(int displayName) {
        mDisplayName = displayName;
    }

    private int mDisplayName;
}

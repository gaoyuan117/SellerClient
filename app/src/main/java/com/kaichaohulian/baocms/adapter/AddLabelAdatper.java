package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：新建标签listview Adapter
 * 创建人：Administrator
 * 创建时间：2017/1/18
 * 修改人：Administrator
 * 修改时间：Administrator
 * 修改备注：
 *
 * @version ${VERSION}
 */
public class AddLabelAdatper extends BaseAdapter{


    private List<ContactFriendsEntity> list = new ArrayList<>();
    private int res;
    private Context context;

    public AddLabelAdatper(Context context, int resource, List<ContactFriendsEntity> users) {
        this.context=context;
        this.res = resource;
        this.list = users;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(res,null);
        }

        ImageView iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvHeader = (TextView) convertView.findViewById(R.id.header);
        final ContactFriendsEntity user = list.get(position);

        final String avater = user.getAvatar();
        String name = user.getUsername();
        String header = user.getHeader();
        final String username = user.getId() + "";
        tv_name.setText(name);
        iv_avatar.setImageResource(R.mipmap.default_useravatar);
        if (avater != null && !avater.equals("")) {
            Glide.with(context).load(avater).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.color.bg_no_photo).into(iv_avatar);
        }
        if (position == 0 || header != null && !header.equals(getItem(position - 1))) {
            if ("".equals(header)) {
                tvHeader.setVisibility(View.GONE);
            } else {
                tvHeader.setVisibility(View.VISIBLE);
                tvHeader.setText(header);
            }
        } else {
            tvHeader.setVisibility(View.GONE);
        }

        // 选择框checkbox
        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
        checkBox.setVisibility(View.GONE);

        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
//        if (position < 0) {
//            return "";
//        }
//        String header = list.get(position).getHeader();
//        return header;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}


package com.kaichaohulian.baocms.adapter;

import android.app.Activity;
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
import com.kaichaohulian.baocms.activity.ImagePagerActivity;
import com.kaichaohulian.baocms.activity.ReleaseTalkActivity;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.circledemo.bean.HeadInfo;
import com.kaichaohulian.baocms.circledemo.widgets.MultiImageView;
import com.kaichaohulian.baocms.entity.MyAlbumEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 相册适配器
 * Created by ljl on 2016/12/29.
 */

public class MyAlbumAdapter extends BaseAdapter {
    private List<MyAlbumEntity> List;
    private Context mContext;

    public MyAlbumAdapter(Context mContext, List<MyAlbumEntity> List) {
        this.mContext = mContext;
        this.List = List;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return List.size() + 1;
    }

    @Override
    public MyAlbumEntity getItem(int position) {
        return List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == 0) {
            HeaderViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.head_circle, null);
                holder = new HeaderViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (HeaderViewHolder) convertView.getTag();
            }
            if (mHeadInfo != null) {
                ImageView bg = (ImageView) convertView.findViewById(R.id.head_bg);
                holder.name.setText(mHeadInfo.nickname);

                holder.create.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtil.next((Activity) mContext, ReleaseTalkActivity.class);
                    }
                });
                Glide.with(parent.getContext()).load(mHeadInfo.avatar).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.album_bg).into(holder.head);
                Glide.with(parent.getContext()).load(mHeadInfo.bg).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.album_bg).into(bg);
            }
        } else {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_myalbum_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            try {
                MyAlbumEntity Item = getItem(position - 1);
                if (Item != null) {
                    String Time = Item.getCreateTime();
                    if (formatDateTime(Time)) {
                        holder.time.setText("今天");
                    } else {
                        Date date = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(Time);
                        int mDay = date.getDay();//年
                        int mMonth = date.getMonth() + 1;//月
                        holder.time.setText(mDay + "");
                        holder.time2.setText(getMonth(mMonth));
                    }
                    holder.text_context.setText(Item.getContent());
                    final List<String> photos = Item.getList();
                    if (photos != null && photos.size() > 0) {
                        holder.multiImageView.setVisibility(View.VISIBLE);
                        holder.multiImageView.setList(photos);
                        holder.multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //imagesize是作为loading时的图片size
                                ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                                ImagePagerActivity.startImagePagerActivity(mContext, photos, position, imageSize);
                            }
                        });
                    } else {
                        holder.multiImageView.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return convertView;
    }

    public String getMonth(int month) {
        switch (month) {
            case 1:
                return "一月";
            case 2:
                return "二月";
            case 3:
                return "三月";
            case 4:
                return "四月";
            case 5:
                return "五月";
            case 6:
                return "六月";
            case 7:
                return "七月";
            case 8:
                return "八月";
            case 9:
                return "九月";
            case 10:
                return "十月";
            case 11:
                return "十一月";
            case 12:
                return "十二月";
        }
        return "";
    }

    HeadInfo mHeadInfo;

    public void setHeadInfo(HeadInfo headInfo) {
        mHeadInfo = headInfo;
    }

    public class HeaderViewHolder {
        public ImageView head;
        public TextView name;
        public ImageView create;

        public HeaderViewHolder(View itemView) {
            head = (ImageView) itemView.findViewById(R.id.head);
            name = (TextView) itemView.findViewById(R.id.name);
            create = (ImageView) itemView.findViewById(R.id.create_photo);
        }
    }

    public class ViewHolder {
        public MultiImageView multiImageView;
        public TextView time, text_context, time2;

        public ViewHolder(View itemView) {
            multiImageView = (MultiImageView) itemView.findViewById(R.id.multiImagView);
            time = (TextView) itemView.findViewById(R.id.time);
            time2 = (TextView) itemView.findViewById(R.id.time2);
            text_context = (TextView) itemView.findViewById(R.id.text_context);
        }
    }

    public boolean formatDateTime(String time) {
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
        if (time == null || "".equals(time)) {
            return false;
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();    //今天
        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        current.setTime(date);
        if (current.after(today)) {
            return true;
        }
        return false;
    }

}

package com.kaichaohulian.baocms.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.ReleaseTalkActivity;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.circledemo.bean.HeadInfo;
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
    public int getCount() {
        return List.size() + 2;
    }

    @Override
    public MyAlbumEntity getItem(int position) {
        return List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (position == 0) {
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
                Glide.with(parent.getContext()).load(mHeadInfo.avatar).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.def_shop_bg).into(holder.head);
                Glide.with(parent.getContext()).load(mHeadInfo.bg).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.def_shop_bg).into(bg);
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
            if (position == 1) {
                holder.text_context.setText("");
                holder.time.setText("今天");
                holder.multiImageView.setImageResource(R.mipmap.camera_myalbum);
                holder.multiImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                    ActivityUtil.next(mContext, ReleaseTalkActivity.class);
                        mContext.startActivity(new Intent(mContext, ReleaseTalkActivity.class));
                    }
                });
            } else {
                MyAlbumEntity Item = getItem(position - 2);
                if (Item != null) {
                    String Time = Item.getCreateTime();
                    if (formatDateTime(Time)) {
                        holder.time.setText("今天");
                    } else {
                        Date date = null;
                        try {
                            date = new SimpleDateFormat("yyyy-MM-dd").parse(Time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        int mDay = date.getDay();//日
                        int mMonth = date.getMonth() + 1;//月
                        Spannable sp = new SpannableString(mDay + " " + getMonth(mMonth));
                        int length = 2;
                        if (mDay < 10) {
                            length = 1;
                        }
                        sp.setSpan(new AbsoluteSizeSpan(32, true), 0, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        sp.setSpan(new AbsoluteSizeSpan(12, true), length, sp.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        holder.time.setText(sp);
                    }
                    holder.text_context.setText(Item.getContent());
                    final List<String> photos = Item.getList();
                    if (photos != null) {
                        for (int i = 0; i < photos.size(); i++) {
                            if (photos.get(i) != null) {
                                Glide.with(mContext).load(photos.get(i)).into(holder.multiImageView);
                                break;
                            }
                        }
                    }
                }

            }
        }
        return convertView;

    }

    public String getMonth(int month) {
        switch (month) {
            case 1:
                return " 1月";
            case 2:
                return " 2月";
            case 3:
                return " 3月";
            case 4:
                return " 4月";
            case 5:
                return " 5月";
            case 6:
                return " 6月";
            case 7:
                return " 7月";
            case 8:
                return " 8月";
            case 9:
                return " 9月";
            case 10:
                return " 10月";
            case 11:
                return " 11月";
            case 12:
                return " 12月";
        }
        return "";
    }

    HeadInfo mHeadInfo;

    public void SetHeadInfo(HeadInfo headInfo) {
        this.mHeadInfo = headInfo;
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
        public ImageView multiImageView;
        public TextView time, text_context;

        public ViewHolder(View itemView) {
            multiImageView = (ImageView) itemView.findViewById(R.id.multiImagView);
            time = (TextView) itemView.findViewById(R.id.time);
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

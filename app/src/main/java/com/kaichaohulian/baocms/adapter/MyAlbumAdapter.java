package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.ImagePagerActivity;
import com.kaichaohulian.baocms.activity.MyAlbumActivity;
import com.kaichaohulian.baocms.activity.ReleaseTalkActivity;
import com.kaichaohulian.baocms.base.BaseListAdapter;
import com.kaichaohulian.baocms.circledemo.widgets.MultiImageView;
import com.kaichaohulian.baocms.entity.AblumEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 相册适配器
 * Created by ljl on 2016/12/29.
 */

public class MyAlbumAdapter extends BaseListAdapter {


    public MyAlbumAdapter(Context context, @Nullable List data) {
        super(context, data);
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 1;
        } else {
            return data.size() == 0 ? 1 : data.size() + 1;
        }
    }

    public boolean formatDateTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
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

    private void FormatString(List list, String params) {
        if (TextUtils.isEmpty(params) || params.equals("null")) {
            return;
        }
        String s = params.replace("]", "");
        s = s.replace("[", "");
        s = s.replace("\\", "");
        s = s.replace("\"", "");
        Collections.addAll(list, s.split(","));
    }

    @Override
    public Object getItem(int i) {
        if (i >= 1) {
            return super.getItem(i - 1);
        } else {
            return null;
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            if (position == 0) {
                view = View.inflate(context, R.layout.item_myalbum_listhead, null);
            } else {
                view = View.inflate(context, R.layout.item_myalbum_list, null);
            }
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        if (position == 0) {
            vh.textContext.setText("");
            vh.time.setText("今天");
            ImageView img = (ImageView) view.findViewById(R.id.img_myablum);
            img.setImageResource(R.mipmap.camera_myalbum);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    ActivityUtil.next(mContext, ReleaseTalkActivity.class);
                    MyAlbumActivity activity = (MyAlbumActivity) context;
                    activity.startActivityForResult((new Intent(context, ReleaseTalkActivity.class)), 100);
                }
            });
            img.setVisibility(View.VISIBLE);

        } else {

            try {
                AblumEntity.ExperiencesBean data = (AblumEntity.ExperiencesBean) getItem(position);
                String Time = data.createdTime;
                if (formatDateTime(Time)) {
                    vh.time.setText("今天");
                } else {
                    Date date = null;
                    Calendar calendar = Calendar.getInstance();
                    try {
                        date = new SimpleDateFormat("yyyy-MM-dd").parse(Time);
                        calendar.setTime(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                    int mMonth = calendar.get(Calendar.MONTH) + 1;
                    Spannable sp = new SpannableString(mDay + " " + getMonth(mMonth));
                    int length = 2;
                    if (mDay < 10) {
                        length = 1;
                    }
                    sp.setSpan(new AbsoluteSizeSpan(28, true), 0, length, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    sp.setSpan(new AbsoluteSizeSpan(12, true), length, sp.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    vh.time.setText(sp);
                }
                vh.textContext.setText((String) data.content);
                final List<String> photos = new ArrayList<>();
                FormatString(photos, (String) data.images);
                MultiImageView multiImageView = (MultiImageView) view.findViewById(R.id.multiImagView);
                if (photos != null && photos.size() > 0) {
                    multiImageView.setVisibility(View.VISIBLE);

                    multiImageView.setList(photos);
                    multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            //imagesize是作为loading时的图片size
                            ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                            ImagePagerActivity.startImagePagerActivity(context, photos, position, imageSize);
                        }
                    });

                } else {
                    multiImageView.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    public class ViewHolder {
        @BindView(R.id.time)
        TextView time;

        @BindView(R.id.text_context)
        TextView textContext;

        public ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }
    }
}
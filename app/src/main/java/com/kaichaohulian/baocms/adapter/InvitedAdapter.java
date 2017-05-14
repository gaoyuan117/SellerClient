package com.kaichaohulian.baocms.adapter;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.entity.InvitedBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by gaoyuan on 2017/5/9.
 */

public class InvitedAdapter extends BaseQuickAdapter<InvitedBean, BaseViewHolder> {

    public InvitedAdapter(int layoutResId, List<InvitedBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, InvitedBean item) {
        try {
            Glide.with(MyApplication.getInstance())
                    .load(item.getAvatar())
                    .error(R.mipmap.default_useravatar)
                    .into((ImageView) helper.getView(R.id.img_item_discover_invite_avatar2));
            ImageView imgChat = helper.getView(R.id.img_item_discover_invite_red_chat);
            ImageView imgArrow = helper.getView(R.id.img_item_discover_invite_arrow);
            LinearLayout layout = helper.getView(R.id.ll_item_discover_invite);
            TextView tvState = helper.getView(R.id.tv_item_discover_invite_state);
            long time = (getTimeStamp(item.getInvateTime()) - getTimeStamp(item.getCreatedTime())) / 1000;
            long time1 =getTimeStamp(item.getInvateTime())- new Date().getTime();
            Log.e("gy","现在的时间："+new Date().getTime());
            Log.e("gy","响应的时间："+getTimeStamp(item.getInvateTime()));
            Log.e("gy","时间："+time1);
            String hasTime = getStrTime(time1);//剩余时间

            helper.setText(R.id.tv_item_discover_invite_name, item.getNickName())
                    .setText(R.id.tv_item_discover_invite_time, item.getCreatedTime())
                    .setText(R.id.tv_item_discover_invite_content, item.getTitle())
                    .addOnClickListener(R.id.bt_item_discover_invite_refuse)
                    .addOnClickListener(R.id.bt_item_discover_invite_receive);

            int status = item.getStatus();
            if (status == 0) {
                imgChat.setVisibility(View.VISIBLE);
                layout.setVisibility(View.VISIBLE);
                tvState.setText("剩余" + hasTime);
                imgArrow.setVisibility(View.GONE);
            } else if (status == 1) {
                imgChat.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                tvState.setText("剩余" + hasTime);
                imgArrow.setVisibility(View.GONE);
            } else if (status == 2) {
                imgChat.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
                tvState.setText("参与成功");
                imgArrow.setVisibility(View.VISIBLE);
            } else if (status == 3) {
                imgChat.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
                tvState.setText("失败");
                imgArrow.setVisibility(View.VISIBLE);
            } else if (status == 4) {
                imgChat.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
                imgArrow.setVisibility(View.VISIBLE);
                tvState.setText("已失效");
            }

        } catch (Exception e) {
            Log.e("gy", e.toString());
        }

    }

    public long getTimeStamp(String timeStr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getStrTime(long cc_time) {
        String re_StrTime = null;
        //同理也可以转为其它样式的时间格式.例如："yyyy/MM/dd HH:mm"
        SimpleDateFormat sdf = new SimpleDateFormat("mm分ss秒");
        // 例如：cc_time=1291778220
        re_StrTime = sdf.format(new Date(cc_time * 1000L));

        return re_StrTime;
    }
}
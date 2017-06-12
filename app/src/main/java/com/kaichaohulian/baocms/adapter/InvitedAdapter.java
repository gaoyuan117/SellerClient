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
            TextView invitedState = helper.getView(R.id.tv_invited_status);
//            long time = (getTimeStamp(item.getInvateTime()) - getTimeStamp(item.getCreatedTime())) / 1000;
            long time1 = (getTimeStamp(item.getInvateTime()) - new Date().getTime());
            Log.e("gy", "时间：" + time1);
            String hasTime = getStrTime(time1);//剩余时间

            helper.setText(R.id.tv_item_discover_invite_name, item.getNickName())
                    .setText(R.id.tv_item_discover_invite_time, item.getCreatedTime())
                    .setText(R.id.tv_item_discover_invite_content, item.getTitle())
                    .addOnClickListener(R.id.bt_item_discover_invite_refuse)
                    .addOnClickListener(R.id.bt_item_discover_invite_receive);

            int status = item.getStatus();
            int userStatus = item.getUserApplyStatus();
            if (userStatus == 0) {
                imgChat.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
                imgArrow.setVisibility(View.GONE);
                invitedState.setVisibility(View.GONE);
            } else if (userStatus == 1) {
                imgChat.setVisibility(View.GONE);
                invitedState.setVisibility(View.VISIBLE);
                invitedState.setText("已接受");
                invitedState.setTextColor(MyApplication.getInstance().getResources().getColor(R.color.zy_text_pink2));
                layout.setVisibility(View.GONE);
                imgArrow.setVisibility(View.VISIBLE);
            } else if (userStatus == 2) {
                imgChat.setVisibility(View.GONE);
                invitedState.setVisibility(View.VISIBLE);
                invitedState.setText("已拒绝");
                invitedState.setTextColor(MyApplication.getInstance().getResources().getColor(R.color.zy_text_green));
                layout.setVisibility(View.GONE);
                imgArrow.setVisibility(View.VISIBLE);
            }

            if (status == 0) {
                imgChat.setVisibility(View.GONE);
                tvState.setText("剩余" + hasTime);
            } else if (status == 1) {
                imgChat.setVisibility(View.GONE);
                tvState.setText("剩余" + hasTime);
            } else if (status == 2) {
                imgChat.setVisibility(View.GONE);
                layout.setVisibility(View.GONE);
                tvState.setText("参与成功");
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
        Log.e("gy", "时间差：" + cc_time);
        long days = cc_time / (1000 * 60 * 60 * 24);
        long hours = (cc_time - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (cc_time - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);

        return hours + "小时" + minutes + "分";
    }
}

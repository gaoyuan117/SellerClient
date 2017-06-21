package com.kaichaohulian.baocms.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.entity.MyInviteBean;
import com.kaichaohulian.baocms.fragment.MyInviteFragment;

import java.util.List;

/**
 * Created by gaoyuan on 2017/5/9.
 */

public class MyInviteAdapter extends BaseQuickAdapter<MyInviteBean, BaseViewHolder> {
    private MyInviteFragment myInviteFragment;

    public MyInviteAdapter(int layoutResId, List<MyInviteBean> data, MyInviteFragment fragment) {
        super(layoutResId, data);
        myInviteFragment = fragment;
    }

    @Override
    protected void convert(BaseViewHolder helper, MyInviteBean item) {
        try {

            Glide.with(MyApplication.getInstance())
                    .load(item.getAvatar())
                    .error(R.mipmap.default_useravatar)
                    .into((ImageView) helper.getView(R.id.img_item_my_invite_avatar));

            int status = item.getStatus();
            int userApplyStatus = item.getUserApplyStatus();
            TextView tvState = helper.getView(R.id.tv_item_my_invite_state);
            TextView tvUserState = helper.getView(R.id.tv_item_my_invite_yn);
            if (status == 1) {
                tvState.setText("进行中");
            } else if (status == 2) {
                tvState.setText("参与成功");
            } else if (status == 3) {
                tvState.setText("失败");
            } else if (status == 4) {
                tvState.setText("已失效");
            } else if (status == 0) {
                tvState.setText("等待接受");
            }

            if (userApplyStatus == 1) {
                tvUserState.setVisibility(View.VISIBLE);
                tvUserState.setText("接受了您的邀请");
                tvUserState.setTextColor(MyApplication.getInstance().getResources().getColor(R.color.zy_text_pink2));
            } else if (userApplyStatus == 2) {
                tvUserState.setText("拒绝了您的邀请");
                tvUserState.setVisibility(View.VISIBLE);
                tvUserState.setTextColor(MyApplication.getInstance().getResources().getColor(R.color.zy_text_green));
            } else {
                tvUserState.setVisibility(View.GONE);
            }
            helper.setText(R.id.tv_item_my_invite_name, item.getNickName());
            helper.setText(R.id.tv_item_my_invite_content, item.getTitle());
            helper.setText(R.id.tv_item_my_invite_time, item.getCreatedTime() + "");
        } catch (Exception e) {
            Log.e("gy", e.toString());
        }
    }
}

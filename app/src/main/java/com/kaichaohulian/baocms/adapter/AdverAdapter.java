package com.kaichaohulian.baocms.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.entity.HasGetAdverBean;
import com.kaichaohulian.baocms.util.GlideUtils;

import java.util.List;

/**
 * Created by gaoyuan on 2017/5/5.
 */

public class AdverAdapter extends BaseQuickAdapter<HasGetAdverBean.AdvertListBean, BaseViewHolder> {

    public AdverAdapter(int layoutResId, List<HasGetAdverBean.AdvertListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HasGetAdverBean.AdvertListBean item) {
        String[] split = item.getImage().split(",");
        GlideUtils.glideQiNiuAvatar(split[0],
                (ImageView) helper.getView(R.id.img_item_adver_avatar2));

        helper.setText(R.id.tv_item_adver_name, item.getUserName())
                .setText(R.id.tv_item_adver_content, item.getContext())
                .setText(R.id.tv_iyem_adver_time, item.getCreatedTime());
        TextView tvState = helper.getView(R.id.tv_item_adver_lingqu);

        ImageView redChat = helper.getView(R.id.img_item_adver_red_chat);
        if (item.getReadStatus() == 0) {
            redChat.setVisibility(View.VISIBLE);
            tvState.setText("点击我领取红包哦");
            tvState.setTextColor(MyApplication.getInstance().getResources().getColor(R.color.zy_orange));
        } else {
            tvState.setText("已领取红包");
            tvState.setTextColor(MyApplication.getInstance().getResources().getColor(R.color.zy_text_color));
            redChat.setVisibility(View.GONE);
        }

    }
}

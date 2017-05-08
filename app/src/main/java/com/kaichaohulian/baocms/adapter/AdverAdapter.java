package com.kaichaohulian.baocms.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.HasGetAdverBean;
import com.kaichaohulian.baocms.util.GlideUtils;

import java.util.List;

/**
 * Created by gaoyuan on 2017/5/5.
 */

public class AdverAdapter extends BaseQuickAdapter<HasGetAdverBean, BaseViewHolder> {

    public AdverAdapter(int layoutResId, List<HasGetAdverBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HasGetAdverBean item) {
        String[] split = item.getImage().split(",");
        GlideUtils.glideQiNiuAvatar(split[0],
                (ImageView) helper.getView(R.id.img_item_adver_avatar2));

        helper.setText(R.id.tv_item_adver_name, item.getUserName())
                .setText(R.id.tv_item_adver_content, item.getContext())
                .setText(R.id.tv_iyem_adver_time, item.getCreatedTime());

        ImageView redChat = helper.getView(R.id.img_item_adver_red_chat);
        //TODO 判断是否已读

    }
}

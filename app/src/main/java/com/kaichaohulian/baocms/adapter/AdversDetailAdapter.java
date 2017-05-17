package com.kaichaohulian.baocms.adapter;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.util.GlideUtils;

import java.util.List;

/**
 * Created by gaoyuan on 2017/5/5.
 */

public class AdversDetailAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public AdversDetailAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (item.contains("http://")) {
            Glide.with(MyApplication.getInstance())
                    .load(item)
                    .error(R.mipmap.default_image)
                    .crossFade()
                    .into((ImageView) helper.getView(R.id.img_item_adver_detail_pic));
        } else {
            GlideUtils.glideQiNiuImg(item, (ImageView) helper.getView(R.id.img_item_adver_detail_pic));

        }
    }
}

package com.kaichaohulian.baocms.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.util.GlideUtils;

import java.util.List;

/**
 * Created by gaoyuan on 2017/5/5.
 */

public class AdversDetailAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public AdversDetailAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        GlideUtils.glideQiNiuImg(item, (ImageView) helper.getView(R.id.img_item_adver_detail_pic));
    }
}

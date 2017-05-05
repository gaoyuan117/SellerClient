package com.kaichaohulian.baocms.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by gaoyuan on 2017/5/5.
 */

public class AdverAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public AdverAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}

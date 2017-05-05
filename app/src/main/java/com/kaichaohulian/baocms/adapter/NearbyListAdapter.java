package com.kaichaohulian.baocms.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by gaoyuan on 2017/5/4.
 */

public class NearbyListAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public NearbyListAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}

package com.kaichaohulian.baocms.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.NearbyZyActivity;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.entity.GreetBean;

import java.util.List;

/**
 * Created by gaoyuan on 2017/5/4.
 */

public class GreetAdapter extends BaseQuickAdapter<GreetBean.RequestDTOs1Bean, BaseViewHolder> {
    public GreetAdapter(int layoutResId, List<GreetBean.RequestDTOs1Bean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GreetBean.RequestDTOs1Bean item) {
        Glide.with(MyApplication.getInstance())
                .load(item.getAvatar())
                .error(R.mipmap.default_useravatar)
                .into((ImageView) helper.getView(R.id.img_item_greet_avatar));
        helper.setText(R.id.tv_item_greet_name, item.getNickname())
                .addOnClickListener(R.id.bt_greet_add);

        Button btStatus = helper.getView(R.id.bt_greet_add);

        TextView tvAdded = helper.getView(R.id.tv_greet_added);
        if (item.getStatus().equals("接受")) {
            btStatus.setVisibility(View.VISIBLE);
            tvAdded.setVisibility(View.GONE);
        } else {
            btStatus.setVisibility(View.GONE);
            tvAdded.setVisibility(View.VISIBLE);

        }


    }
}

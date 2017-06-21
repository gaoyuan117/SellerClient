package com.kaichaohulian.baocms.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.entity.NearbyBean;
import com.kaichaohulian.baocms.util.GlideUtils;

import java.util.List;

/**
 * Created by gaoyuan on 2017/5/4.
 */

public class NearbyListAdapter extends BaseQuickAdapter<NearbyBean, BaseViewHolder> {

    public NearbyListAdapter(int layoutResId, List<NearbyBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NearbyBean item) {
        try{
            helper.setText(R.id.tv_item_nearby_name, item.getUsername())
                    .setText(R.id.tv_item_nearby_distance, item.getDistrictName() == null ? item.getDistance() : item.getDistance() + "-" + item.getDistrictName());
            ImageView sex = helper.getView(R.id.img_item_nearby_sex);
            if(item!=null){
                Glide.with(MyApplication.getInstance())
                        .load(item.getAvatar())
                        .error(R.mipmap.default_useravatar)
                        .crossFade()
                        .into((ImageView) helper.getView(R.id.img_item_nearby_avatar));
                if(item.getSex()==null){
                    item.setSex("男");
                }
                sex.setImageResource(item.getSex().equals("男") ? R.mipmap.boy : R.mipmap.gir);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

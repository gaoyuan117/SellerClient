package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.NearBusinessEntity;
import com.kaichaohulian.baocms.entity.NearByPeopleListEntity;
import com.kaichaohulian.baocms.entity.RandomIcon;

import java.util.List;
import java.util.Random;

/**
 * Created by liuyu on 2016/12/20.
 */

public class NearBusinessListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    List<NearBusinessEntity> data ;
//  int face[] = new int[]{R.mipmap.comma_face_01,R.mipmap.comma_face_02,R.mipmap.comma_face_03,R.mipmap.comma_face_04,R.mipmap.comma_face_05,R.mipmap.comma_face_06,R.mipmap.comma_face_07,R.mipmap.comma_face_08,R.mipmap.comma_face_09,R.mipmap.comma_face_10,R.mipmap.comma_face_11,R.mipmap.comma_face_12,R.mipmap.comma_face_13,R.mipmap.comma_face_14,R.mipmap.comma_face_15,R.mipmap.comma_face_16,R.mipmap.comma_face_17,R.mipmap.comma_face_17,R.mipmap.comma_face_18,R.mipmap.f1};

//    int face[] = RandomIcon.face;
    public NearBusinessListAdapter(Context context, List<NearBusinessEntity> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView =  inflater.inflate(R.layout.nearby_people_list_item,null);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.nearby_people_list_name);
            viewHolder.txtDistance = (TextView) convertView.findViewById(R.id.nearby_people_list_distance);
            viewHolder.txtSign = (TextView) convertView.findViewById(R.id.nearby_people_list_sign);
            viewHolder.imgHead = (ImageView) convertView.findViewById(R.id.nearby_people_list_head_icon);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtName.setText(data.get(position).getUsername());
        viewHolder.txtSign.setText(data.get(position).getThermalSignatrue());
        viewHolder.txtDistance.setText(data.get(position).getDistance());
        Glide.with(context)
                .load(data.get(position).getAvatar())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.imgHead);
        return convertView;
    }
    class ViewHolder{
        TextView txtName;
        TextView txtDistance;
        ImageView imgHead;
        TextView txtSign;
    }

    public int randomInt(int max){
        return new Random().nextInt(max);
    }
}

package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.MeAddressEntity;

import java.util.List;

/**
 * Created by liuyu on 2016/12/21.
 */

public class MeAddressListAdapter extends BaseAdapter {
    public static final int MeAddress_LIST_ITEM_FIRST = 0;
    public static final int MeAddress_LIST_ITEM_LAST = 1;


    Context context;
    LayoutInflater inflater;
    List<MeAddressEntity> data;

    public MeAddressListAdapter(Context context, List<MeAddressEntity> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return data.size()+1;
    }

    @Override
    public int getItemViewType(int position){

        if (position == data.size()){
            return  MeAddress_LIST_ITEM_LAST;
        }else {
            return  MeAddress_LIST_ITEM_FIRST;
        }
    }

    @Override
    public  int getViewTypeCount(){
        return 2;
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
        int type = getItemViewType(position);
        ViewHolder viewHolder;
        switch (type){
            case MeAddress_LIST_ITEM_FIRST:
                if (convertView == null){
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.me_address_list_item1,null);
                    viewHolder.name = (TextView) convertView.findViewById(R.id.me_address_name);
                    viewHolder.address = (TextView) convertView.findViewById(R.id.me_address_address);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.address.setText(data.get(position).getArea() + data.get(position).getAddress());
                viewHolder.name.setText(data.get(position).getShouHuoname());
                break;
            case MeAddress_LIST_ITEM_LAST:
                convertView = inflater.inflate(R.layout.me_address_list_item2,null);
                break;
        }
        return convertView;
    }
    class ViewHolder{
        TextView name;
        TextView address;
    }
}

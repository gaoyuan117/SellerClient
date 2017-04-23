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
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.entity.ReceivedRedBagListEntity;
import com.kaichaohulian.baocms.view.GlideCircleTransform;

import java.util.List;

/**
 * Created by liuyu on 2016/12/22.
 */
public class ReceviedRedBagListAdapter extends BaseAdapter{
    public static final int RECEIVED_REDBAG_LIST_ITEM_HEAD = 0;
    public static final int RECEIVED_REDBAG_LIST_ITEM_TWO = 1;
    private LayoutInflater inflater;
    private Context mContext;
    private List<ReceivedRedBagListEntity> data;

    private int redCount;
    private double redsum;
    private String time;
    private int bests;

    public ReceviedRedBagListAdapter(Context mContext, List<ReceivedRedBagListEntity> data) {
        this.mContext = mContext;
        this.data = data;
        inflater = LayoutInflater.from(mContext);
    }

    public void setHead(int redCount, double redsum, String time, int bests){
        this.redCount = redCount;
        this.redsum = redsum;
        this.time = time;
        this.bests = bests;
    }

    @Override
    public int getCount() {
        return data.size() + 1;
    }
    @Override
    public  int getItemViewType(int position){

        if (position == 0){
            return  RECEIVED_REDBAG_LIST_ITEM_HEAD;
        }else {
            return RECEIVED_REDBAG_LIST_ITEM_TWO;
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

        switch (type){
            case  RECEIVED_REDBAG_LIST_ITEM_HEAD:
                ViewHolderHead viewHoderHead;
                if (convertView == null){
                    viewHoderHead = new ViewHolderHead();
                    convertView = inflater.inflate(R.layout.received_redbag_list_item1, null);
                    viewHoderHead.imgHead = (ImageView) convertView.findViewById(R.id.received_redbag_head);
                    viewHoderHead.txtDate = (TextView) convertView.findViewById(R.id.received_redbag_time);
                    viewHoderHead.txtAccount = (TextView) convertView.findViewById(R.id.received_redbag_account);
                    viewHoderHead.txtName = (TextView) convertView.findViewById(R.id.received_redbag_name);
                    viewHoderHead.txtReceivedBagNumber = (TextView) convertView.findViewById(R.id.received_redbag_number);
                    viewHoderHead.txtShouqizuijia = (TextView) convertView.findViewById(R.id.received_redbag_shouqizuijia_number);
                    convertView.setTag(viewHoderHead);
                }else {
                    viewHoderHead = (ViewHolderHead) convertView.getTag();
                }
                viewHoderHead.txtReceivedBagNumber.setText(redCount+"");
                viewHoderHead.txtShouqizuijia.setText(bests+"");
                viewHoderHead.txtAccount.setText(redsum+"");
                viewHoderHead.txtDate.setText(time);
                viewHoderHead.txtName.setText(MyApplication.getInstance().UserInfo.getUsername()+"共收到");
                Glide.with(mContext).load(MyApplication.getInstance().UserInfo.getAvatar()).error(R.mipmap.default_useravatar).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(mContext)).into(viewHoderHead.imgHead);
                break;
            case  RECEIVED_REDBAG_LIST_ITEM_TWO:

                ViewHolder viewHolder;
                if (convertView == null){
                    viewHolder = new ViewHolder();
                    convertView = inflater.inflate(R.layout.received_redbag_list_item2,null);
                    viewHolder.senderName= (TextView) convertView.findViewById(R.id.sent_redbag_user);
                    viewHolder.date = (TextView) convertView.findViewById(R.id.sent_redbag_item_date);
                    viewHolder.account = (TextView) convertView.findViewById(R.id.recevied_redbag_item_account);
                    convertView.setTag(viewHolder);
                }else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                viewHolder.senderName.setText(data.get(position-1).getUserName());
                viewHolder.date.setText(data.get(position-1).getCreatedTime());
                viewHolder.account.setText(data.get(position-1).getUseracount()+"");
                break;
        }

        return convertView;
    }

    class ViewHolder{

        TextView  senderName;
        TextView  date;
        TextView  account;
    }

    class ViewHolderHead{
        ImageView  imgHead;
        TextView   txtDate;
        TextView   txtName;
        TextView   txtAccount;
        TextView   txtReceivedBagNumber;
        TextView   txtShouqizuijia;
    }
}

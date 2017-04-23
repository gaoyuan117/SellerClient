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
import com.kaichaohulian.baocms.entity.SendRedBagListEntity;
import com.kaichaohulian.baocms.view.GlideCircleTransform;

import java.util.List;

/**
 * Created by liuyu on 2016/12/16.
 */

public class SendRedBagListAdapter extends BaseAdapter {
    public static final int SEND_REDBAG_LIST_ITEM_HEAD = 0;
    public static final int SEND_REDBAG_LIST_ITEM_TWO = 1;
    private Context mContext;
    private LayoutInflater inflater;
    private List<SendRedBagListEntity> data;

    private int redCount;
    private double redsum;
    private String years;

    public SendRedBagListAdapter(Context mContext, List<SendRedBagListEntity> data) {
        this.mContext = mContext;
        this.data = data;

        inflater = LayoutInflater.from(mContext);
    }

    public void setHead(int redCount, double redsum, String years){
        this.redCount = redCount;
        this.redsum = redsum;
        this.years = years;
    }

    @Override
    public int getCount() {
        return data.size()+1;
    }

    @Override
    public SendRedBagListEntity getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    @Override
    public  int getItemViewType(int position){
        if (position == 0){
            return  SEND_REDBAG_LIST_ITEM_HEAD;
        }else {
            return SEND_REDBAG_LIST_ITEM_TWO;
        }
    }

    @Override
    public  int getViewTypeCount(){
        return 2;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        switch (getItemViewType(i)) {
            case  SEND_REDBAG_LIST_ITEM_HEAD:
                ViewHolderHead viewHolderHead;
                if(view == null) {
                    viewHolderHead = new ViewHolderHead();
                    view = inflater.inflate(R.layout.sent_redbag_list_item1, null);
                    viewHolderHead.imgHead = (ImageView) view.findViewById(R.id.sent_redbag_head);
                    viewHolderHead.txtName = (TextView) view.findViewById(R.id.sent_redbag_name);
                    viewHolderHead.txtAccount = (TextView) view.findViewById(R.id.sent_redbag_account);
                    viewHolderHead.txtSentBagNumber = (TextView) view.findViewById(R.id.sent_redbag_number);
                    viewHolderHead.txtDate = (TextView) view.findViewById(R.id.sent_redbag_time);
                    view.setTag(viewHolderHead);
                }else {
                    viewHolderHead = (ViewHolderHead) view.getTag();
                }
                viewHolderHead.txtSentBagNumber.setText(redCount + "");
                viewHolderHead.txtAccount.setText(redsum + "");
                viewHolderHead.txtName.setText(MyApplication.getInstance().UserInfo.getUsername()+"共发出");
                viewHolderHead.txtDate.setText(years);
                Glide.with(mContext).load(MyApplication.getInstance().UserInfo.getAvatar()).error(R.mipmap.default_useravatar).diskCacheStrategy(DiskCacheStrategy.ALL).transform(new GlideCircleTransform(mContext)).into(viewHolderHead.imgHead);
                return view;
            case  SEND_REDBAG_LIST_ITEM_TWO:
                ViewHolder viewHolder;
                if (view == null) {
                    viewHolder = new ViewHolder();
                    view = inflater.inflate(R.layout.sent_redbag_list_item2, null);
                    viewHolder.redBagType = (TextView) view.findViewById(R.id.sent_redbag_bagtype);
                    viewHolder.redBagDate = (TextView) view.findViewById(R.id.sent_redbag_item_date);
                    viewHolder.redBagAccount = (TextView) view.findViewById(R.id.sent_redbag_item_account);
                    viewHolder.redBagGet = (TextView) view.findViewById(R.id.sent_redbag_item_fenshu);
                    viewHolder.txtfenshu = (TextView) view.findViewById(R.id.sent_redbag_item_fenshu);
                    view.setTag(viewHolder);
                } else {
                    viewHolder = (ViewHolder) view.getTag();
                }
                SendRedBagListEntity SendRedBagListEntity = getItem(i - 1);
                viewHolder.redBagType.setText(SendRedBagListEntity.getType() == 0 ? "普通红包" : "拼手气红包");
                viewHolder.redBagAccount.setText(data.get(i-1).getSum()+"");
                viewHolder.txtfenshu.setText(SendRedBagListEntity.getStatus());
                viewHolder.redBagDate.setText(SendRedBagListEntity.getCreatedTime());
                return view;
        }
        return view;
    }

    class ViewHolder{
        TextView redBagType;
        TextView redBagDate;
        TextView redBagAccount;
        TextView redBagGet;
        TextView  txtfenshu;
    }

    class ViewHolderHead{
        ImageView imgHead;
        TextView   txtDate;
        TextView   txtName;
        TextView   txtAccount;
        TextView   txtSentBagNumber;
    }
}

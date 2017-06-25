package com.kaichaohulian.baocms.ecdemo.ui.chatting.model;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.BaseHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.IDCardViewHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.view.ChattingItemContainer;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;

import org.json.JSONException;
import org.json.JSONObject;

public class IDCardRxRow extends BaseChattingRow {

    public IDCardRxRow(int type) {
        super(type);
    }

    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {
        if (convertView == null) {
            convertView = new ChattingItemContainer(inflater, R.layout.chatting_item_idcard_from);
            IDCardViewHolder holder = new IDCardViewHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, true));
        }
        return convertView;
    }

    @Override
    public int getChatViewType() {
        return ChattingRowType.DESCRIPTION_IDCARD_RECEIVED.ordinal();
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu, View targetView, ECMessage detail) {
        return false;
    }

    @Override
    protected void buildChattingData(Context context, BaseHolder baseHolder, ECMessage detail, int position) {
        IDCardViewHolder holder = (IDCardViewHolder) baseHolder;
        ECMessage message = detail;
        if (message != null) {
            ViewHolderTag holderTag = ViewHolderTag.createTag(detail,
                    ViewHolderTag.TagType.TAG_IM_IDCARD, position);
            ECTextMessageBody textBody = (ECTextMessageBody) message.getBody();
            String userData="";

            if(message.getUserData().equals("cardtype")){
                userData = textBody.getMessage();
            }else {
                userData = message.getUserData();
            }

            Log.e("gy", "名片数据："+userData);
            try {
                JSONObject jsonObject = new JSONObject(userData);
                Glide.with(context).load(jsonObject.getString("a")).placeholder(R.mipmap.default_useravatar).error(R.mipmap.default_useravatar).into(holder.avater);
                holder.username.setText(jsonObject.getString("n"));
                holder.phone.setText(jsonObject.getString("m"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            OnClickListener onClickListener = ((ChattingActivity) context).mChattingFragment.getChattingAdapter().getOnClickListener();
            getMsgStateResId(position, holder, detail, onClickListener);
            holder.relativeLayout.setTag(holderTag);
            holder.relativeLayout.setOnClickListener(onClickListener);
        }

    }

}

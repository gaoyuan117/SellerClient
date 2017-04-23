package com.kaichaohulian.baocms.ecdemo.ui.chatting.model;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.fastjson.JSON;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.TransferDetailActivity;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.BaseHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.TransferViewHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.view.ChattingItemContainer;
import com.yuntongxun.ecsdk.ECMessage;

import java.math.BigDecimal;

public class TransferRxRow extends BaseChattingRow {

    private String id;
    private ECMessage message;

    public TransferRxRow(int type) {
        super(type);
    }

    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {
        if (convertView == null) {
            convertView = new ChattingItemContainer(inflater, R.layout.chatting_item_transfer_from);
            TransferViewHolder holder = new TransferViewHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, true));
        }
        return convertView;
    }

    @Override
    public int getChatViewType() {
        return ChattingRowType.DESCRIPTION_TRANSFER_RECEIVED.ordinal();
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu, View targetView, ECMessage detail) {
        return false;
    }

    @Override
    protected void buildChattingData(final Context context, BaseHolder baseHolder, ECMessage detail, int position) {
        TransferViewHolder holder = (TransferViewHolder) baseHolder;
        message = detail;
        if (message != null) {
            String userData = message.getUserData();
            com.alibaba.fastjson.JSONObject jsonObj = (com.alibaba.fastjson.JSONObject) JSON.parse(userData);

            ViewHolderTag holderTag = ViewHolderTag.createTag(detail, ViewHolderTag.TagType.TAG_IM_TRANSFER, position);
            final String money = jsonObj.getString("money");
            String type = jsonObj.getString("type");


            BigDecimal bd = new BigDecimal(money);
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            holder.amountTV.setText("¥" + bd.toString());

            OnClickListener onClickListener = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userData = message.getUserData();
                    com.alibaba.fastjson.JSONObject jsonObj = (com.alibaba.fastjson.JSONObject) JSON.parse(userData);
                    String money = jsonObj.getString("money");
                    id = jsonObj.getString("id");

                    Intent transferDetailIntent = new Intent(context, TransferDetailActivity.class);
                    transferDetailIntent.putExtra("amount", money);
                    transferDetailIntent.putExtra("id", id);
                    context.startActivity(transferDetailIntent);
                }
            };
            getMsgStateResId(position, holder, detail, onClickListener);
            holder.transferRL.setOnClickListener(onClickListener);
        }
    }

}

package com.kaichaohulian.baocms.ecdemo.ui.chatting.model;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.BaseHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.TransferViewHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.view.ChattingItemContainer;
import com.kaichaohulian.baocms.transfer.TransferSuccessHolder;
import com.yuntongxun.ecsdk.ECMessage;

import java.math.BigDecimal;

/**
 * Created by gaoyuan on 2017/6/1.
 */

public class TransferSuc extends BaseChattingRow {

    public TransferSuc(int type) {
        super(type);
    }

    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {
        if (convertView == null) {
            convertView = new ChattingItemContainer(inflater, R.layout.chatting_item_transfer_suc);
            TransferSuccessHolder holder = new TransferSuccessHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, true));
        }
        return convertView;
    }

    @Override
    public int getChatViewType() {
        return ChattingRowType.DESCRIPTION_ZHUAN_RECEIVED.ordinal();
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu, View targetView, ECMessage detail) {
        return false;
    }

    @Override
    protected void buildChattingData(Context context, BaseHolder baseHolder, ECMessage detail, int position) {
        TransferSuccessHolder holder = (TransferSuccessHolder) baseHolder;
        ECMessage message = detail;
        if (message != null) {
            String userData = message.getUserData();
            com.alibaba.fastjson.JSONObject jsonObj = (com.alibaba.fastjson.JSONObject) JSON.parse(userData);

            ViewHolderTag holderTag = ViewHolderTag.createTag(detail, ViewHolderTag.TagType.TAG_IM_TRANSFER, position);
            String money = jsonObj.getString("money");
            String type = jsonObj.getString("type");

            BigDecimal bd = new BigDecimal(money);
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            holder.money.setText("¥" + bd.toString());
        }
    }
}
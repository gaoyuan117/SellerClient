package com.kaichaohulian.baocms.ecdemo.ui.chatting.model;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.BaseHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.IDCardViewHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.TransferViewHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.view.ChattingItemContainer;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.im.ECTextMessageBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class TransferTxRow extends BaseChattingRow {

    public TransferTxRow(int type) {
        super(type);
    }

    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {
        if (convertView == null) {
            convertView = new ChattingItemContainer(inflater, R.layout.chatting_item_transfer_to);
            TransferViewHolder holder = new TransferViewHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, true));
        }
        return convertView;
    }

    @Override
    public int getChatViewType() {
        return ChattingRowType.DESCRIPTION_TRANSFER_TRANSMIT.ordinal();
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu, View targetView, ECMessage detail) {
        return false;
    }

    @Override
    protected void buildChattingData(Context context, BaseHolder baseHolder, ECMessage detail, int position) {
        TransferViewHolder holder = (TransferViewHolder) baseHolder;
        ECMessage message = detail;
        if (message != null) {
            String userData = message.getUserData();
            com.alibaba.fastjson.JSONObject jsonObj = (com.alibaba.fastjson.JSONObject) JSON.parse(userData);

            ViewHolderTag holderTag = ViewHolderTag.createTag(detail, ViewHolderTag.TagType.TAG_IM_TRANSFER, position);
            String money = jsonObj.getString("money");
            String type = jsonObj.getString("type");

            BigDecimal bd = new BigDecimal(money);
            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
            holder.amountTV.setText("¥" + bd.toString());

//            OnClickListener onClickListener = new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ToastUtil.showMessage("1111111111111111111111111111111111111111111111111111111");
//                }
//            };

//            getMsgStateResId(position, holder, detail, onClickListener);
//            holder.transferRL.setOnClickListener(onClickListener);

        }
    }
}

package com.kaichaohulian.baocms.ecdemo.ui.chatting.model;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.RechargeActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.RedPacketConstant;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.BaseHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.RedPacketViewHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.redpacketutils.CheckRedPacketMessageUtil;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.view.ChattingItemContainer;
import com.yuntongxun.ecsdk.ECMessage;


/**
 * Created by ustc on 2016/6/24.
 */
public class RedPacketRxRow extends BaseChattingRow {
    public RedPacketRxRow(int type) {
        super(type);
    }

    @Override
    public View buildChatView(final LayoutInflater inflater, View convertView) {
        //we have a don't have a converView so we'll have to create a new one
        if (convertView == null) {
            convertView = new ChattingItemContainer(inflater, R.layout.chatting_item_redpacket_from);
            //use the view holder pattern to save of already looked up subviews
            RedPacketViewHolder holder = new RedPacketViewHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, true));
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = inflater.getContext();
                context.startActivity(new Intent(context, RechargeActivity.class));
            }
        });
        return convertView;
    }

    @Override
    public void buildChattingData(final Context context, BaseHolder baseHolder, ECMessage detail, int position) {
        RedPacketViewHolder holder = (RedPacketViewHolder) baseHolder;
        ECMessage message = detail;
        if (message != null) {
            if (message.getType() == ECMessage.Type.TXT) {
//                JSONObject jsonObject = CheckRedPacketMessageUtil.isRedPacketMessage(message);
//                if (jsonObject != null) {

                String userData = detail.getUserData();
                JSONObject jsonObj = (JSONObject) JSON.parse(userData);
                String greetingStr = "";
                try {
                    greetingStr = jsonObj.getString("money_greeting");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (TextUtils.isEmpty(greetingStr)) {
                    greetingStr = jsonObj.getString("e");
                }
                //清除文本框，和加载progressdialog
                holder.getGreetingTv().setText(greetingStr);
//                holder.getSponsorNameTv().setText(context.getResources().getString(R.string.ytx_luckymoney));

//                String packetType = jsonObject.getString(RedPacketConstant.MESSAGE_ATTR_RED_PACKET_TYPE);
//
//                if (!TextUtils.isEmpty(packetType) && TextUtils.equals(packetType, RedPacketConstant.GROUP_RED_PACKET_TYPE_EXCLUSIVE)) {
//                    holder.getPacketTypeTv().setVisibility(View.VISIBLE);
//                    holder.getPacketTypeTv().setText(context.getResources().getString(R.string.exclusive_red_packet));
//                } else {
//                    holder.getPacketTypeTv().setVisibility(View.GONE);
//                }

                ViewHolderTag holderTag = ViewHolderTag.createTag(message, ViewHolderTag.TagType.TAG_IM_TRANSFER_R, position);
                View.OnClickListener onClickListener = ((ChattingActivity) context).mChattingFragment.getChattingAdapter().getOnClickListener();
                holder.getBubble().setTag(holderTag);
                holder.getBubble().setOnClickListener(onClickListener);
//                }
            }
        }
    }

    @Override
    public int getChatViewType() {
        return ChattingRowType.REDPACKET_ROW_RECEIVED.ordinal();
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu, View targetView, ECMessage detail) {
        return false;
    }
}

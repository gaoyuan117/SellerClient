package com.kaichaohulian.baocms.ecdemo.ui.chatting.model;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.BaseHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.LocationViewHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.view.ChattingItemContainer;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.ecsdk.im.ECLocationMessageBody;

public class LocationRxRow extends BaseChattingRow {

    public LocationRxRow(int type) {
        super(type);
    }

    @Override
    public View buildChatView(LayoutInflater inflater, View convertView) {
        if (convertView == null) {
            convertView = new ChattingItemContainer(inflater, R.layout.chatting_item_location_from);
            LocationViewHolder holder = new LocationViewHolder(mRowType);
            convertView.setTag(holder.initBaseHolder(convertView, true));
        }
        return convertView;
    }

    @Override
    public int getChatViewType() {
        return ChattingRowType.LOCATION_ROW_RECEIVED.ordinal();
    }

    @Override
    public boolean onCreateRowContextMenu(ContextMenu contextMenu, View targetView, ECMessage detail) {
        return false;
    }

    @Override
    protected void buildChattingData(Context context, BaseHolder baseHolder, ECMessage detail, int position) {
        LocationViewHolder holder = (LocationViewHolder) baseHolder;
        ECMessage message = detail;
        if (message != null) {
            ViewHolderTag holderTag = ViewHolderTag.createTag(detail, ViewHolderTag.TagType.TAG_IM_LOCATION, position);

            ECLocationMessageBody textBody = (ECLocationMessageBody) message.getBody();
            holder.descTextView.setText(textBody.getTitle());
            OnClickListener onClickListener = ((ChattingActivity) context).mChattingFragment.getChattingAdapter().getOnClickListener();
            holder.relativeLayout.setTag(holderTag);
            holder.relativeLayout.setOnClickListener(onClickListener);
        }
    }

}

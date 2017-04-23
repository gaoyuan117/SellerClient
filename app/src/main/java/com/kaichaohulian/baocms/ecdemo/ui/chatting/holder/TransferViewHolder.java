package com.kaichaohulian.baocms.ecdemo.ui.chatting.holder;


import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;

public class TransferViewHolder extends BaseHolder {
    public TextView amountTV;
    public RelativeLayout transferRL;

    public TransferViewHolder(int type) {
        super(type);
    }

    @Override
    public TextView getReadTv() {
        return null;
    }

    public BaseHolder initBaseHolder(View baseView, boolean receive) {
        super.initBaseHolder(baseView);
        amountTV = (TextView) baseView.findViewById(R.id.transfers_amount);
        transferRL = (RelativeLayout) baseView.findViewById(R.id.re_rl);

        if (receive) {
            type = 26;
        } else {
            type = 27;
        }
        return this;
    }

}

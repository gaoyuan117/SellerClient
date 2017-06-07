package com.kaichaohulian.baocms.transfer;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.BaseHolder;

/**
 * Created by gaoyuan on 2017/6/1.
 */

public class TransferSuccessHolder extends BaseHolder {
    public TextView money;

    public TransferSuccessHolder(int type) {
        super(type);
    }

    @Override
    public TextView getReadTv() {
        return null;
    }


    public BaseHolder initBaseHolder(View baseView, boolean receive) {
        super.initBaseHolder(baseView);
        money = (TextView) baseView.findViewById(R.id.transfers_amount);

        if (receive) {
            type = 24;
        } else {
            type = 25;
        }
        return this;
    }
}

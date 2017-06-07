package com.kaichaohulian.baocms.ecdemo.ui.chatting.model;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.alibaba.fastjson.JSON;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.TransferDetailActivity;
import com.kaichaohulian.baocms.activity.TransferSuccessActivity;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.BaseHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.holder.TransferViewHolder;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.view.ChattingItemContainer;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yuntongxun.ecsdk.ECMessage;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class TransferRxRow extends BaseChattingRow {

    private String id;

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
        final ECMessage message;
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
                    if (TextUtils.isEmpty(id)) {
                        id = jsonObj.getString("transferID");
                    }
                    String uid = jsonObj.getString("uid");

                    tansferSure(context, id, uid, money);
                }
            };
            getMsgStateResId(position, holder, detail, onClickListener);
            holder.transferRL.setOnClickListener(onClickListener);
        }
    }


    private void tansferSure(final Context context, final String transferId, final String uid, final String money) {
        RequestParams params = new RequestParams();
        params.put("transferId", transferId);
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        HttpUtil.post(Url.ISRECEIVE, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getString("code").equals("0")) {
                        Intent intent = new Intent(context, TransferDetailActivity.class);
                        intent.putExtra("amount", money);
                        intent.putExtra("id", transferId);
                        intent.putExtra("uid", uid);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, TransferSuccessActivity.class);
                        intent.putExtra("amount", money);
                        context.startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}

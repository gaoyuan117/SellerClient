package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseListAdapter;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingFragment;
import com.kaichaohulian.baocms.entity.InviteDetailEntity;
import com.kaichaohulian.baocms.entity.InviteReciverEntity;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzwzz on 2017/5/14.
 */

public class InviteDetailGrid2Adapter extends BaseListAdapter {
    private SimpleDateFormat format;
    private Object obj;

    public InviteDetailGrid2Adapter(Context context, @Nullable List data, @Nullable Object obj) {
        super(context, data);
        this.obj = obj;
        format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    }

    @Override
    public int getCount() {
        try{
            if (obj != null) {
                return 1;
            }
            if (data == null) {
                return 0;
            } else {
                return data.size() == 0 ? 0 : data.size();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return super.getItem(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = View.inflate(context, layoutIds[0], null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        if (obj != null) {
            DataBindForObj((InviteReciverEntity.UserBean) obj,vh);
        } else {
            DataBind(vh, i);
        }
        return view;
    }

    private void DataBindForObj(final InviteReciverEntity.UserBean data, ViewHolder vh){
        try {
            vh.name.setText(data.username);
            Glide.with(context).load(data.avator).into(vh.avatar);
            vh.btnInviteComplaint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "投诉", Toast.LENGTH_SHORT).show();
                }
            });
            vh.btnInviteEvaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "评价", Toast.LENGTH_SHORT).show();
                }
            });
            vh.chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChattingActivity.class);
                    intent.putExtra(ChattingFragment.RECIPIENTS, data.phoneNumber);
                    intent.putExtra(ChattingFragment.CONTACT_USER, data.username);
                    intent.putExtra("user_id", data.user_id);
                    intent.putExtra(ChattingFragment.CUSTOMER_SERVICE, false);
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.d("InviteDetailGridAdapter", "e:" + e);
        }
    }

    private void DataBind(ViewHolder vh, int i) {
        try {
            final InviteDetailEntity.ListBean entity = (InviteDetailEntity.ListBean) getItem(i);
            if (entity.user_id == 0) {
                data.remove(i);
                notifyDataSetChanged();
            }
            vh.name.setText(entity.username + "");
            Glide.with(context).load(entity.avator).into(vh.avatar);
            vh.btnInviteComplaint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 客服界面
                }
            });
            vh.btnInviteEvaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 评价界面
                }
            });
            vh.chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChattingActivity.class);
                    intent.putExtra(ChattingFragment.RECIPIENTS, entity.phoneNumber+"");
                    intent.putExtra(ChattingFragment.CONTACT_USER, entity.username+"");
                    intent.putExtra("user_id", entity.user_id);
                    intent.putExtra(ChattingFragment.CUSTOMER_SERVICE, false);
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.d("InviteDetailGridAdapter", "e:" + e);
        }
    }

    class ViewHolder {
        @BindView(R.id.img_invited_info_avatar)
        ImageView avatar;
        @BindView(R.id.tv_invited_info_name)
        TextView name;
        @BindView(R.id.img_invite_info_chat)
        ImageView chat;
        @BindView(R.id.btn_invite_complaint)
        Button btnInviteComplaint;
        @BindView(R.id.btn_invite_evaluate)
        Button btnInviteEvaluate;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

}

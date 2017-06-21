package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.InvitationmgActivity;
import com.kaichaohulian.baocms.base.BaseListAdapter;
import com.kaichaohulian.baocms.entity.InviteDetailEntity;
import com.kaichaohulian.baocms.entity.InviteOfFindEntity;
import com.kaichaohulian.baocms.entity.MyInviteEntity;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xzwzz on 2017/5/9.
 */

public class MyInviteListAdapter extends BaseListAdapter {


    public MyInviteListAdapter(Context context, @Nullable List data) {
        super(context, data);
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = View.inflate(context, layoutIds[0], null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

            MyInviteEntity entity = (MyInviteEntity) getItem(position);
            holder.titleInvite.setText(entity.getInvite().getTitle());
            holder.adressInvite.setText(entity.getInvite().getInviteAddress());
            String time=entity.getInvite().getInvateTime();
            time = entity.getInvite().getInvateTime().substring(0, entity.getInvite().getInvateTime().length() - 3);
            holder.timeInvite.setText(time);
            switch (entity.getInvite().getStatus()){
                case 0:
                case 1:
                    holder.statusInvite.setText("进行中");
                    break;
                case 2:
                    holder.statusInvite.setText("已完成");
                    break;
                case 3:
                    holder.statusInvite.setText("已失效");
                    break;
            }

        return view;
    }

    class ViewHolder {
        @BindView(R.id.time_invite)
        TextView timeInvite;
        @BindView(R.id.status_invite)
        TextView statusInvite;
        @BindView(R.id.title_invite)
        TextView titleInvite;
        @BindView(R.id.adress_invite)
        TextView adressInvite;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

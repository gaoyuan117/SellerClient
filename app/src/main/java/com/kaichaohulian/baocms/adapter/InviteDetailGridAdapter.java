package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.kaichaohulian.baocms.activity.InvitedetailActivity;
import com.kaichaohulian.baocms.base.BaseListAdapter;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.InviteDetailEntity;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xzwzz on 2017/5/12.
 */

public class InviteDetailGridAdapter extends BaseListAdapter {
    private SimpleDateFormat format;
    private int type;

    public InviteDetailGridAdapter(Context context, @Nullable List data) {
        super(context, data);
        format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    }



    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder1 vh = null;
        if (view == null) {
                    view = View.inflate(context, layoutIds[0], null);
                    vh = new ViewHolder1(view);
                    view.setTag(vh);

        } else {
                vh = (ViewHolder1) view.getTag();
        }
        DataBind(vh,i);
        return view;
    }

    public List getList(){
        return data;
    }

    private void DataBind(ViewHolder1 vh1, int i) {
            try {
                InviteDetailEntity.ListBean entity = (InviteDetailEntity.ListBean) getItem(i);
                if (entity.user_id == 0) {
                    data.remove(i);
                    notifyDataSetChanged();
                }
                vh1.name.setText(entity.username + "");
                switch (entity.inviteStatus) {
                    case 0:
                        vh1.state.setText("已拒绝");
                        vh1.state.setTextColor(Color.parseColor("#54da4a"));
                        break;
                    case 1:
                        vh1.state.setText("已接受");
                        vh1.state.setTextColor(Color.parseColor("#fb5986"));
                        break;
                    default:
                        vh1.state.setText("已发送邀请");
                        break;
                }
                vh1.time.setText(format.format(entity.createdTime));
                Glide.with(context).load(entity.avator).into(vh1.avatar);
            } catch (Exception e) {
                Log.d("InviteDetailGridAdapter", "e:" + e);
            }
//        } else if (vh1 == null && vh2 != null) {
//            try {
//                InviteDetailEntity.ListBean entity = (InviteDetailEntity.ListBean) getItem(i);
//                if (entity.user_id == 0) {
//                    data.remove(i);
//                    notifyDataSetChanged();
//                }
//                vh2.name.setText(entity.username + "");
//                vh2.time.setText(format.format(entity.createdTime));
//                Glide.with(context).load(entity.avator).into(vh2.avatar);
//                vh2.btnInviteComplaint.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(context, "投诉", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                vh2.btnInviteEvaluate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(context, "评价", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            } catch (Exception e) {
//                Log.d("InviteDetailGridAdapter", "e:" + e);
//            }
//        }
    }



    class ViewHolder1 {
        @BindView(R.id.img_invited_info_avatar)
        ImageView avatar;
        @BindView(R.id.tv_invited_info_name)
        TextView name;
        @BindView(R.id.tv_invited_info_time)
        TextView time;
        @BindView(R.id.img_invite_info_chat)
        ImageView chat;
        @BindView(R.id.tv_invite_info_state)
        TextView state;

        public ViewHolder1(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

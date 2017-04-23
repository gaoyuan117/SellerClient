package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.entity.ApplyAndNoticeEntity;
import com.kaichaohulian.baocms.entity.UserInfo;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.HanziToPinyin;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.List;

public class NewFriendsAdapter extends BaseAdapter {
    private Context mContext;
    private List<ApplyAndNoticeEntity> List;
    private int total = 0;

    public NewFriendsAdapter(Context mContext, List<ApplyAndNoticeEntity> List) {
        this.mContext = mContext;
        this.List = List;
        total = List.size();
    }

    @Override
    public int getCount() {
        return List.size();
    }

    @Override
    public ApplyAndNoticeEntity getItem(int position) {
        return List.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.item_newfriendsmsag, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        final ApplyAndNoticeEntity msg = getItem(position);

        holder.tv_name.setText(msg.getNickname());
        holder.tv_reason.setText(msg.getMessage());
        holder.tv_added.setText("已添加");
        if (msg.getStatus().equals("接受")) {
            holder.tv_added.setVisibility(View.GONE);
            holder.btn_add.setVisibility(View.VISIBLE);
            holder.btn_add.setTag(msg);
            holder.btn_add.setText("接受");
            holder.btn_add.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    //同意好友请求或者群申请
                    acceptInvitation(holder.btn_add, holder.tv_added, position);
                }

            });
        } else  if (msg.getStatus().equals("等待验证")) {
            holder.tv_added.setVisibility(View.VISIBLE);
            holder.btn_add.setVisibility(View.GONE);
            holder.tv_added.setText("等待验证");
        }else  if (msg.getStatus().equals("已添加")) {
            holder.tv_added.setVisibility(View.VISIBLE);
            holder.btn_add.setVisibility(View.GONE);
        } else  if (msg.getStatus().equals("添加")) {
            holder.tv_added.setVisibility(View.GONE);
            holder.btn_add.setVisibility(View.VISIBLE);
            holder.btn_add.setTag(msg);
            holder.btn_add.setText("添加");
            holder.btn_add.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 添加通讯录好友
                    addFriends(msg.getUserId()+"", holder.btn_add, holder.tv_added, position);
                }

            });

        }
        Glide.with(mContext).load(msg.getAvatar()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.default_useravatar).into(holder.iv_avatar);
        return convertView;
    }


    private class ViewHolder {
        ImageView iv_avatar;
        TextView tv_name;
        TextView tv_reason;
        TextView tv_added;
        Button btn_add;

        public ViewHolder(View convertView){
            iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            tv_reason = (TextView) convertView.findViewById(R.id.tv_reason);
            tv_added = (TextView) convertView.findViewById(R.id.tv_added);
            btn_add = (Button) convertView.findViewById(R.id.btn_add);
        }
    }


    private void acceptInvitation(final Button btn_add, final TextView tv_added, final int position) {
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("requestId", List.get(position).getId());
        params.put("action", "ACCEPT");
        params.put("addFriend", "true");
        HttpUtil.post(Url.friends_handle, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("通知与申请：", response.toString());
                    if (response.getInt("code") == 0) {
                        tv_added.setVisibility(View.VISIBLE);
                        btn_add.setVisibility(View.GONE);
                        List.get(position).setStatus("已添加");
                    }
                    DBLog.showToast(response.getString("errorDescription"), mContext);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {}

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                DBLog.showToast("请求服务器失败", mContext);
                DBLog.e("tag", statusCode + ":" + responseString);
            }
        });
    }

    public void addFriends(String friendId,final Button btn_add, final TextView tv_added, final int position){
        ShowDialog.showDialog(mContext, "正在添加...", false, null);
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("friendId", friendId);
        params.put("message", "请求添加您为好友");
        HttpUtil.get(Url.friends_apply, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    DBLog.e("添加好友：", response.toString());
                    DBLog.showToast(response.getString("errorDescription"),mContext);
                    if (response.getInt("code") == 0) {
                        tv_added.setVisibility(View.VISIBLE);
                        btn_add.setVisibility(View.GONE);
                        List.get(position).setStatus("添加");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    ShowDialog.dissmiss();
                }
            }

            @Override
            public void onFinish() {}

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                DBLog.showToast("请求服务器失败", mContext);
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }


    /**
     * 设置hearder属性，方便通讯中对联系人按header分类显示，以及通过右侧ABCD...字母栏快速定位联系人
     *
     * @param username
     * @param user
     */
    protected void setUserHearder(String username, UserInfo user) {
        String headerName = null;
        if (!TextUtils.isEmpty(user.getUsername())) {
            headerName = user.getUsername();
        } else {
            headerName = user.getUsername();
        }
        headerName = headerName.trim();
        if (username.equals(UserInfo.NEW_FRIENDS_USERNAME)) {
            user.setHeader("");
        } else if (Character.isDigit(headerName.charAt(0))) {
            user.setHeader("#");
        } else {
            user.setHeader(HanziToPinyin.getInstance()
                    .get(headerName.substring(0, 1)).get(0).target.substring(0,
                    1).toUpperCase());
            char header = user.getHeader().toLowerCase().charAt(0);
            if (header < 'a' || header > 'z') {
                user.setHeader("#");
            }
        }
    }
}

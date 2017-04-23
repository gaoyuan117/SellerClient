package com.kaichaohulian.baocms.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.entity.ApplyAndNoticeEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的好友Adapter实现
 * Created by Administrator on 2016/12/13 0013.
 */
public class DirectoriesFriendAdapter extends ArrayAdapter<ApplyAndNoticeEntity> implements SectionIndexer {

    private Context mContext;
    List<String> list;
    List<ApplyAndNoticeEntity> userList;
    List<ApplyAndNoticeEntity> copyUserList;
    private LayoutInflater layoutInflater;
    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;
    private int res;
    public MyFilter myFilter;

    @SuppressLint("SdCardPath")
    public DirectoriesFriendAdapter(Context context, int resource, List<ApplyAndNoticeEntity> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.res = resource;
        this.userList = objects;
        copyUserList = new ArrayList<ApplyAndNoticeEntity>();
        copyUserList.addAll(objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(res, null);
        }

        ImageView iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);

        TextView nameTextview = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvHeader = (TextView) convertView.findViewById(R.id.header);
        View view_temp = convertView.findViewById(R.id.view_temp);
        final Button addfriend = (Button) convertView.findViewById(R.id.add);
        TextView tv_number = (TextView) convertView.findViewById(R.id.tv_number);

        Log.e("cdh", "view_temp=" + view_temp);
        final ApplyAndNoticeEntity user = getItem(position);
        if (user == null)
            Log.d("ContactAdapter", position + "");
        // 设置nick，demo里不涉及到完整user，用username代替nick显示

        String header = user.getHeader();
        String usernick = user.getNickname();
        String useravatar = user.getAvatar();

        if (position == 0 || header != null
                && !header.equals(getItem(position - 1).getHeader())) {
            if ("".equals(header)) {
                tvHeader.setVisibility(View.GONE);
                view_temp.setVisibility(View.VISIBLE);
            } else {
                tvHeader.setVisibility(View.VISIBLE);
                tvHeader.setText(header);
                view_temp.setVisibility(View.GONE);
            }
        } else {
            tvHeader.setVisibility(View.GONE);
            view_temp.setVisibility(View.VISIBLE);
        }
        // 显示申请与通知item

        if (StringUtils.isEmpty(usernick)) {
            nameTextview.setText("未命名");
        } else {
            nameTextview.setText(usernick);
        }
        if (!StringUtils.isEmpty(useravatar)) {
            Glide.with(parent.getContext()).load(Url.PIC_ROOT + useravatar).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.default_useravatar).into(iv_avatar);
        } else {
            iv_avatar.setImageResource(R.mipmap.default_useravatar);
        }
        addfriend.setText(user.getStatus());
        if (user.getStatus().equals("已添加")) {
            addfriend.setClickable(false);
            addfriend.setBackgroundResource(R.color.transparent);
            addfriend.setTextColor(mContext.getResources().getColor(R.color.im_social_dig_name_bg));
        } else if (user.getStatus().equals("添加")) {
            addfriend.setBackgroundResource(R.mipmap.add_contactbtn);
            addfriend.setTextColor(mContext.getResources().getColor(R.color.white));
            addfriend.setClickable(true);
            addfriend.setText("添加");
            addfriend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 添加通讯录好友
                    addFriends(user.getUserId() + "", position);
                }

            });
        }

        return convertView;
    }

    @Override
    public ApplyAndNoticeEntity getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    public int getPositionForSection(int section) {
        return positionOfSection.get(section);
    }

    public int getSectionForPosition(int position) {
        return sectionOfPosition.get(position);
    }

    @Override
    public Object[] getSections() {
        positionOfSection = new SparseIntArray();
        sectionOfPosition = new SparseIntArray();
        int count = getCount();
        list = new ArrayList<String>();
        list.add(getContext().getString(R.string.search_header));
        positionOfSection.put(0, 0);
        sectionOfPosition.put(0, 0);
        for (int i = 1; i < count; i++) {

            String letter = getItem(i).getHeader();
            System.err.println("contactadapter getsection getHeader:" + letter
                    + " name:" + getItem(i).getNickname());
            int section = list.size() - 1;
            if (list.get(section) != null && !list.get(section).equals(letter)) {
                list.add(letter);
                section++;
                positionOfSection.put(section, i);
            }
            sectionOfPosition.put(i, section);
        }
        return list.toArray(new String[list.size()]);
    }

    @Override
    public Filter getFilter() {
        if (myFilter == null) {
            myFilter = new MyFilter(userList);
        }
        return myFilter;
    }

    private class MyFilter extends Filter {
        List<ApplyAndNoticeEntity> mList = null;

        public MyFilter(List<ApplyAndNoticeEntity> myList) {
            super();
            this.mList = myList;
        }

        @Override
        protected synchronized FilterResults performFiltering(
                CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mList == null) {
                mList = new ArrayList<ApplyAndNoticeEntity>();
            }
            if (prefix == null || prefix.length() == 0) {
                results.values = copyUserList;
                results.count = copyUserList.size();
            } else {
                String prefixString = prefix.toString();
                final int count = mList.size();
                final ArrayList<ApplyAndNoticeEntity> newValues = new ArrayList<ApplyAndNoticeEntity>();
                for (int i = 0; i < count; i++) {
                    final ApplyAndNoticeEntity user = mList.get(i);
                    String username = user.getNickname();

                    if (username.startsWith(prefixString)) {
                        newValues.add(user);
                    } else {
                        final String[] words = username.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with
                        // space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {
                                newValues.add(user);
                                break;
                            }
                        }
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected synchronized void publishResults(CharSequence constraint,
                                                   FilterResults results) {
            userList.clear();
            userList.addAll((List<ApplyAndNoticeEntity>) results.values);
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }

    public void addFriends(String friendId, final int position) {
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
                    DBLog.showToast(response.getString("errorDescription"), mContext);
                    if (response.getInt("code") == 0) {
                        getItem(position).setStatus("已添加");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ShowDialog.dissmiss();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                DBLog.showToast("请求服务器失败", mContext);
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }
}

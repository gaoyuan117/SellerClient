package com.kaichaohulian.baocms.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.ContactFriendsEntity;
import com.kaichaohulian.baocms.utils.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by xzwzz on 2017/5/3.
 */

public class AdvertMassSelectAdapter extends ConactAdapter implements SectionIndexer {
    List<String> list;
    List<ContactFriendsEntity> userList;
    List<ContactFriendsEntity> copyUserList;
    private LayoutInflater layoutInflater;
    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;
    private int res;
    public MyFilter myFilter;
    private HashSet<ContactFriendsEntity> set;
    @SuppressLint("SdCardPath")
    public AdvertMassSelectAdapter(Context context, int resource, List<ContactFriendsEntity> objects) {
        super(context, resource, objects);
        this.res = resource;
        this.userList = objects;
        copyUserList = new ArrayList<>();
        copyUserList.addAll(objects);
        layoutInflater = LayoutInflater.from(context);
        set=new HashSet<>();
    }


    public HashSet GetSet(){
        return set;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = layoutInflater.inflate(res, null);
            vh=new ViewHolder(convertView);
            vh.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        set.add(getItem(position));
                    }else{
                        set.remove(getItem(position));
                    }
                }
            });
            convertView.setTag(vh);

        }else{
            vh= (ViewHolder) convertView.getTag();
        }


        ContactFriendsEntity user = getItem(position);
        if (user == null)
            Log.d("ContactAdapter", position + "");
        // 设置nick，demo里不涉及到完整user，用username代替nick显示

        String header = user.getHeader();
        String usernick = user.getUsername();
        String useravatar = user.getAvatar();

        if (position == 0 || header != null
                && !header.equals(getItem(position - 1).getHeader())) {
            if ("".equals(header)) {
                vh.tvHeader.setVisibility(View.GONE);
                vh.view_temp.setVisibility(View.VISIBLE);
            } else {
                vh.tvHeader.setVisibility(View.VISIBLE);
                vh.tvHeader.setText(header);
                vh.view_temp.setVisibility(View.GONE);
            }
        } else {
            vh.tvHeader.setVisibility(View.GONE);
            vh.view_temp.setVisibility(View.VISIBLE);
        }
        // 显示申请与通知item

        if (StringUtils.isEmpty(usernick)) {
            vh.nameTextview.setText("未命名");
        } else {
            vh.nameTextview.setText(usernick);
        }
        if (!StringUtils.isEmpty(useravatar)) {
            Glide.with(parent.getContext()).load(useravatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(vh.avatar);
        } else {
            vh.avatar.setImageResource(R.mipmap.default_useravatar);
        }


        return convertView;
    }



    class ViewHolder{
        TextView tvHeader,nameTextview;
        View view_temp;
        ImageView avatar;
        CheckBox select;

        public ViewHolder(View view) {
            avatar = (ImageView) view.findViewById(R.id.iv_avatar);
            select= (CheckBox) view.findViewById(R.id.iv_selector);
            nameTextview = (TextView) view.findViewById(R.id.tv_name);
            tvHeader = (TextView) view.findViewById(R.id.header);
            view_temp = view.findViewById(R.id.view_temp);
        }
    }

    @Override
    public ContactFriendsEntity getItem(int position) {
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
                    + " name:" + getItem(i).getUsername());
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
        List<ContactFriendsEntity> mList = null;

        public MyFilter(List<ContactFriendsEntity> myList) {
            super();
            this.mList = myList;
        }

        @Override
        protected synchronized FilterResults performFiltering(
                CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mList == null) {
                mList = new ArrayList<ContactFriendsEntity>();
            }
            if (prefix == null || prefix.length() == 0) {
                results.values = copyUserList;
                results.count = copyUserList.size();
            } else {
                String prefixString = prefix.toString();
                final int count = mList.size();
                final ArrayList<ContactFriendsEntity> newValues = new ArrayList<ContactFriendsEntity>();
                for (int i = 0; i < count; i++) {
                    final ContactFriendsEntity user = mList.get(i);
                    String username = user.getUsername();

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
            userList.addAll((List<ContactFriendsEntity>) results.values);
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}

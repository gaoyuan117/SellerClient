package com.kaichaohulian.baocms.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.entity.ShopEntity;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.StringUtils;
import com.kaichaohulian.baocms.view.GlideCircleTransform;

import java.util.ArrayList;
import java.util.List;

/**
 * 简单的好友Adapter实现
 * Created by Administrator on 2016/12/13 0013.
 */
public class BusinessAdapter extends ArrayAdapter<ShopEntity> implements SectionIndexer {

    List<String> list;
    List<ShopEntity> userList;
    List<ShopEntity> copyUserList;
    private LayoutInflater layoutInflater;
    private SparseIntArray positionOfSection;
    private SparseIntArray sectionOfPosition;
    private int res;
    public MyFilter myFilter;

    @SuppressLint("SdCardPath")
    public BusinessAdapter(Context context, int resource, List<ShopEntity> objects) {
        super(context, resource, objects);
        this.res = resource;
        this.userList = objects;
        copyUserList = new ArrayList<ShopEntity>();
        copyUserList.addAll(objects);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(res, null);
        }

        ImageView iv_avatar = (ImageView) convertView.findViewById(R.id.iv_avatar);

        TextView nameTextview = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tvHeader = (TextView) convertView.findViewById(R.id.header);
        View view_temp = convertView.findViewById(R.id.view_temp);
        Log.e("cdh", "view_temp=" + view_temp);
        ShopEntity ShopEntity = getItem(position);
        if (ShopEntity == null)
            Log.d("ContactAdapter", position + "");
        // 设置nick，demo里不涉及到完整user，用username代替nick显示

        String header = ShopEntity.getHeader();
        String ShopName = ShopEntity.getShopName();
        String logo = ShopEntity.getLogo();

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

        if (StringUtils.isEmpty(ShopName)) {
            nameTextview.setText("未命名");
        } else {
            nameTextview.setText(ShopName);
        }
        LogUtil.e("TRACE", "target shop icon : " + logo);
        if (!StringUtils.isEmpty(logo)) {
            switch (res) {
                case R.layout.item_contact_list:
                    Glide.with(parent.getContext()).load(logo).placeholder(R.mipmap.def_shop_bg)/*.transform(new GlideCircleTransform(parent.getContext()))*/.into(iv_avatar);
                    break;
                case R.layout.item_shopping_list:
                    Glide.with(parent.getContext()).load(Url.PIC_ROOT + logo).placeholder(R.mipmap.def_shop_bg)/*.transform(new GlideCircleTransform(parent.getContext()))*/.into(iv_avatar);
                    break;
            }
        } else {
            iv_avatar.setImageResource(R.mipmap.default_useravatar);
        }
        return convertView;
    }

    @Override
    public ShopEntity getItem(int position) {
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
            System.err.println("contactadapter getsection getHeader:" + letter + " name:" + getItem(i).getShopName());
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
        List<ShopEntity> mList = null;

        public MyFilter(List<ShopEntity> myList) {
            super();
            this.mList = myList;
        }

        @Override
        protected synchronized FilterResults performFiltering(
                CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mList == null) {
                mList = new ArrayList<ShopEntity>();
            }
            if (prefix == null || prefix.length() == 0) {
                results.values = copyUserList;
                results.count = copyUserList.size();
            } else {
                String prefixString = prefix.toString();
                final int count = mList.size();
                final ArrayList<ShopEntity> newValues = new ArrayList<ShopEntity>();
                for (int i = 0; i < count; i++) {
                    final ShopEntity ShopEntity = mList.get(i);
                    String username = ShopEntity.getShopName();

                    if (username.startsWith(prefixString)) {
                        newValues.add(ShopEntity);
                    } else {
                        final String[] words = username.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with
                        // space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {
                                newValues.add(ShopEntity);
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
        protected synchronized void publishResults(CharSequence constraint, FilterResults results) {
            userList.clear();
            userList.addAll((List<ShopEntity>) results.values);
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}

package com.kaichaohulian.baocms.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.entity.DialogListViewItem;

import java.util.List;

/**
 * Created by limengshuai on 2017/2/16.
 * 类描述：ButtomDialog适配器
 */

public class DialogListViewAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<DialogListViewItem> mList;

    public DialogListViewAdapter(Context context, List<DialogListViewItem> list) {
        layoutInflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.bottom_dialog_item, null);
            viewHolder.text = (TextView) convertView
                    .findViewById(R.id.txt_list_item);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DialogListViewItem dialogListViewItem = mList.get(position);
        CharSequence text = dialogListViewItem.getText();
        viewHolder.text.setText(text);

        return convertView;
    }

    class ViewHolder {
        TextView text;
    }

}

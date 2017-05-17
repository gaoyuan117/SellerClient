package com.kaichaohulian.baocms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.kaichaohulian.baocms.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/5/12.
 */

public class EvaluateAdapter extends BaseAdapter {
    private List<String> mList;
    private Context context;

    public EvaluateAdapter(List<String> mList, Context context) {
        this.mList = mList;
        this.context = context;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_evalute, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.cbItemEvaluateLabel.setText(mList.get(position));

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.cb_item_evaluate_label)
        CheckBox cbItemEvaluateLabel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

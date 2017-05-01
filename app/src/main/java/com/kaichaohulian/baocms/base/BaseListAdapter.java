package com.kaichaohulian.baocms.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by xzwzz on 2017/5/1.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {
    protected Context context;
    protected List<T> data;
    protected int[] layoutIds;
    public BaseListAdapter(Context context,@Nullable List<T> data) {
        this.context=context;
        this.data=data;
    }

    @Override
    public int getCount() {
        if(data==null){
            return 0;
        }else{
            return data.size()==0?1:data.size();
        }
    }

    @Override
    public int getViewTypeCount() {
        return layoutIds==null?1:layoutIds.length;
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setLayoutIds(@LayoutRes int... layoutIds){
        this.layoutIds=layoutIds;
    };

    @Override
    public abstract View getView(int i, View view, ViewGroup viewGroup);

}

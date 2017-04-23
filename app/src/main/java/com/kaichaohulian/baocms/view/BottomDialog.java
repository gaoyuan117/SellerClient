package com.kaichaohulian.baocms.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.DialogListViewAdapter;
import com.kaichaohulian.baocms.entity.DialogListViewItem;
import com.kaichaohulian.baocms.listener.OnBottomDialogItemOnClickListener;
import com.kaichaohulian.baocms.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by limengshuai on 2017/2/16.
 */

public class BottomDialog extends Dialog {

    private Context mCtx;
    private List<DialogListViewItem> mListItem;
    private View view;

    private ListView listView;
    private TextView cancel;

    public OnBottomDialogItemOnClickListener bottomDialogItemOnClickListener;

    public BottomDialog(Context context, int theme) {
        super(context, theme);
        initVars(context);
        setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        initViews();
    }

    private void initVars(Context context) {
        this.mCtx = context;
        mListItem = new ArrayList<DialogListViewItem>();
        view = LayoutInflater.from(context).inflate(R.layout.bottom_dialog, null);
    }

    private void initViews() {

        initListView();

        initTextView();
    }
    public void setOnBottomDialogItemOnClickListener(OnBottomDialogItemOnClickListener bottomDialogItemOnClickListener){
        this.bottomDialogItemOnClickListener = bottomDialogItemOnClickListener;
    }
    /**
     * 功能描述：初始化ListView控件
     *
     */
    private void initListView() {
        listView = (ListView) this.findViewById(R.id.txt_dialog_listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dismiss();
                if (bottomDialogItemOnClickListener != null) {
                    bottomDialogItemOnClickListener.onItemClick(
                            mListItem.get(position), position);
                }
            }
        });
    }

    /**
     * 功能描述：初始化"取消"按钮控件
     *
     */
    private void initTextView() {
        cancel = (TextView) this.findViewById(R.id.txt_cancel);
        cancel.setOnClickListener(new android.view.View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
            }

        });
    }

    /**
     * 功能描述：展示对话框
     *
     * @Time 2016年4月18日
     * @Author lizy18
     */
    public void show() {
        /**
         * 待显示的数据
         */
        listView.setAdapter(new DialogListViewAdapter(mCtx, mListItem));
        /**
         * 展示的位置
         */
        Window window = getWindow();
        window.setWindowAnimations(R.style.bottom_dialog_anim_style);
        android.view.WindowManager.LayoutParams attributes = window
                .getAttributes();
        attributes.x = 0;
        attributes.y = ScreenUtils.getScreenHeight(mCtx);
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
        attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 仿照QQ这设施透明度
        attributes.alpha = 0.7f;
        onWindowAttributesChanged(attributes);

        // 设置点击外围解散dialog
        this.setCanceledOnTouchOutside(true);

        super.show();
    }

    public void addItem(DialogListViewItem item) {
        if (item != null) {
            mListItem.add(item);
        } else {
            throw new NullPointerException("DialogListViewItem对象不能为空！");
        }
    }
}

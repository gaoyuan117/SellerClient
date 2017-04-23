package com.kaichaohulian.baocms.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.AddFriendsOneActivity;
import com.kaichaohulian.baocms.activity.CreatChatRoomActivity;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.view.zxing.activity.CaptureActivity;

/**
 * 仿微信对话框
 * Created by ljl on 2016/12/11.
 */
public class AddPopWindow extends PopupWindow {
    private View conentView;

   
	@SuppressLint("InflateParams")
	public AddPopWindow(final Activity context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popupwindow_add, null);
 
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

        RelativeLayout   re_chatroom =(RelativeLayout) conentView.findViewById(R.id.re_chatroom);
        RelativeLayout   re_addfriends =(RelativeLayout) conentView.findViewById(R.id.re_addfriends);
        RelativeLayout   re_saoyisao =(RelativeLayout) conentView.findViewById(R.id.re_saoyisao);

        re_chatroom.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                ActivityUtil.next(context, CreatChatRoomActivity.class);
                AddPopWindow.this.dismiss();
            }

        } );
        re_addfriends.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                ActivityUtil.next(context, AddFriendsOneActivity.class);
                AddPopWindow.this.dismiss();
            }
            
        } );
        re_saoyisao.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CaptureActivity.class);
                context.startActivityForResult(intent, AddFriendsOneActivity.REQUEST_CODE);
            }
        });
    }

    /**
     * 显示popupWindow
     * 
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }
}

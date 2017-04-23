package com.kaichaohulian.baocms.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.ui.contact.ContactSelectListActivity;

/**
 * 红包 界面
 */
public class RedBagActivity extends BaseActivity {

    private Button btnPingshouqi;
    private Button btnNormal;


    @Override
    public void setContent() {
        setContentView(R.layout.red_bag_layout);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("红包");
        setRightTitle("我的红包").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoDialog();
            }
        });
        btnPingshouqi = getId(R.id.btn_pingshouqi_redbag);
        btnNormal = getId(R.id.btn_putong_redbag);
    }

    public static final int REQ_GOURP_ID = 100;
    public static final int REQ_CONTRACT_ID = 101;

    @Override
    public void initEvent() {
        btnPingshouqi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent groupIntent = new Intent(getActivity(), GroupChatActivity.class);
                groupIntent.putExtra(GroupChatActivity.IS_SELECT_GROUPID, true);
                startActivityForResult(groupIntent, REQ_GOURP_ID);

//                ActivityUtil.next(getActivity(), GroupChatActivity.class);
            }
        });

        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent groupIntent = new Intent(getActivity(), SelectContactActionActivity.class);
                groupIntent.putExtra(GroupChatActivity.IS_SELECT_GROUPID, true);
                startActivityForResult(groupIntent, REQ_CONTRACT_ID);

//                Bundle Bundle = new Bundle();
//                Bundle.putInt("type", 0);
//                ActivityUtil.next(RedBagActivity.this, SendingRedBagActivity.class, Bundle);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_GOURP_ID) {
            try {
                String sessionid = data.getStringExtra("sessionid");
                String groupname = data.getStringExtra("groupname");
                String groupid = data.getStringExtra("groupid");

                Intent redpckIntent = new Intent(RedBagActivity.this, SendingRedBagActivity.class);
                redpckIntent.putExtra(SendingRedBagActivity.IS_FROM_PCK, true);
                redpckIntent.putExtra("sessionid", sessionid);
                redpckIntent.putExtra("groupname", groupname);
                redpckIntent.putExtra("groupid", groupid);
                redpckIntent.putExtra("type", 1);
                redpckIntent.putExtra("isGroup", "1");
                startActivity(redpckIntent);
                finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (requestCode == REQ_CONTRACT_ID) {
            try {
                String sessionid = data.getStringExtra("sessionid");
                Intent redpckIntent = new Intent(RedBagActivity.this, SendingRedBagActivity.class);
                redpckIntent.putExtra(SendingRedBagActivity.IS_FROM_PCK, true);
                redpckIntent.putExtra(SendingRedBagActivity.IS_SINGLE_SESSION, true);
                redpckIntent.putExtra("sessionid", sessionid);
                redpckIntent.putExtra("type", 0);
                redpckIntent.putExtra("isGroup", "0");
                startActivity(redpckIntent);
                finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void showPhotoDialog() {
        final android.app.AlertDialog dlg = new android.app.AlertDialog.Builder(this).create();
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alertdialog);
        // 为确认按钮添加事件,执行退出应用操作
        TextView tv_paizhao = (TextView) window.findViewById(R.id.tv_content1);
        tv_paizhao.setText("我发的红包");
        tv_paizhao.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SdCardPath")
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), SendRedBagActivity.class);
                dlg.cancel();
            }
        });
        TextView tv_xiangce = (TextView) window.findViewById(R.id.tv_content2);
        tv_xiangce.setText("收到的红包");
        tv_xiangce.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), ReceviedRedBagListActivity.class);
                dlg.cancel();
            }
        });
    }

    public void back(View view) {
        finish();
    }

}

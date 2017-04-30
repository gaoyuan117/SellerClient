package com.kaichaohulian.baocms.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.ui.contact.ContactSelectListActivity;
import com.kaichaohulian.baocms.util.GlideUtils;
import com.kaichaohulian.baocms.view.zxing.activity.CaptureActivity;

public class AddFriendsOneActivity extends BaseActivity {
    public final static int REQUEST_CODE = 0x0123;
    private TextView tv_search, WeChatID;
    private View scannerCode, showCode, phoneContact, shopping, Relative_LdAdd, Relative_mmGroup;
    private String id ="我的买家号:" + MyApplication.getInstance().UserInfo.getAccountNumber();
    private RelativeLayout rlCode;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_addfriends);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        tv_search = getId(R.id.tv_search);
        scannerCode = getId(R.id.scannerCode);
        showCode = getId(R.id.showCode);
        phoneContact = getId(R.id.phoneContact);
        shopping = getId(R.id.shopping);
        WeChatID = getId(R.id.WeChatID);
        Relative_LdAdd = getId(R.id.Relative_LdAdd);
        Relative_mmGroup = getId(R.id.Relative_mmGroup);
        rlCode = getId(R.id.Relative_code);
    }

    @Override
    public void initEvent() {
        tv_search.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), AddFriendsTwoActivity.class);
            }

        });
        if (id.equals("0")) {
            WeChatID.setText("我的帐号：未设置");
        } else {
            WeChatID.setText(id);
        }
        scannerCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        phoneContact.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtil.next(getActivity(), ContactSelectListActivity.class);
                ActivityUtil.next(getActivity(), DirectoriesFriendActivity.class);
            }
        });
        shopping.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), BusinessActivity.class);
            }
        });
        Relative_LdAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                ActivityUtil.next(getActivity(), RadarActivity.class);
                Intent intent = new Intent(AddFriendsOneActivity.this, AdvancedRadarActivity.class);
                startActivity(intent);
            }
        });
        Relative_mmGroup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), RelativeMMGroupActivity.class);
            }
        });
        rlCode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                codeDialog();
            }
        });
    }

    /**
     * 二维码对话框
     */
    private void codeDialog(){
        //TODO 实例化对话框控件
        View dialogView = View.inflate(this,R.layout.dialog_code,null);
        ImageView avatar = (ImageView) dialogView.findViewById(R.id.img_code_avatar);
        ImageView codeImg = (ImageView) dialogView.findViewById(R.id.img_code);
        TextView name = (TextView) dialogView.findViewById(R.id.tv_code_name);
        TextView address = (TextView) dialogView.findViewById(R.id.tv_code_adress);
        name.setText(MyApplication.getInstance().UserInfo.getUsername());
        address.setText(MyApplication.getInstance().UserInfo.getDistrictId());
        Glide.with(MyApplication.getInstance())
                .load(MyApplication.getInstance().UserInfo.getAvatar())
                .error(R.mipmap.default_useravatar)
                .crossFade()
                .into(avatar);
        Glide.with(MyApplication.getInstance())
                .load(MyApplication.getInstance().UserInfo.getQrCode())
                .error(R.mipmap.default_image)
                .crossFade()
                .into(codeImg);
        Dialog dialog = new Dialog(this,R.style.MyDialogStyle);
        dialog.setContentView(dialogView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CaptureActivity.RESULT_TYPE) == CaptureActivity.RESULT_SUCCESS) {
                    try {
                        String result = bundle.getString(CaptureActivity.RESULT_STRING);
                        String Code[] = result.split(":");
                        if (Code[0].equals("addFriendCode")) {
                            Intent mIntent = new Intent(AddFriendsOneActivity.this, AddFriendsTwoActivity.class);
                            mIntent.putExtra("addFriendCode", Code[1] + "");
                            startActivity(mIntent);
                        } else {
                            showToastMsg("请扫描正确的二维码");
                        }
                    } catch (Exception e) {
                        showToastMsg("请扫描正确的二维码");
                    }
                } else if (bundle.getInt(CaptureActivity.RESULT_TYPE) == CaptureActivity.RESULT_FAILED) {
                    showToastMsg("解析二维码失败");
                }
            }
        }
    }

    public void back(View view) {
        finish();
    }
}

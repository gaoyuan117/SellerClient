package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.ecdemo.ui.contact.ContactSelectListActivity;
import com.kaichaohulian.baocms.view.zxing.activity.CaptureActivity;

public class AddFriendsOneActivity extends BaseActivity {
    public final static int REQUEST_CODE = 0x0123;
    private TextView tv_search, WeChatID;
    private View scannerCode, showCode, phoneContact, shopping, Relative_LdAdd, Relative_mmGroup;
    private String id ="我的买家号:" + MyApplication.getInstance().UserInfo.getAccountNumber();

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

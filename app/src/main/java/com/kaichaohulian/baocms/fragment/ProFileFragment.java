package com.kaichaohulian.baocms.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.DownloadService;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.UserInfoManager;
import com.kaichaohulian.baocms.activity.AdvertMgActivity;
import com.kaichaohulian.baocms.activity.AdvertmassActivity;
import com.kaichaohulian.baocms.activity.InvitationmgActivity;
import com.kaichaohulian.baocms.activity.MeAboutActivity;
import com.kaichaohulian.baocms.activity.MeSettingsActivity;
import com.kaichaohulian.baocms.activity.MyAlbumActivity;
import com.kaichaohulian.baocms.activity.OnlineServiceActivity;
import com.kaichaohulian.baocms.activity.PersonalActivity;
import com.kaichaohulian.baocms.activity.PocketActivity;
import com.kaichaohulian.baocms.activity.SendinvitationActivity;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseFragment;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.CommonEntity;
import com.kaichaohulian.baocms.entity.VersionBean;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.utils.SharedPrefsUtil;
import com.nostra13.universalimageloader.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的
 * Created by ljl on 2016/12/11.
 */
@SuppressLint("ValidFragment")
public class ProFileFragment extends BaseFragment {


    private TextView me_head_name, me_head_phone, me_version;
    private TextView me_buyer_number;

    private ImageView im_QrCode;
    private ImageView me_head_icon;


    public ProFileFragment(MyApplication myApplication, Activity activity, Context context) {
        super(myApplication, activity, context);
    }

    @Override
    public void setContent() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.me_layout, null);
        ButterKnife.bind(this, mView);
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPrefsUtil.putValue(getActivity(), MyApplication.getInstance().UserInfo.getPhoneNumber(), MyApplication.getInstance().UserInfo.getAvatar() + "-x-" + MyApplication.getInstance().UserInfo.getUsername());
        SharedPrefsUtil.putValue(getActivity(), MyApplication.getInstance().UserInfo.getUserId() + "", MyApplication.getInstance().UserInfo.getAvatar() + "-x-" + MyApplication.getInstance().UserInfo.getUsername());
    }

    @Override
    public void initView() {
        me_head_icon = getId(R.id.me_head_icon);
        me_buyer_number = getId(R.id.me_buyer_number);
        me_head_name = getId(R.id.me_head_name);
        me_head_phone = getId(R.id.me_buyer_phone);
        me_version = getId(R.id.tv_version_num);


        im_QrCode = getId(R.id.im_QrCode);
        Glide.with(MyApplication.getInstance()).load(MyApplication.getInstance().UserInfo.getAvatar()).error(R.mipmap.default_useravatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(me_head_icon);
        me_head_name.setText(MyApplication.getInstance().UserInfo.getUsername());
        me_head_phone.setText("账号:" + MyApplication.getInstance().UserInfo.getPhoneNumber() + "");
        String userid = String.valueOf(MyApplication.getInstance().UserInfo.getUserId());

        if (!TextUtils.isEmpty(userid) && !"null".equals(userid)) {
            me_buyer_number.setText("ID:" + userid);
        } else {
            me_buyer_number.setText("暂未获取到账号");
        }

        Glide.with(MyApplication.getInstance()).load(Url.BASE_URL + MyApplication.getInstance().UserInfo.getQrCode()).diskCacheStrategy(DiskCacheStrategy.ALL).into(im_QrCode);
    }

    @Override
    public void initData() {
        checkVersion();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initView();
        }
    }

    @Override
    public void initEvent() {

    }


    @OnClick({R.id.me_name, R.id.me_relativelayout_MassAdvertising, R.id.me_relativelayout_Advertising_manager, R.id.me_relativelayout_SendInvitation, R.id.me_relativelayout_invitationManager, R.id.me_relativelayout_album, R.id.me_relativelayout_pocket, R.id.me_relativelayout_about, R.id.me_relativelayout_OnlineService, R.id.me_relativelayout_settings, R.id.me_relativelayout_version})
    public void onClick(View view) {
        switch (view.getId()) {
            //个人信息
            case R.id.me_name:
                ActivityUtil.next(getActivity(), PersonalActivity.class);
                break;
            //群发广告
            case R.id.me_relativelayout_MassAdvertising:
                ActivityUtil.next(getActivity(), AdvertmassActivity.class);
                break;
            //广告管理
            case R.id.me_relativelayout_Advertising_manager:
                ActivityUtil.next(getActivity(), AdvertMgActivity.class);
                break;
            //发送邀请
            case R.id.me_relativelayout_SendInvitation:
                ActivityUtil.next(getActivity(), SendinvitationActivity.class);
                break;
            //邀请管理
            case R.id.me_relativelayout_invitationManager:
                Intent intent2 = new Intent(getActivity(), InvitationmgActivity.class);
                intent2.putExtra("type", "ProFile");
                startActivity(intent2);

                break;
            //相册
            case R.id.me_relativelayout_album:
                ActivityUtil.next(getActivity(), MyAlbumActivity.class);
                break;
            //钱包
            case R.id.me_relativelayout_pocket:
                UserInfoManager.getInstance().updateUserCache(getActivity());
                ActivityUtil.next(getActivity(), PocketActivity.class);
                break;
            //关于
            case R.id.me_relativelayout_about:

                ActivityUtil.next(getActivity(), MeAboutActivity.class);
                break;
            //在线客服
            case R.id.me_relativelayout_OnlineService:
                ActivityUtil.next(getActivity(), OnlineServiceActivity.class);
                break;
            //设置
            case R.id.me_relativelayout_settings:
                ActivityUtil.next(getActivity(), MeSettingsActivity.class);
                break;
            //检查版本更新
            case R.id.me_relativelayout_version:
                checkVersion();
                break;
        }
    }


    /**
     * 版本对话框
     */
    private void openRedPackageDialog(final String path) {
        View view = View.inflate(getActivity(), R.layout.dialog_version, null);
        ImageView dialogClose = (ImageView) view.findViewById(R.id.img_verson_close);
        TextView dialogCancle = (TextView) view.findViewById(R.id.tv_verson_cancle);
        TextView dialogSure = (TextView) view.findViewById(R.id.tv_verson_sure);
        final Dialog dialog = new Dialog(getActivity(), R.style.dialog_type);
        dialog.setContentView(view);
        dialog.show();

        dialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialogCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showMessage("后台更新中");
                Intent intent = new Intent(getActivity(), DownloadService.class);
                intent.putExtra("path", path);
//                intent.putExtra("path", "http://d.koudai.com/com.koudai.weishop/1000f/weishop_1000f.apk");
                getActivity().startService(intent);
                dialog.dismiss();
            }
        });
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            String version = info.versionName;
            Log.e("gy", "版本号：" + version);
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void checkVersion() {
        RetrofitClient.getInstance().createApi().checkVersion()
                .compose(RxUtils.<HttpResult<VersionBean>>io_main())
                .subscribe(new BaseObjObserver<VersionBean>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(VersionBean versionBean) {
                        if (!versionBean.getAndroidVersion().equals(getVersion())) {
                            openRedPackageDialog("115.126.100.146:8080/ZFishApp/" + versionBean.getAndroidPath());
                        }
                    }
                });
    }


}

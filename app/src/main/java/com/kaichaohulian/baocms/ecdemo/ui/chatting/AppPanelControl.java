/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.kaichaohulian.baocms.ecdemo.ui.chatting;

import android.content.Context;


import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.ui.SDKCoreHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 聊天插件功能控制器
 *
 * @author Jorstin Chan@容联•云通讯
 * @version 4.0
 * @date 2014-12-10
 */
public class AppPanelControl {

    private Context mContext;

    private static boolean isShowVoipCall = true;
    public int[] cap = new int[]{
            R.string.app_panel_pic,
//            R.string.app_panel_tackpic,
//            R.string.attach_red_video,
//            R.string.attach_red_packet,
            R.string.attach_turncash,
            R.string.attach_personinfo,
            R.string.app_panel_location,
            R.string.attach_storage
    };

    public int[] capVoip = new int[]{
            R.string.app_panel_pic,
//            R.string.app_panel_tackpic,
//            R.string.attach_red_video,
//            R.string.attach_red_packet,
            R.string.attach_turncash,
            R.string.attach_personinfo,
            R.string.app_panel_location,
            R.string.attach_storage
    };

    public AppPanelControl() {
        mContext = CCPAppManager.getContext();
    }


    public static void setShowVoipCall(boolean isShowVoipCall) {
        AppPanelControl.isShowVoipCall = isShowVoipCall;
    }

    public List<Capability> getCapability() {
        List<Capability> capabilities = new ArrayList<Capability>();

        if (isShowVoipCall && SDKCoreHelper.getInstance().isSupportMedia()) {
            for (int i = 0; i < capVoip.length; i++) {
                Capability capability = getCapability(capVoip[i]);
                capabilities.add(capabilities.size(), capability);
            }

        } else {
            for (int i = 0; i < cap.length; i++) {
                Capability capability = getCapability(cap[i]);
                capabilities.add(capabilities.size(), capability);
            }
        }
        return capabilities;
    }

    /**
     * @param resid
     * @return
     */
    private Capability getCapability(int resid) {
        Capability capability = null;
        switch (resid) {
            case R.string.app_panel_pic:
                capability = new Capability(getContext().getString(
                        R.string.app_panel_pic), R.mipmap.photo);
                break;
            case R.string.app_panel_tackpic:
                capability = new Capability(getContext().getString(
                        R.string.app_panel_tackpic), R.drawable.icon_chat_photo);
                break;
            case R.string.app_panel_file:
                capability = new Capability(getContext().getString(
                        R.string.app_panel_file), R.drawable.capability_file_icon);
                break;
            case R.string.app_panel_voice:
                capability = new Capability(getContext().getString(
                        R.string.app_panel_voice), R.drawable.icon_chat_phone);
                break;
            case R.string.app_panel_video:
                capability = new Capability(getContext().getString(
                        R.string.app_panel_video), R.drawable.video_call);
                break;
            case R.string.app_panel_read_after_fire:
                capability = new Capability(getContext().getString(
                        R.string.app_panel_read_after_fire), R.drawable.fire_msg);
                break;
            case R.string.app_panel_location:
                capability = new Capability(getContext().getString(
                        R.string.app_panel_location), R.mipmap.location);
                break;
            case R.string.attach_red_packet:
                capability = new Capability(getContext().getString(
                        R.string.attach_red_packet), R.drawable.icon_chat_moneyred);
                break;
            case R.string.attach_storage:
                capability = new Capability(getContext().getString(
                        R.string.attach_storage), R.mipmap.collect);
                break;
            case R.string.attach_personinfo:
                capability = new Capability(getContext().getString(
                        R.string.attach_personinfo), R.mipmap.idcard);
                break;
            case R.string.attach_turncash:
                capability = new Capability(getContext().getString(
                        R.string.attach_turncash), R.mipmap.transfer);
                break;
            case R.string.attach_red_video:
                capability = new Capability(getContext().getString(
                        R.string.attach_red_video), R.drawable.icon_chat_vedio);
                break;
            default:
                break;
        }
        capability.setId(resid);
        return capability;
    }

    /**
     * @return
     */
    private Context getContext() {
        if (mContext == null) {
            mContext = MyApplication.getInstance().getApplicationContext();
        }
        return mContext;
    }
}

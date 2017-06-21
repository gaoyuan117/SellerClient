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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.ecdemo.common.CCPAppManager;
import com.kaichaohulian.baocms.ecdemo.common.utils.CrashHandler;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.ecdemo.ui.ECFragmentActivity;
import com.kaichaohulian.baocms.ecdemo.ui.SDKCoreHelper;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.view.CCPChattingFooter2;
import com.kaichaohulian.baocms.event.FinishEvent;
import com.yuntongxun.ecsdk.ECMessage;

import de.greenrobot.event.EventBus;


/**
 * @author 容联•云通讯
 * @version 4.0
 * @date 2014-12-9
 */
public class ChattingActivity extends ECFragmentActivity implements ChattingFragment.OnChattingAttachListener {
    private static final String TAG = "ECSDK_Demo.ChattingActivity";
    public ChattingFragment mChattingFragment;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.d(TAG, "onCreate");
        super.onCreate(null);
        getWindow().setFormat(PixelFormat.TRANSPARENT);
        registEventBus();
        myReceiver = new MyReceiver();
        String recipients = getIntent().getStringExtra(ChattingFragment.RECIPIENTS);
        if (recipients == null) {
            finish();
            LogUtil.e(TAG, "recipients is null !!");
            return;
        }
        setContentView(R.layout.chattingui_activity_container);
        mChattingFragment = new ChattingFragment();
        Bundle bundle = getIntent().getExtras();
        bundle.putBoolean(ChattingFragment.FROM_CHATTING_ACTIVITY, true);
        mChattingFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id.ccp_root_view, mChattingFragment).commit();
        onActivityCreate();
        if (isChatToSelf(recipients) || isPeerChat(recipients)) {
            AppPanelControl.setShowVoipCall(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        CrashHandler.getInstance().setContext(this);

        IntentFilter filter1 = new IntentFilter();

        filter1.addAction("com.kaichaohulian.baocms.removemember");
        filter1.addAction(SDKCoreHelper.ACTION_KICK_OFF);

        registerReceiver(myReceiver, filter1);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegistEventBus();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        LogUtil.d(TAG, "chatting ui dispatch key event :" + event);
        if (mChattingFragment != null && mChattingFragment.onKeyDown(event.getKeyCode(), event)) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    public class MyReceiver extends BroadcastReceiver {
        // 可用Intent的getAction()区分接收到的不同广播
        @Override
        public void onReceive(Context arg0, Intent intent) {
            if (intent == null) {
                return;
            }
            if (intent.getAction().equals(SDKCoreHelper.ACTION_KICK_OFF)
                    || intent.getAction().equals("com.kaichaohulian.baocms.removemember"))
                finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.d(TAG, "chatting ui on key down, " + keyCode + ", " + event);
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && CCPChattingFooter2.isRecodering) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        LogUtil.d(TAG, "chatting ui on key up");
        return super.onKeyUp(keyCode, event);
    }

    public boolean isPeerChat() {
        if (mChattingFragment != null) {
            return mChattingFragment.isPeerChat();
        }
        return false;
    }

    public boolean isChatToSelf(String sessionId) {
        String userId = CCPAppManager.getUserId();
        return sessionId != null && (sessionId.equalsIgnoreCase(userId) ? true : false);
    }

    public boolean isPeerChat(String sessionId) {
        return sessionId.toLowerCase().startsWith("g");
    }

    @Override
    public void onChattingAttach() {
        LogUtil.d(TAG, "onChattingAttach");
    }

    @Override
    protected boolean isEnableSwipe() {
        return false;
    }


    public void registEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void unRegistEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onEventMainThread(FinishEvent event) {
        finish();
    }

}
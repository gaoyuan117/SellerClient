package com.kaichaohulian.baocms.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;

import com.kaichaohulian.baocms.activity.LoginActivity;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.utils.DBLog;

/**
 * 应用程序Activity的基类
 * Created by ljl on 2016/12/11.
 */
public abstract class BaseEcFragment extends com.kaichaohulian.baocms.ecdemo.ui.BaseFragment {
    protected MyApplication myApplication;
    protected Context context;
    protected Activity activity;

    public BaseEcFragment() {
    }

    public BaseEcFragment(MyApplication myApplication, Activity activity, Context context) {
        this.myApplication = myApplication;
        this.activity = activity;
        this.context = context;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
    }


    protected void showToastMsg(String msg) {
        DBLog.showToast(msg, getActivity());
    }



    /**
     * 监听数据变化 用于Fragment 与 Activity 通信
     *
     * @author Administrator
     */
    public interface DataChangeListener {
        void dataChange();

        void success(Object data);

        void fail(String msg);
    }

    protected DataChangeListener listener;

    public void setDataChangeListener(DataChangeListener listener) {
        this.listener = listener;
    }

    /**
     * 登录状态
     *
     * @return true登录 false未登录
     */
    public boolean isLoginState() {
        if (MyApplication.getInstance().UserInfo == null) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否登录 如果为否则自动跳转到登录界面
     *
     * @param T      跳转的类
     * @param bundle 传值
     * @return 是否登录
     */
    public boolean isLoginAutomaticJump(Class<?> T, Bundle bundle) {
        boolean is = isLoginState();
        if (!is) {
            ActivityUtil.next(getActivity(), LoginActivity.class);
        } else {
            if (T != null) {
                ActivityUtil.next(getActivity(), T, bundle);
            }
        }
        return is;
    }

    /**
     * 判断是否登录 如果为否则自动跳转到登录界面
     *
     * @param T       跳转的类
     * @param bundle  传值
     * @param reqCode 回调 值
     * @return 是否登录
     */
    public boolean isLoginAutomaticJump(Class<?> T, Bundle bundle, int reqCode) {
        boolean is = isLoginState();
        if (!is) {
            ActivityUtil.next(getActivity(), LoginActivity.class);
        } else {
            if (T != null) {
                ActivityUtil.next(getActivity(), T, bundle, reqCode);
            }
        }
        return is;
    }

    /**
     * 判断是否登录 如果为否则自动跳转到登录界面
     *
     * @param T 跳转的类
     * @return 是否登录
     */
    public boolean isLoginAutomaticJump(Class<?> T) {
        boolean is = isLoginState();
        if (!is) {
            ActivityUtil.next(getActivity(), LoginActivity.class);
        } else {
            if (T != null) {
                ActivityUtil.next(getActivity(), T);
            }
        }
        return is;
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onDestroy() {
        onReleaseTabUI();
        super.onDestroy();
    }

    /**
     * 当前TabFragment被释放
     */
    protected abstract void onReleaseTabUI();

    @Override
    public int getTitleLayoutId() {
        return -1;
    }
}

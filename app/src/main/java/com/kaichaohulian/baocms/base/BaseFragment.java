package com.kaichaohulian.baocms.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.LoginActivity;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.utils.DBLog;

/**
 * 应用程序Activity的基类
 * Created by ljl on 2016/12/11.
 */
public abstract class BaseFragment extends Fragment {
    public View mView;
    protected MyApplication myApplication;
    protected Context context;
    protected Activity activity;
    public int width;
    public int height;

    public BaseFragment(MyApplication myApplication, Activity activity, Context context) {
        this.myApplication = myApplication;
        this.activity = activity;
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Create: " + this.getClass().getSimpleName());
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        setContent();
        initData();
        initView();
        initEvent();
        ViewGroup parent = (ViewGroup) mView.getParent();
        if (parent != null) {
            parent.removeView(mView);// 先移除
        }
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 防止被T后，没点确定按钮然后按了home键，长期在后台又进app导致的crash
        if (savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
    }

    /**
     * 设置布局
     */
    public abstract void setContent();

    public abstract void initData();

    public abstract void initView();

    public abstract void initEvent();

    protected void showToastMsg(String msg) {
        DBLog.showToast(msg, getActivity());
    }

    /**
     * 设置title
     */
    protected void setCenterTitle(String title) {
        if (title == null) {
            return;
        }
        if (title.length() > 12) {
            title = title.substring(0, 11) + "...";
        }
        TextView tvTitle = (TextView) mView.findViewById(R.id.tv_title);
        tvTitle.setText(title);
        goBack();
    }

    protected void visibilityExit() {
        mView.findViewById(R.id.exit_layout).setVisibility(View.GONE);
    }

    /**
     * 获取控件ID
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T getId(int id) {
        return (T) mView.findViewById(id);
    }

    /**
     * 设置布局
     */
    protected void goBack() {
        mView.findViewById(R.id.iv_back).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtil.goBack(getActivity());
                    }
                });
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
}

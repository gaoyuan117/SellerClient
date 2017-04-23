package com.kaichaohulian.baocms.base;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.LoginActivity;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.AppManager;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.ecdemo.ui.ECSuperActivity;
import com.kaichaohulian.baocms.utils.DBLog;

/**
 * 应用程序Activity的基类
 * Created by ljl on 2016/12/11.
 */
public abstract class BaseEcActivity extends ECSuperActivity {
    /**
     * 允许全屏
     */
    public Boolean FEATURE_NO_TITLE = true;
    public int width;
    public int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (FEATURE_NO_TITLE) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        super.onCreate(savedInstanceState);
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();

        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
        initData();
        initView();
        initEvent();
    }


    public abstract void initData();

    public abstract void initView();

    public abstract void initEvent();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 添加Activity到堆栈
        AppManager.getAppManager().finishActivity(this);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
    }

    protected BaseEcActivity getActivity() {
        return BaseEcActivity.this;
    }

    /**
     * 设置title
     */
    protected void setLeftTitle(String title) {
        if (title == null) {
            return;
        }
        if (title.length() > 12) {
            title = title.substring(0, 11) + "...";
        }
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(title);
        goBack();
    }

    protected void setCenterTitle(String title){
        if (title == null) {
            return;
        }
        if (title.length() > 12) {
            title = title.substring(0, 11) + "...";
        }
        TextView tvTitle = (TextView) findViewById(R.id.center_title_tv);
        tvTitle.setText(title);
        goBack();
    }

    protected TextView setRightTitle(String title) {
        if (title == null) {
            return null;
        }
        TextView tvTitle = (TextView) findViewById(R.id.tv_right_text);
        tvTitle.setText(title);
        return tvTitle;
    }

    protected void setIm_view(int id) {
        if (id == 0) {
            return;
        }
        ImageView ImageView = (ImageView) findViewById(R.id.iv_image);
        ImageView.setVisibility(View.VISIBLE);
        ImageView.setImageResource(id);
    }

    protected ImageView setIm1_view(int id) {
        if (id == 0) {
            return null;
        }
        ImageView ImageView = (ImageView) findViewById(R.id.iv_image1);
        ImageView.setVisibility(View.VISIBLE);
        ImageView.setImageResource(id);
        return ImageView;
    }

    /**
     * 设置布局
     */
    protected void goBack() {
        findViewById(R.id.iv_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.goBack(BaseEcActivity.this);
            }
        });
    }

    protected void visibilityExit() {
        findViewById(R.id.exit_layout).setVisibility(View.GONE);
    }

    /**
     * 土司
     */
    protected void showToastMsg(String msg) {
        DBLog.showToast(msg, this);
    }

    /**
     * 获取控件ID
     *
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T getId(int id) {
        return (T) findViewById(id);
    }

    public final String TAG = "BaseActivity";

    // 字体
    public void typeface(TextView txt) {
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/sv.ttf");
        txt.setTypeface(face);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ActivityUtil.goBack(this);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) {
            DBLog.i(TAG, "点击了菜单键");
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
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

    public void out(String tag, Object msg) {
        DBLog.out(tag, msg);
    }

}

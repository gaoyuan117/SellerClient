package com.kaichaohulian.baocms.rxjava;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.http.HttpResult;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by admin on 2017/3/27.
 *
 */

public abstract class BaseObjObserver<T> implements Observer<HttpResult<T>> {

    private static final String TAG = "BaseObjObserver";
    private boolean isToast = true;//是否toast
    private Context mContext;
    private String message;//加载框提示内容
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout refreshLayout;//下拉刷新


    protected BaseObjObserver(Context context) {
        mContext = context;
    }

    protected BaseObjObserver(Context context, SwipeRefreshLayout refreshLayout) {
        this(context);
        this.refreshLayout = refreshLayout;
    }

    protected BaseObjObserver(Context context, boolean isToast) {
        mContext = context;
        this.isToast = isToast;
    }

    protected BaseObjObserver(Context context, String message) {
        this.mContext = context;
        this.message = message;
    }

    protected BaseObjObserver(Context context, String message, boolean isToast) {
        this(context, message);
        this.isToast = isToast;
    }


    @Override
    public void onNext(HttpResult<T> value) {
        if (value.code == 0) {
            T t = value.data;
            onHandleSuccess(t);
        } else {
            onHandleError(value.code, value.errorDescription);
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "error:" + e.toString());
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setEnabled(false);
        }
    }

    @Override
    public void onComplete() {

        if (progressDialog != null) {
            progressDialog.dismiss();
        }

        if (refreshLayout != null && refreshLayout.isRefreshing()) {
            refreshLayout.setEnabled(false);
        }
    }

    protected abstract void onHandleSuccess(T t);

    protected void onHandleError(int code, String msg) {
        //根据code处理

        //是否Toast，默认true
        if (isToast) {
            Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        //显示对话框
        if (!TextUtils.isEmpty(message)) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage(message);
            progressDialog.show();
        }
    }
}


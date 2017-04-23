package com.kaichaohulian.baocms.util;

import java.util.ArrayList;
import java.util.Timer;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.RedpacketOpenActivity;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.ecdemo.common.utils.LogUtil;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.entity.ItemMenu;
import com.kaichaohulian.baocms.entity.RedBagDetail;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;
import org.json.JSONObject;

public class VDialog implements VDialogInterface {
    private MyPopupWindow pw;
    public static final int OK = 101;
    public static final int CANCEL = 100;
    private Toast mToast;
    private static VDialog instance = null;
    private Timer mTimer;

    //公司选择弹出框
    private AlertDialog comSelDlg = null;
    //webview错误弹出框
    private AlertDialog webviewErrorDlg = null;

    public static synchronized VDialog getDialogInstance() {
        if (null == instance) {
            instance = new VDialog();
            VDialogAdapter.getDialogInstance().setInterface(instance);
        }
        return instance;
    }

    public PopupWindow getPopupWindow() {
        return pw;
    }

    private VDialog() {
        // 获取屏幕尺寸数据
//        getDeviceScreenSize();
    }

    @Override
    public void showDoubleBtnAlertDialog(Context ctx, String content, String okText, String cancelText,
                                         boolean isKickType, Handler handler) {

    }

    @Override
    public void showSingleBtnAlertDialog(Activity context, String content, String okText, boolean isKickType,
                                         Handler handler) {

    }

    @SuppressLint("NewApi")
    public void showDeleteConfirmDialog(Activity context, final Handler handler) {
        comSelDlg = new AlertDialog.Builder(context, Window.FEATURE_NO_TITLE)
                .setTitle("删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.sendEmptyMessage(1);
                        comSelDlg.cancel();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        comSelDlg.cancel();
                    }
                })
                .create();
        comSelDlg.setCanceledOnTouchOutside(false);
        comSelDlg.setCancelable(false);
        comSelDlg.show();

    }

    //更新弹出框
    private AlertDialog updateDlg = null;

    public void closeUpdateDlg() {
        if (updateDlg != null) {
            updateDlg.cancel();
            updateDlg = null;
        }
    }

    @SuppressLint("NewApi")
    public void showQuitConfirmDialog(Activity context, final Handler handler) {
        comSelDlg = new AlertDialog.Builder(context, AlertDialog.THEME_HOLO_LIGHT)
                .setTitle("确定退出？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        handler.sendEmptyMessage(1);
                        comSelDlg.cancel();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        comSelDlg.cancel();
                    }
                })
                .create();
        comSelDlg.setCanceledOnTouchOutside(false);
        comSelDlg.setCancelable(false);
        comSelDlg.show();

    }


    @Override
    public boolean isKickDlgType() {
        return false;
    }

    @Override
    public void showConfirmDialog(Activity context, View parent, String content, String leftButtonText,
                                  String rightButtonText, Handler handler) {

    }

    @Override
    public void activateAccountTipPopupWindow(Activity context, View parent, String tip) {

    }

    @Override
    public void successDialog(Context context, int resourceId) {

    }

    public void release() {
        closePw();
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    public boolean isShow() {
        if (pw != null && pw.isShowing()) {
            return true;
        }
        return false;
    }

    //判断Dialog是不是弹出来了
    public boolean isDialogShow() {
        if (comSelDlg != null && comSelDlg.isShowing()) {
            return true;
        }
        return false;
    }


    public boolean isHasShow() {
        if (pw != null) {
            // return pw.isShowing();
            closePw();
        }
        return false;
    }

    /**
     * 提示框
     *
     * @param mContext
     * @param text
     */
    Handler handler = MyApplication.sGlobalHandler;

    private class ExcuteRunnable implements Runnable {
        private String text;
        private Context mContext;

        ExcuteRunnable(Context mContext, String text) {
            this.text = text;
            this.mContext = mContext;
        }

        @Override
        public void run() {
            if (mToast == null) {
                mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
            }
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    /**
     * showTimeType 0 表示短显示， 1表示长显示
     */
    private class ExcuteRunnable1 implements Runnable {
        private String text;
        private int showTimeType;
        private Context mContext;

        ExcuteRunnable1(Context mContext, String text, int showTimeType) {
            this.text = text;
            this.showTimeType = showTimeType;
            this.mContext = mContext;
        }

        @Override
        public void run() {
            if (mToast == null) {
                mToast = Toast.makeText(mContext, text, showTimeType);
            }
            mToast.setText(text);
            mToast.setDuration(showTimeType);
            mToast.show();
        }
    }

    // 避免重复弹出toast的函数
    public void toast(Context context, String text) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        if (!text.equals("未知TOKEN"))
            handler.post(new ExcuteRunnable(context, text));
    }

    // 避免重复弹出toast的函数，显示长toast
    public void toastLong(Context context, String text, int showTimeType) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        handler.post(new ExcuteRunnable1(context, text, showTimeType));
    }

    public void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    public void cancelToastWithLayout() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
//    // 设置阴影效果
//    private static void applyFunction(View root) {
//        if (root != null) {
//            RelativeLayout rApplyBg = (RelativeLayout) root.findViewById(R.id.rApplyBg);
//            if (rApplyBg != null) {
//                rApplyBg.getBackground().setAlpha(50);
//            }
//        }
//    }
//
//    private void setOutAnimation(View view, final OnDialogDismissListener listener) {
//        ScaleAnimation mPopupMenuOutAnimation = null;
//        if (mPopupMenuOutAnimation == null) {
//            // 创建弹出菜单收起时使用的动画
//            mPopupMenuOutAnimation = new ScaleAnimation(1.0f, 1.0f, 1f, 0f);
//            mPopupMenuOutAnimation.setDuration(200);
//            mPopupMenuOutAnimation.setAnimationListener(new AnimationListener() {
//                public void onAnimationStart(Animation arg0) {
//                }
//
//                public void onAnimationRepeat(Animation arg0) {
//                }
//
//                public void onAnimationEnd(Animation arg0) {
//                    closePw();
//                    if (listener != null) {
//                        listener.onDismiss();
//                    }
//                }
//
//            });
//        }
//
//        if (view != null) {
//            view.startAnimation(mPopupMenuOutAnimation);
//        }
//    }
//
//
//    /**
//     * 自定义弹出系统菜单
//     * 
//     * @param context
//     * @param parent
//     */
//    public void showSystemDialog(final Activity context, View parent, String conent, final Handler handler) {
//        if (isHasShow()) {
//            return;
//        }
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View vPopupWindow = inflater.inflate(R.layout.show_sytem_dialog, null, false);
//        pw = new MyPopupWindow(context, vPopupWindow, deviceWidth, deviceHeight, true);
//
//        pw.setAnimationStyle(R.style.AnimationPreview);
//        pw.setFocusable(true);
//        pw.showAtLocation(parent, Gravity.CENTER, 0, 0);
//
//        final TextView mContent = (TextView) vPopupWindow.findViewById(R.id.mContent);
//        mContent.setText(conent);
//
//        RelativeLayout cancel = (RelativeLayout) vPopupWindow.findViewById(R.id.confirm_calcel);
//        cancel.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                closePw();
//                sendMessage(handler, CANCEL, "");
//            }
//        });
//
//        RelativeLayout ok = (RelativeLayout) vPopupWindow.findViewById(R.id.confirm_ok);
//        ok.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {// 确定
//                closePw();
//                sendMessage(handler, OK, "");
//            }
//        });
//
//        LinearLayout relativedownload = (LinearLayout) vPopupWindow.findViewById(R.id.cj_popup);
//        relativedownload.setBackgroundDrawable(new BitmapDrawable());
//        relativedownload.setBackgroundColor(context.getResources().getColor(R.color.btm));
//        relativedownload.setFocusable(false);
//        relativedownload.setFocusableInTouchMode(false);
//        relativedownload.setOnKeyListener(new OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    // pw.dismiss();
//                }
//                return true;
//            }
//        });
//    }
//
//    /**
//     * 自定义弹出窗口
//     * 
//     * @param context
//     * @param parent
//     * @parm content
//     * @parm leftButtonText
//     * @parm rightButtonText
//     * @parm handler
//     */
//    public void showConfirmDialog(final Activity context, View parent, String content, String leftButtonText,
//            String rightButtonText, final Handler handler) {
//        if (isHasShow()) {
//            return;
//        }
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View vPopupWindow = inflater.inflate(R.layout.show_confirm_dialog, null, false);
//        pw = new MyPopupWindow(context, vPopupWindow, deviceWidth, deviceHeight, true);
//
//        pw.setAnimationStyle(R.style.AnimationPreview);
//        pw.setFocusable(true);
//        pw.showAtLocation(parent, Gravity.CENTER, 0, 0);
//
//        final TextView mContent = (TextView) vPopupWindow.findViewById(R.id.mContent);
//        mContent.setText(content);
//
//        final TextView mLeftButtonText = (TextView) vPopupWindow.findViewById(R.id.mqd);
//        mLeftButtonText.setText(leftButtonText);
//
//        final TextView mRightButtonText = (TextView) vPopupWindow.findViewById(R.id.mqx);
//        mRightButtonText.setText(rightButtonText);
//
//        RelativeLayout cancel = (RelativeLayout) vPopupWindow.findViewById(R.id.rCalcel);
//        cancel.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                closePw();
//                sendMessage(handler, CANCEL, "");
//            }
//        });
//
//        RelativeLayout ok = (RelativeLayout) vPopupWindow.findViewById(R.id.rOk);
//        ok.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {// 确定
//                closePw();
//                sendMessage(handler, OK, "");
//            }
//        });
//
//        LinearLayout relativedownload = (LinearLayout) vPopupWindow.findViewById(R.id.cj_popup);
//        relativedownload.setBackgroundDrawable(new BitmapDrawable());
//        relativedownload.setBackgroundColor(context.getResources().getColor(R.color.btm));
//        relativedownload.setFocusable(false);
//        relativedownload.setFocusableInTouchMode(false);
//        relativedownload.setOnKeyListener(new OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    // pw.dismiss();
//                }
//                return true;
//            }
//        });
//    }
//
//
//    /**
//     * 自定义弹出系统菜单
//     * 
//     * @param context
//     * @param parent
//     */
//    public void showSystemDialogText(final Activity context, View parent, String conent, final Handler handler,
//            String text) {
//        if (isHasShow()) {
//            return;
//        }
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View vPopupWindow = inflater.inflate(R.layout.show_sytem_dialog, null, false);
//        pw = new MyPopupWindow(context, vPopupWindow, deviceWidth, deviceHeight, true);
//
//        pw.setAnimationStyle(R.style.AnimationPreview);
//        pw.setFocusable(true);
//        pw.showAtLocation(parent, Gravity.CENTER, 0, 0);
//
//        final TextView mContent = (TextView) vPopupWindow.findViewById(R.id.mContent);
//        mContent.setText(conent);
//
//        RelativeLayout cancel = (RelativeLayout) vPopupWindow.findViewById(R.id.confirm_calcel);
//        cancel.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                closePw();
//                sendMessage(handler, CANCEL, "");
//            }
//        });
//
//        TextView mqd = (TextView) vPopupWindow.findViewById(R.id.mqd);
//        mqd.setText(text);
//
//        RelativeLayout ok = (RelativeLayout) vPopupWindow.findViewById(R.id.confirm_ok);
//        ok.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {// 确定
//                closePw();
//                sendMessage(handler, OK, "");
//            }
//        });
//
//        LinearLayout relativedownload = (LinearLayout) vPopupWindow.findViewById(R.id.cj_popup);
//        relativedownload.setBackgroundDrawable(new BitmapDrawable());
//        relativedownload.setBackgroundColor(context.getResources().getColor(R.color.btm));
//        relativedownload.setFocusable(false);
//        relativedownload.setFocusableInTouchMode(false);
//        relativedownload.setOnKeyListener(new OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    // pw.dismiss();
//                }
//                return true;
//            }
//        });
//    }
//
//
//    public void showBankAlertDialog(final Context ctx, final ArrayList<Bank> banklist,final Handler handler) {
//        if (ctx != null&&!((Activity)ctx).isFinishing()) {
//            
//            comSelDlg = new AlertDialog.Builder(ctx,AlertDialog.THEME_HOLO_LIGHT).create();
//            comSelDlg.setCanceledOnTouchOutside(false);
//            comSelDlg.setCancelable(false);
//            comSelDlg.show();
//           
//            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view = inflater.inflate(R.layout.banklist, null, false);
//            comSelDlg.getWindow().setContentView(view);
//            ListView listView = (ListView)view.findViewById(R.id.list_view);
//            ArrayList<String> list = new ArrayList<String>();
//            for (Bank bank : banklist) {
//				list.add(bank.getName());
//			}
//            listView.setAdapter(new ArrayAdapter<String>(ctx, R.layout.simple_item, list));
//           
//
//           listView.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//				Bank bank = banklist.get(position);
//				
//				 Message msg = null;
//                   msg = handler.obtainMessage(OK);
//                   msg.arg1 = bank.getId();
//                   Bundle bundle = new Bundle();
//                   bundle.putString("name", bank.getName());
//                   msg.setData(bundle);
//                   comSelDlg.cancel();
//               if (msg != null) {
//                   msg.sendToTarget();
//               }
//			}
//		});
//
//            
//        }
//    }
//    
//    
//    
//    /*
//     * 弹出AlertDialog 退出系统
//     */
//    public void showExitDialog(final Context ctx, String content, String okText, String cancelText,String title,
//            final Handler handler) {
//        if (ctx != null) {
//            final AlertDialog dlg = new AlertDialog.Builder(ctx).create();
//            dlg.setCanceledOnTouchOutside(false);
//            dlg.setCancelable(false);
//            dlg.show();
//
//            LayoutInflater inflater = (LayoutInflater) App.getContext()
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final View view = inflater.inflate(R.layout.show_system_dialog_new, null, false);
//            dlg.getWindow().setContentView(view);
//
//            final TextView mContent = (TextView) view.findViewById(R.id.mContent);
//            final TextView mTitle = (TextView) view.findViewById(R.id.show_title);
//            // mContent.setTextSize(14);
//            mContent.setText(content);
//            mTitle.setText(title);
//            
//            final TextView leftBtn = (TextView) view.findViewById(R.id.mqd);
//            leftBtn.setText(cancelText);
//
//            final TextView rightBtn = (TextView) view.findViewById(R.id.mqx);
//            rightBtn.setText(okText);
//
//            RelativeLayout cancel = (RelativeLayout) view.findViewById(R.id.rCalcel);
//
//            RelativeLayout ok = (RelativeLayout) view.findViewById(R.id.rOk);
//            View.OnClickListener clickListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (v != null) {
//                        Message msg = null;
//                        switch (v.getId()) {
//                        case R.id.rCalcel: {
//                            msg = handler.obtainMessage(OK);
//                            break;
//                        }
//                        case R.id.rOk: {
//                            msg = handler.obtainMessage(CANCEL);
//                        }
//                        }
//                        dlg.cancel();
//                        if (msg != null) {
//                            msg.sendToTarget();
//                        }
//                    }
//                }
//            };
//            cancel.setOnClickListener(clickListener);
//            ok.setOnClickListener(clickListener);
//        }
//    }
//    
//    /*
//     * 弹出AlertDialog 只有 "确定" 按钮
//     */
//    public void showSingleBtnAlertDialog(final Activity context, String content, String okText, final boolean isKickType ,final Handler handler) {
//        if (context != null) {
//            final AlertDialog dlg = new AlertDialog.Builder(context).create();
//            dlg.setCanceledOnTouchOutside(false);
//            dlg.setCancelable(false);
//            dlg.show();
//
//            LayoutInflater inflater = (LayoutInflater) App.getContext()
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final View view = inflater.inflate(R.layout.show_sytem_dialog_only_ok, null, false);
//            dlg.getWindow().setContentView(view);
//
//            final TextView mContent = (TextView) view.findViewById(R.id.mContent);
//            // 设置确认按钮的值
//            final TextView mOk = (TextView) view.findViewById(R.id.btn_ok);
//            mContent.setText(content);
//            if (!TextUtils.isEmpty(okText)) {
//                mOk.setText(okText);
//                mOk.setTextColor(context.getResources().getColor(R.color.emphasize_color));
//            }
//
//            RelativeLayout okBtn = (RelativeLayout) view.findViewById(R.id.lyt_ok);
//            View.OnClickListener clickListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (v != null) {
//                        isKickDlgType = false ;
//                        Message msg = null;
//                        switch (v.getId()) {
//                        case R.id.lyt_ok: {
//                            if(DateUtil.equalsDelTime()){
//                                return;
//                            }
//                            msg = handler.obtainMessage(OK);
//                        }
//                        }
//                        dlg.cancel();
//                        if (msg != null) {
//                            msg.sendToTarget();
//                        }
//                    }
//                }
//            };
//            okBtn.setOnClickListener(clickListener);
//            isKickDlgType = isKickType;
//        }
//    }
//    
//    /*
//     * 弹出AlertDialog 只有 "确定" 按钮
//     */
//    public void showPermissionAlertDialog(final Activity context, final Handler handler) {
//        if (context != null && !context.isFinishing()) {
//            View rootView = ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
//            if(rootView == null||rootView.getWindowToken() == null) {//如果activity依附的View返回为null，直接返回
//                return ;
//            }
//            final AlertDialog dlg = new AlertDialog.Builder(context).create();
//            dlg.setCanceledOnTouchOutside(false);
//            dlg.setCancelable(false);
//            dlg.show();
//
//            LayoutInflater inflater = (LayoutInflater) App.getContext()
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            final View view = inflater.inflate(R.layout.meet_permission_layout, null, false);
//            dlg.getWindow().setContentView(view);
//            Button okBtn = (Button) view.findViewById(R.id.permission_iknow);
//            View.OnClickListener clickListener = new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (v != null) {
//                        Message msg = null;
//                        switch (v.getId()) {
//                        case R.id.permission_iknow: {
//                            msg = handler.obtainMessage(OK);
//                        }
//                        }
//                        dlg.cancel();
//                        if (msg != null) {
//                            msg.sendToTarget();
//                        }
//                    }
//                }
//            };
//            okBtn.setOnClickListener(clickListener);
//        }
//    }
//
//    /**
//     * 自定义弹出系统菜单,带button设置功能
//     * 
//     * @author WH1407054 zhuxiang
//     * @param context
//     * @param parent
//     */
//    public void showSystemDialog(final Activity context, View parent, String conent, String btnLeft, String btnRight,
//            final Handler handler) {
//        if (isHasShow()) {
//            return;
//        }
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View vPopupWindow = inflater.inflate(R.layout.show_sytem_dialog, null, false);
//        pw = new MyPopupWindow(context, vPopupWindow, deviceWidth, deviceHeight, true);
//
//        pw.setAnimationStyle(R.style.AnimationPreview);
//        pw.showAtLocation(parent, Gravity.CENTER, 0, 0);
//
//        final TextView mContent = (TextView) vPopupWindow.findViewById(R.id.mContent);
//        mContent.setText(conent);
//
//        final TextView leftBtn = (TextView) vPopupWindow.findViewById(R.id.mqx);
//        leftBtn.setText(btnLeft);
//
//        final TextView rightBtn = (TextView) vPopupWindow.findViewById(R.id.mqd);
//        rightBtn.setText(btnRight);
//
//        RelativeLayout cancel = (RelativeLayout) vPopupWindow.findViewById(R.id.confirm_calcel);
//        cancel.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                closePw();
//                sendMessage(handler, CANCEL, "");
//            }
//        });
//
//        RelativeLayout ok = (RelativeLayout) vPopupWindow.findViewById(R.id.confirm_ok);
//        ok.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {// 确定
//                closePw();
//                sendMessage(handler, OK, "");
//            }
//        });
//
//        final LinearLayout relativedownload = (LinearLayout) vPopupWindow.findViewById(R.id.cj_popup);
//        relativedownload.setBackgroundDrawable(new BitmapDrawable());
//        relativedownload.setBackgroundColor(context.getResources().getColor(R.color.btm));
//        relativedownload.setFocusable(true);
//        relativedownload.setFocusableInTouchMode(true);
//
//        // 监听时要和外面一致 KeyEvent.ACTION_DOWN
//        relativedownload.setOnKeyListener(new OnKeyListener() {
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK && KeyEvent.ACTION_DOWN == event.getAction()
//                        && event.getRepeatCount() == 0) {
//                    closePw();
//                }
//                return true;
//            }
//        });
//    }

    /**
     * @method: closePw @Description: 关闭和释放资源 @throws
     */
    public void closePw() {
        if (pw != null) {
            Activity attach = pw.getAttachActivity();
            if (attach != null && !attach.isFinishing()) {
                pw.closePopupWindow();
                pw = null;
            }
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    //关闭所有对话框
    public void closeAllPw() {
        closePw();

        if (comSelDlg != null) {
            comSelDlg.cancel();
            comSelDlg = null;
        }

        if (webviewErrorDlg != null) {
            webviewErrorDlg.cancel();
            webviewErrorDlg = null;
        }
    }

    public void closePw(int type) {
        if (pw != null) {
            Activity attach = pw.getAttachActivity();
            if (attach != null && !attach.isFinishing() && type == pw.getPopType()) {
                pw.closePopupWindow();
                pw = null;
            }
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public void closePw(Activity ac) {
        if (pw != null && ac != null) {
            Activity attach = pw.getAttachActivity();

            if (ac == attach && !ac.isFinishing()) {
                pw.closePopupWindow();
                pw = null;
            }
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * 显示loding框
     */
    @Override
    public void showLoadingDialog(Context mContext, int strResId, boolean cancelable, boolean autoClose, int msgType) {
//        String content =  mContext.getResources().getString(strResId);
//        showLoadingDialog(mContext,content,cancelable,autoClose,msgType);
    }

    /**
     * 隐藏loding框
     * 返回true，表示有dlg显示， false 没有dlg在显示
     */
    @Override
    public boolean hideLoadingDialog(int msgType) {
//        if (mLoadingDlg != null && mLoadingDlg.isShowing()) {
//
//            //  force close
//            if(msgType == -1){
//                mLoadingDlg.dismiss();
//                mLoadingDlg = null;
//            }else {
//                if(msgType ==mLoadingDlg.getmDlgType()){
//                    mLoadingDlg.dismiss();
//                    mLoadingDlg = null;
//                }
//            }
//            return true;
//        }
        return false;
    }

    @Override
    public void showLoadingDialog(String content, boolean cancelable, boolean autoClose, int type) {

    }

    @Override
    public void showErrorCode(String msg) {

    }

    Toast toast;

    public void showFirstDialog(final Activity context, View parent, final Handler handler) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopupWindow = inflater.inflate(R.layout.first_menu, null, false);
        pw = new MyPopupWindow(context, vPopupWindow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        vPopupWindow.setFocusableInTouchMode(true);
        pw.setAnimationStyle(R.style.AnimationPreview);
        pw.setFocusable(true); // 设置PopupWindow可触摸
        pw.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        pw.showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1] - 450);

        ArrayList<ItemMenu> items = new ArrayList<ItemMenu>();
        ItemMenu menu1 = new ItemMenu("营业时间", 1);
        items.add(menu1);
        ItemMenu menu2 = new ItemMenu("人均消费", 2);
        items.add(menu2);
        ItemMenu menu3 = new ItemMenu("所在区域", 3);
        items.add(menu3);
        ItemMenu menu4 = new ItemMenu("商家介绍", 4);
        items.add(menu4);


        ListView listView = (ListView) vPopupWindow.findViewById(R.id.menu_listview);
        listView.setAdapter(new ArrayAdapter<ItemMenu>(context, R.layout.item_menu, items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message msg = null;
                msg = handler.obtainMessage(OK);
                msg.arg1 = ((ItemMenu) adapterView.getAdapter().getItem(i)).getId();
                if (msg != null) {
                    msg.sendToTarget();
                    pw.dismiss();
                }
            }
        });

        vPopupWindow.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closePw();
                    return true;
                }
                return false;
            }
        });

        vPopupWindow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closePw();
            }
        });

    }

    private String mSnederName;
    private String mSenderAvatar;

    public void showOpenRedBagDialog(final Activity context, View parent, final RedBagDetail detail, final Handler handler) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopupWindow = inflater.inflate(R.layout.openredbag, null, false);
        pw = new MyPopupWindow(context, vPopupWindow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        vPopupWindow.setFocusableInTouchMode(true);
        pw.setAnimationStyle(R.style.AnimationPreview);
        pw.setFocusable(true); // 设置PopupWindow可触摸
        pw.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        pw.showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1] - 450);

        final TextView senderTV = (TextView) vPopupWindow.findViewById(R.id.tv_name);
        final ImageView senderIV = (ImageView) vPopupWindow.findViewById(R.id.img_head);
        final TextView openTV = (TextView) vPopupWindow.findViewById(R.id.send);
        final ImageView closeTV = (ImageView) vPopupWindow.findViewById(R.id.img_close);
        final TextView historyTV = (TextView) vPopupWindow.findViewById(R.id.tv_history);

        historyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RedpacketOpenActivity.class);
                intent.putExtra(RedpacketOpenActivity.RED_ID, detail.getId());
                context.startActivity(intent);
                closePw();
            }
        });

        closeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePw();
            }
        });
        final int creator = detail.getCreator();
        int currUserId = MyApplication.getInstance().UserInfo.getUserId();
        String currUserAvatar = MyApplication.getInstance().UserInfo.getAvatar();
        String currUserName = MyApplication.getInstance().UserInfo.getUsername();
        if (creator == currUserId) {
            mSnederName = currUserName;
            mSenderAvatar = currUserAvatar;

            senderTV.setText(currUserName);
            ImageLoader.getInstance().displayImage(currUserAvatar, senderIV);
        } else {
            RequestParams params = new RequestParams();
            params.put("id", MyApplication.getInstance().UserInfo.getUserId());
            params.put("friendId", detail.getCreator());
            HttpUtil.post(Url.dependIDGetUserInfo, params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        if (response.getInt("code") == 0) {
                            JSONObject jsonObject = response.getJSONObject("dataObject");
                            mSnederName = jsonObject.getString("username");
                            mSenderAvatar = jsonObject.getString("avatar");
                            senderTV.setText(mSnederName);
                            ImageLoader.getInstance().displayImage(mSenderAvatar, senderIV);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                    }
                }

                @Override
                public void onFinish() {
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    DBLog.e("tag", statusCode + ":" + responseString);
                }
            });
        }

        vPopupWindow.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closePw();
                    return true;
                }
                return false;
            }
        });

        openTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, RedpacketOpenActivity.class);
//                intent.putExtra(RedpacketOpenActivity.AVATER, mSenderAvatar);
//                intent.putExtra(RedpacketOpenActivity.SENDER_NAME, mSnederName);
//                intent.putExtra(RedpacketOpenActivity.BLANCE, detail.getBalance());
//                intent.putExtra(RedpacketOpenActivity.SENDER_ID, creator);
//                intent.putExtra(RedpacketOpenActivity.RED_ID, detail.getId());
//                context.startActivity(intent);
//                closePw();
                openRedpacket(context, detail, MyApplication.getInstance().UserInfo.getUserId(), detail.getType(), detail.getId());
            }
        });
    }

    private void openRedpacket(final Activity context, final RedBagDetail detail, int userId, int type, int redid) {
        RequestParams params = new RequestParams();
        params.put("id", userId);
        params.put("redId", redid);
        params.put("type", type);
        HttpUtil.post(Url.moneyreds_open, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
                        LogUtil.d("TRACE", "target response : " + response);
                        Intent intent = new Intent(context, RedpacketOpenActivity.class);
                        intent.putExtra(RedpacketOpenActivity.AVATER, mSenderAvatar);
                        intent.putExtra(RedpacketOpenActivity.SENDER_NAME, mSnederName);
                        intent.putExtra(RedpacketOpenActivity.BLANCE, detail.getBalance());
                        intent.putExtra(RedpacketOpenActivity.SENDER_ID, detail.getCreator());
                        intent.putExtra(RedpacketOpenActivity.RED_ID, detail.getId());
                        context.startActivity(intent);
                        closePw();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    ShowDialog.dissmiss();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                ToastUtil.showMessage("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }

    public void showSecondDialog(boolean isMyShop, final Activity context, View parent, final Handler handler) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = null;
        if (isMyShop) {
            vPopupWindow = inflater.inflate(R.layout.second_menu_my, null, false);
        } else {
            vPopupWindow = inflater.inflate(R.layout.second_menu, null, false);
        }
        pw = new MyPopupWindow(context, vPopupWindow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        vPopupWindow.setFocusableInTouchMode(true);
        pw.setAnimationStyle(R.style.AnimationPreview);
        pw.setFocusable(true);
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        pw.setOutsideTouchable(true);
        pw.setFocusable(true); // 设置PopupWindow可触摸
        pw.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        pw.showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1] - 450);

        ArrayList<ItemMenu> items = new ArrayList<ItemMenu>();
        ItemMenu menu1 = new ItemMenu("技术服务比例", 1);
        items.add(menu1);
        ItemMenu menu2 = new ItemMenu("额外福利", 2);
        items.add(menu2);

        ListView listView = (ListView) vPopupWindow.findViewById(R.id.menu_listview);
        listView.setAdapter(new ArrayAdapter<ItemMenu>(context, R.layout.item_menu, items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message msg = null;
                msg = handler.obtainMessage(OK);
                msg.arg1 = ((ItemMenu) adapterView.getAdapter().getItem(i)).getId();
                if (msg != null) {
                    msg.sendToTarget();
                    pw.dismiss();
                }
            }
        });
        vPopupWindow.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closePw();
                    return true;
                }
                return false;
            }
        });

        vPopupWindow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closePw();
            }
        });

    }

    public void showThreeDialog(boolean isMyShop, final Activity context, View parent, final Handler handler) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopupWindow = inflater.inflate(R.layout.three_menu, null, false);
        pw = new MyPopupWindow(context, vPopupWindow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        pw.setAnimationStyle(R.style.AnimationPreview);
        pw.setFocusable(true);
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        pw.setFocusable(true); // 设置PopupWindow可触摸
        pw.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        LogUtil.e("TRACE", "location x :" + location[0]);
        LogUtil.e("TRACE", "location y :" + location[1]);
        pw.showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1] - 350);
        ArrayList<ItemMenu> items = new ArrayList<ItemMenu>();
        ItemMenu menu1 = new ItemMenu("一键拨号", 1);
        items.add(menu1);
        ItemMenu menu2 = new ItemMenu("地图导航", 2);
        items.add(menu2);
        ItemMenu menu3 = new ItemMenu("商家地址", 3);
        items.add(menu3);

        ListView listView = (ListView) vPopupWindow.findViewById(R.id.menu_listview);
        listView.setAdapter(new ArrayAdapter<>(context, R.layout.item_menu, items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message msg = null;
                msg = handler.obtainMessage(OK);
                msg.arg1 = ((ItemMenu) adapterView.getAdapter().getItem(i)).getId();
                if (msg != null) {
                    msg.sendToTarget();
                    if (pw != null)
                        pw.dismiss();
                }
            }
        });

        vPopupWindow.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closePw();
                    return true;
                }
                return false;
            }
        });

        vPopupWindow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closePw();
            }
        });
    }

    public void showThreeDialog2(boolean isMyShop, final Activity context, View parent, final Handler handler) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopupWindow = inflater.inflate(R.layout.four_menu, null, false);
        pw = new MyPopupWindow(context, vPopupWindow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        pw.setAnimationStyle(R.style.AnimationPreview);
        pw.setFocusable(true);
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        pw.setFocusable(true); // 设置PopupWindow可触摸
        pw.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        pw.showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1] - 450);

        ArrayList<ItemMenu> items = new ArrayList<ItemMenu>();
        ItemMenu menu1 = new ItemMenu("一键拨号", 1);
        items.add(menu1);
        ItemMenu menu2 = new ItemMenu("地图导航", 2);
        items.add(menu2);
        ItemMenu menu3 = new ItemMenu("商家地址", 3);
        items.add(menu3);

        ListView listView = (ListView) vPopupWindow.findViewById(R.id.menu_listview);
        listView.setAdapter(new ArrayAdapter<>(context, R.layout.item_menu, items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message msg = null;
                msg = handler.obtainMessage(OK);
                msg.arg1 = ((ItemMenu) adapterView.getAdapter().getItem(i)).getId();
                if (msg != null) {
                    msg.sendToTarget();
                    pw.dismiss();
                }
            }
        });
        vPopupWindow.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closePw();
                    return true;
                }
                return false;
            }
        });

        vPopupWindow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closePw();
            }
        });

    }

    public void showFourDialog(final Activity context, View parent, final Handler handler) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View vPopupWindow = inflater.inflate(R.layout.four_menu, null, false);
        pw = new MyPopupWindow(context, vPopupWindow, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        pw.setAnimationStyle(R.style.AnimationPreview);
        pw.setFocusable(true);
        int[] location = new int[2];
        parent.getLocationOnScreen(location);
        pw.setFocusable(true); // 设置PopupWindow可触摸
        pw.setOutsideTouchable(true); // 设置非PopupWindow区域可触摸
        pw.showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1] - 450);

        ArrayList<ItemMenu> items = new ArrayList<ItemMenu>();
        ItemMenu menu1 = new ItemMenu("扫描订单", 1);
        items.add(menu1);
        ItemMenu menu2 = new ItemMenu("销售统计", 2);
        items.add(menu2);
        ItemMenu menu3 = new ItemMenu("提现申请", 3);
        items.add(menu3);
        ItemMenu menu4 = new ItemMenu("提现记录", 4);
        items.add(menu4);
        ItemMenu menu5 = new ItemMenu("店铺维护", 5);
        items.add(menu5);
        ItemMenu menu6 = new ItemMenu("付款图片", 6);
        items.add(menu6);

        ListView listView = (ListView) vPopupWindow.findViewById(R.id.menu_listview);
        listView.setAdapter(new ArrayAdapter<ItemMenu>(context, R.layout.item_menu, items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Message msg = null;
                msg = handler.obtainMessage(OK);
                msg.arg1 = ((ItemMenu) adapterView.getAdapter().getItem(i)).getId();
                if (msg != null) {
                    msg.sendToTarget();
                    pw.dismiss();
                }
            }
        });
        vPopupWindow.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closePw();
                    return true;
                }
                return false;
            }
        });

        vPopupWindow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closePw();
            }
        });

    }

}

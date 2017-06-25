package com.kaichaohulian.baocms.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.EvaluateActivity;
import com.kaichaohulian.baocms.activity.ImagePagerActivity;
import com.kaichaohulian.baocms.activity.OnlineServiceActivity;
import com.kaichaohulian.baocms.activity.ReleaseTalkActivity;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseListAdapter;
import com.kaichaohulian.baocms.ecdemo.common.utils.ToastUtil;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingActivity;
import com.kaichaohulian.baocms.ecdemo.ui.chatting.ChattingFragment;
import com.kaichaohulian.baocms.entity.InviteDetailEntity;
import com.kaichaohulian.baocms.entity.InviteReciverEntity;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.qiniu.Auth;
import com.kaichaohulian.baocms.qiniu.QiNiuConfig;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.apache.http.Header;
import org.json.JSONArray;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by xzwzz on 2017/5/14.
 */

public class InviteDetailGrid2Adapter extends BaseListAdapter {
    private SimpleDateFormat format;
    private Object obj;
    public int index = 0;
    public List<String> list;
    public ImageBaseAdapter imageBaseAdapter;
    public GridView gridView;
    private Dialog dialog;
    private ImageView dialogClose;
    private TextView dialogCancle;
    private TextView dialogSure;
    private UploadManager uploadManager;
    private EditText editText;
    private String content;
    private List<String > picList;

    public InviteDetailGrid2Adapter(Context context, @Nullable List data, @Nullable Object obj) {
        super(context, data);
        this.obj = obj;
        format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        list = new ArrayList<>();

        View view = View.inflate(context, R.layout.dialog_complain, null);
        dialogClose = (ImageView) view.findViewById(R.id.img_complain_close);
        dialogCancle = (TextView) view.findViewById(R.id.tv_complain_cancle);
        dialogSure = (TextView) view.findViewById(R.id.tv_complain_sure);
        gridView = (GridView) view.findViewById(R.id.gv_complain_photos);
        editText = (EditText) view.findViewById(R.id.et_complain_content);
        imageBaseAdapter = new ImageBaseAdapter();
        gridView.setAdapter(imageBaseAdapter);
        dialog = new Dialog(context, R.style.dialog_type);
        dialog.setContentView(view);
        picList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        try {
            if (obj != null) {
                return 1;
            }
            if (data == null) {
                return 0;
            } else {
                return data.size() == 0 ? 0 : data.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return super.getItem(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if (view == null) {
            view = View.inflate(context, layoutIds[0], null);
            vh = new ViewHolder(view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        if (obj != null) {
            DataBindForObj((InviteReciverEntity.UserBean) obj, vh);
        } else {
            DataBind(vh, i);
        }
        return view;
    }

    private void DataBindForObj(final InviteReciverEntity.UserBean data, ViewHolder vh) {
        try {
            vh.name.setText(data.username);
            Glide.with(context).load(data.avator).error(R.mipmap.default_useravatar).into(vh.avatar);
            vh.btnInviteComplaint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Observable.just(1)
                            .compose(RxUtils.<Integer>io_main())
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {


//                                    context.startActivity(new Intent(context, OnlineServiceActivity.class));

                                    openComplainDialog(data.user_id);
                                }
                            });
                }
            });
            vh.btnInviteEvaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Observable.just(1)
                            .compose(RxUtils.<Integer>io_main())
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    Intent intent = new Intent(context, EvaluateActivity.class);
                                    intent.putExtra("id", data.user_id + "");
                                    context.startActivity(intent);
                                }
                            });
                }
            });
            vh.chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChattingActivity.class);
                    intent.putExtra(ChattingFragment.RECIPIENTS, data.phoneNumber);
                    intent.putExtra(ChattingFragment.CONTACT_USER, data.username);
                    intent.putExtra("user_id", data.user_id);
                    intent.putExtra(ChattingFragment.CUSTOMER_SERVICE, false);
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.d("InviteDetailGridAdapter", "e:" + e);
        }
    }

    private void DataBind(ViewHolder vh, int i) {
        try {
            final InviteDetailEntity.ListBean entity = (InviteDetailEntity.ListBean) getItem(i);
            if (entity.user_id == 0) {
                data.remove(i);
                notifyDataSetChanged();
            }
            vh.name.setText(entity.username + "");
            Glide.with(context).load(entity.avator).into(vh.avatar);
            vh.btnInviteComplaint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Observable.just(1)
                            .compose(RxUtils.<Integer>io_main())
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    openComplainDialog(entity.user_id);
                                }
                            });
                }
            });
            vh.btnInviteEvaluate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Observable.just(1)
                            .compose(RxUtils.<Integer>io_main())
                            .subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(Integer integer) throws Exception {
                                    Intent intent = new Intent(context, EvaluateActivity.class);
                                    intent.putExtra("id", entity.user_id + "");
                                    context.startActivity(intent);
                                }
                            });
                }
            });
            vh.chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChattingActivity.class);
                    intent.putExtra(ChattingFragment.RECIPIENTS, entity.phoneNumber + "");
                    intent.putExtra(ChattingFragment.CONTACT_USER, entity.username + "");
                    intent.putExtra("user_id", entity.user_id);
                    intent.putExtra(ChattingFragment.CUSTOMER_SERVICE, false);
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.d("InviteDetailGridAdapter", "e:" + e);
        }
    }

    class ViewHolder {
        @BindView(R.id.img_invited_info_avatar)
        ImageView avatar;
        @BindView(R.id.tv_invited_info_name)
        TextView name;
        @BindView(R.id.img_invite_info_chat)
        ImageView chat;
        @BindView(R.id.btn_invite_complaint)
        Button btnInviteComplaint;
        @BindView(R.id.btn_invite_evaluate)
        Button btnInviteEvaluate;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

    }

    private void openComplainDialog(final int toUserId) {
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
                content = editText.getText().toString();
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showMessage("请输入投诉理由");
                    return;
                }
                if (list.size() == 0) {
                    Commt(toUserId);
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    File mFile = new File(list.get(i));
                    if (mFile.exists()) {
                        upload(getToken(), mFile, i, toUserId);
                    } else {
                        ShowDialog.dissmiss();
                        ToastUtil.showMessage("获取图片异常");
                    }
                }
            }
        });
    }


    public class ImageBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            index = list.size() != 3 ? list.size() + 1 : list.size();
            return index;
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.release_imageitem, null);
            ImageView ImageView = (android.widget.ImageView) convertView.findViewById(R.id.image);
            if (index != 3 && index - 1 == position)
                ImageView.setBackgroundResource(R.mipmap.icon_releasetalk_add);
            else
                showImageView(ImageView, list.get(position));
            return convertView;
        }
    }

    private void showImageView(ImageView iamgeView, String avatar) {
        Glide.with(context)
                .load(avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iamgeView);
    }

//    private JSONArray JSONArray = new JSONArray();

    private void upload(final String uploadToken, File uploadFile, final int i, final int toUserId) {
        if (uploadManager == null) {
            uploadManager = new UploadManager();
        }
        picList.clear();
        uploadManager.put(uploadFile, null, uploadToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, com.qiniu.android.http.ResponseInfo respInfo, org.json.JSONObject jsonData) {
                        if (respInfo.isOK()) {
                            try {
                                String fileKey = jsonData.getString("key");
                                final String url = "http://oez2a4f3v.bkt.clouddn.com/" + fileKey;
//                                JSONArray.put(url);
                                picList.add(url);
                                if (picList.size() == index - 1) {
                                    Commt(toUserId);
                                }
                            } catch (Exception e) {
                                Log.e("gy", "图片失败：" + e.toString());
                                ShowDialog.dissmiss();
                                ToastUtil.showMessage("上传图片失败");
                            }
                        } else {
                            ShowDialog.dissmiss();
                            ToastUtil.showMessage("上传图片失败");
                        }
                    }
                }, null);
    }

    public void Commt(int toUserId) {
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().UserInfo.getUserId());
        params.put("toUserId", toUserId);

        params.put("remark", content);
        if (list.size() != 0) {

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < picList.size(); i++) {
                sb.append(picList.get(i)+",");
            }
            params.put("images", sb.toString());
        }

        HttpUtil.get(Url.complain, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                try {
                    DBLog.e("投诉：", response.toString());
                    if (response.getInt("code") == 0) {
                        ToastUtil.showMessage("投诉信息已提交，请联系客服");
                        context.startActivity(new Intent(context, OnlineServiceActivity.class));
                        dialog.dismiss();

                    } else {
                        ToastUtil.showMessage(response.getString("errorDescription"));
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

    private String getToken() {
        Auth auth = Auth.create(QiNiuConfig.QINIU_AK, QiNiuConfig.QINIU_SK);
        return auth.uploadToken("yxin");
    }

}

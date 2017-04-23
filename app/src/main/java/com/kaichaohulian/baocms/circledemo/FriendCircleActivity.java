package com.kaichaohulian.baocms.circledemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.ReleaseTalkActivity;
import com.kaichaohulian.baocms.app.ActivityUtil;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.circledemo.adapter.CircleAdapter;
import com.kaichaohulian.baocms.circledemo.bean.CircleItem;
import com.kaichaohulian.baocms.circledemo.bean.CommentConfig;
import com.kaichaohulian.baocms.circledemo.bean.CommentItem;
import com.kaichaohulian.baocms.circledemo.bean.FavortItem;
import com.kaichaohulian.baocms.circledemo.mvp.presenter.CirclePresenter;
import com.kaichaohulian.baocms.circledemo.mvp.view.ICircleView;
import com.kaichaohulian.baocms.circledemo.utils.CommonUtils;
import com.kaichaohulian.baocms.circledemo.widgets.CommentListView;
import com.kaichaohulian.baocms.circledemo.widgets.DivItemDecoration;
import com.kaichaohulian.baocms.db.DataHelper;
import com.kaichaohulian.baocms.http.HttpUtil;
import com.kaichaohulian.baocms.http.Url;
import com.kaichaohulian.baocms.qiniu.Auth;
import com.kaichaohulian.baocms.qiniu.QiNiuConfig;
import com.kaichaohulian.baocms.utils.DBLog;
import com.kaichaohulian.baocms.view.ShowDialog;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.apache.http.Header;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;

public class FriendCircleActivity extends BaseActivity implements ICircleView {
    protected static final String TAG = FriendCircleActivity.class.getSimpleName();
    private CircleAdapter mAdapter;
    private LinearLayout mEditTextBody;
    private EditText mEditText;
    private ImageView sendIv;

    private int mScreenHeight;
    private int mEditTextBodyHeight;
    private int mCurrentKeyboardH;
    private int mSelectCircleItemH;
    private int mSelectCommentItemOffset;

    private CirclePresenter mPresenter;
    private CommentConfig mCommentConfig;
    private SuperRecyclerView recyclerView;
    private RelativeLayout bodyLayout;
    private LinearLayoutManager layoutManager;
//    private TitleBar titleBar;

    private final static int TYPE_PULLREFRESH = 1;
    private final static int TYPE_UPLOADREFRESH = 2;

    @Override
    protected void onResume() {
        super.onResume();
        addHttpData(TYPE_PULLREFRESH);
    }

    @Override
    public void setContent() {
        setContentView(R.layout.shoppingcircle_activity);
    }

    @Override
    public void initData() {
        mPresenter = new CirclePresenter(getActivity());
        mPresenter.attachView(this);
        initView();
        addHttpData(TYPE_PULLREFRESH);
    }

    @SuppressLint({"ClickableViewAccessibility", "InlinedApi"})
    public void initView() {
        initTitle();
        recyclerView = getId(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DivItemDecoration(1, true));
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        recyclerView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mEditTextBody.getVisibility() == View.VISIBLE) {
                    updateEditTextBodyVisible(View.GONE, null);
                    return true;
                }
                return false;
            }
        });

        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        mPresenter.loadData(TYPE_PULLREFRESH);
                        addHttpData(TYPE_PULLREFRESH);
                        recyclerView.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Glide.with(FriendCircleActivity.this).resumeRequests();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(FriendCircleActivity.this).pauseRequests();
                }

            }
        });


        mAdapter = new CircleAdapter(getActivity(), MyApplication.getInstance().UserInfo.getUserId() + "");
        mAdapter.setCirclePresenter(mPresenter);
        recyclerView.setAdapter(mAdapter);

        mEditTextBody = getId(R.id.editTextBodyLl);
        mEditText = getId(R.id.circleEt);
        sendIv = getId(R.id.sendIv);
        sendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPresenter != null) {
                    //发布评论
                    String content = mEditText.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        Toast.makeText(getActivity(), "评论内容不能为空...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mPresenter.addComment(content, mCommentConfig);
                    evaulate(mCommentConfig.exprienceId, mCommentConfig.evaluateId, content);
                }
                updateEditTextBodyVisible(View.GONE, null);
            }
        });

        setViewTreeObserver();
    }

    @Override
    public void initEvent() {

    }

    private void initTitle() {
        setCenterTitle("买家圈");
        ImageView ImageView = setIm1_view(R.mipmap.icon_shoppingcircle_send);
        ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtil.next(getActivity(), ReleaseTalkActivity.class);
            }
        });
    }


    private void setViewTreeObserver() {
        bodyLayout = getId(R.id.bodyLayout);
        final ViewTreeObserver swipeRefreshLayoutVTO = bodyLayout.getViewTreeObserver();
        swipeRefreshLayoutVTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                bodyLayout.getWindowVisibleDisplayFrame(r);
                int statusBarH = getStatusBarHeight();//状态栏高度
                int screenH = bodyLayout.getRootView().getHeight();
                if (r.top != statusBarH) {
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);
                Log.d(TAG, "screenH＝ " + screenH + " &keyboardH = " + keyboardH + " &r.bottom=" + r.bottom + " &top=" + r.top + " &statusBarH=" + statusBarH);

                if (keyboardH == mCurrentKeyboardH) {//有变化时才处理，否则会陷入死循环
                    return;
                }

                mCurrentKeyboardH = keyboardH;
                mScreenHeight = screenH;//应用屏幕的高度
                mEditTextBodyHeight = mEditTextBody.getHeight();

                //偏移listview
                if (layoutManager != null && mCommentConfig != null) {
                    layoutManager.scrollToPositionWithOffset(mCommentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE, getListviewOffset(mCommentConfig));
                }
            }
        });
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
//           if(mEditTextBody != null && mEditTextBody.getVisibility() == View.VISIBLE){
//        	   mEditTextBody.setVisibility(View.GONE);
//        	   return true;
//           }
//        }
//		return super.onKeyDown(keyCode, event);
//	}

    @Override
    public void update2DeleteCircle(String circleId) {
        Log.i("tag", "update2DeleteCircle 1111");
        List<CircleItem> circleItems = mAdapter.getDatas();
        for (int i = 0; i < circleItems.size(); i++) {
            if (circleId.equals(circleItems.get(i).getId())) {
                circleItems.remove(i);
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void update2AddFavorite(int circlePosition, FavortItem addItem) {
        Log.i("tag", "update2AddFavorite 2222");
        if (addItem != null) {
            CircleItem item = (CircleItem) mAdapter.getDatas().get(circlePosition);
            item.getFavorters().add(addItem);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void update2DeleteFavort(int circlePosition, String favortId) {
        Log.i("tag", "update2DeleteFavort 33333");
        CircleItem item = (CircleItem) mAdapter.getDatas().get(circlePosition);
        List<FavortItem> items = item.getFavorters();
        for (int i = 0; i < items.size(); i++) {
            if (favortId.equals(items.get(i).getId())) {
                items.remove(i);
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void update2AddComment(int circlePosition, CommentItem addItem) {
        Log.i("tag", "update2AddComment 444444");
        if (addItem != null) {
            CircleItem item = (CircleItem) mAdapter.getDatas().get(circlePosition);
            item.getComments().add(addItem);
            mAdapter.notifyDataSetChanged();
        }
        //清空评论文本
        mEditText.setText("");
    }

    @Override
    public void update2DeleteComment(int circlePosition, String commentId) {
        CircleItem item = (CircleItem) mAdapter.getDatas().get(circlePosition);
        List<CommentItem> items = item.getComments();
        for (int i = 0; i < items.size(); i++) {
            if (commentId.equals(items.get(i).getId())) {
                items.remove(i);
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
        mCommentConfig = commentConfig;
        mEditTextBody.setVisibility(visibility);

        measureCircleItemHighAndCommentItemOffset(commentConfig);

        if (View.VISIBLE == visibility) {
            mEditText.requestFocus();
            //弹出键盘
            CommonUtils.showSoftInput(mEditText.getContext(), mEditText);

        } else if (View.GONE == visibility) {
            //隐藏键盘
            CommonUtils.hideSoftInput(mEditText.getContext(), mEditText);
        }
    }

    @Override
    public void update2loadData(int loadType, List<CircleItem> datas) {
        if (loadType == TYPE_PULLREFRESH) {
            mAdapter.setDatas(datas);
        } else if (loadType == TYPE_UPLOADREFRESH) {
            mAdapter.getDatas().addAll(datas);
        }
        mAdapter.notifyDataSetChanged();

        if (mAdapter.getDatas().size() < 45 + CircleAdapter.HEADVIEW_SIZE) {
            recyclerView.setupMoreListener(new OnMoreListener() {
                @Override
                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addHttpData(TYPE_UPLOADREFRESH);
//                            mPresenter.loadData(TYPE_UPLOADREFRESH);
                        }
                    }, 2000);

                }
            }, 1);
        } else {
            recyclerView.removeMoreListener();
        }

    }


    /**
     * 测量偏移量
     *
     * @param commentConfig
     * @return
     */
    private int getListviewOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return 0;
        //这里如果你的listview上面还有其它占高度的控件，则需要减去该控件高度，listview的headview除外。
        //int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight;
//        int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight - titleBar.getHeight();  // 带顶部高度
        int listviewOffset = mScreenHeight - mSelectCircleItemH - mCurrentKeyboardH - mEditTextBodyHeight;
        if (commentConfig.commentType == CommentConfig.Type.REPLY) {
            //回复评论的情况
            listviewOffset = listviewOffset + mSelectCommentItemOffset;
        }
        Log.i(TAG, "listviewOffset : " + listviewOffset);
        return listviewOffset;
    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return;

        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        //只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem = layoutManager.getChildAt(commentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE - firstPosition);

        if (selectCircleItem != null) {
            mSelectCircleItemH = selectCircleItem.getHeight();
        }

        if (commentConfig.commentType == CommentConfig.Type.REPLY) {
            //回复评论的情况
            CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
            if (commentLv != null) {
                //找到要回复的评论view,计算出该view距离所属动态底部的距离
                View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                if (selectCommentItem != null) {
                    //选择的commentItem距选择的CircleItem底部的距离
                    mSelectCommentItemOffset = 0;
                    View parentView = selectCommentItem;
                    do {
                        int subItemBottom = parentView.getBottom();
                        parentView = (View) parentView.getParent();
                        if (parentView != null) {
                            mSelectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                        }
                    } while (parentView != null && parentView != selectCircleItem);
                }
            }
        }
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }

    private int limit = 1;

    public void addHttpData(final int type) {
        if (type == TYPE_PULLREFRESH)
            limit = 1;
        else
            limit++;
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("page", limit + "");
        HttpUtil.post(Url.findAll, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                try {
                    DBLog.i("tag", response.toString());
                    if (response.getInt("code") == 0) {
                        response = response.getJSONObject("dataObject");
                        org.json.JSONArray JSONArray = response.getJSONArray("experiences");
                        mPresenter.loadData(type, JSONArray);
                    }
                } catch (Exception e) {
                    showToastMsg("数据解析异常...");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                showToastMsg("请求服务器失败");
                DBLog.e("tag", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }

    /**
     * 评论回复
     */
    public void evaulate(String Id, String evaluateId, final String content) {
        RequestParams params = new RequestParams();
        params.put("userId", MyApplication.getInstance().UserInfo.getUserId());    // 评论人ID
        params.put("exprienceId", Id);   // 心得ID
        params.put("content", content);   // 内容

        evaluateId = evaluateId == null ? "0" : evaluateId;
        params.put("evaluateId", evaluateId);   // 评论ID
        params.put("parentId", evaluateId);        // 评论父类ID

        HttpUtil.post(Url.evaulate, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                try {
                    DBLog.i("tag", response.toString());
                    if (response.getInt("code") == 0) {
                        showToastMsg("评论成功...");
                    }
                } catch (Exception e) {
                    showToastMsg("数据解析异常...");
                    e.printStackTrace();
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

    private List<String> list = new ArrayList<>();
    public static final int REQUEST_CODE = 200;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                list = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                if (list.size() != 0) {
                    File mFile = new File(list.get(0));
                    if (mFile.exists()) {
                        upload(getToken(), mFile);
                    } else {
                        ShowDialog.dissmiss();
                        showToastMsg("获取图片异常");
                    }
                }
            }
        }
    }

    private UploadManager uploadManager;

    private void upload(String uploadToken, File uploadFile) {
        if (uploadManager == null) {
            uploadManager = new UploadManager();
        }
        uploadManager.put(uploadFile, null, uploadToken,
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, com.qiniu.android.http.ResponseInfo respInfo, org.json.JSONObject jsonData) {
                        if (respInfo.isOK()) {
                            try {
                                String fileKey = jsonData.getString("key");
                                final String url = "http://oez2a4f3v.bkt.clouddn.com/" + fileKey;
                                setHeadBg(url);
                            } catch (Exception e) {
                                ShowDialog.dissmiss();
                                showToastMsg("上传图片失败");
                            }
                        } else {
                            ShowDialog.dissmiss();
                            showToastMsg("上传图片失败");
                        }
                    }
                }, null);
    }

    private String getToken() {
        Auth auth = Auth.create(QiNiuConfig.QINIU_AK, QiNiuConfig.QINIU_SK);
        return auth.uploadToken("yxin");
    }


    DataHelper mDataHelper;

    /**
     * 设置背景图
     *
     * @param
     */
    public void setHeadBg(final String avatar) {
        if (mDataHelper == null) {
            mDataHelper = new DataHelper(getApplication());
        }
        RequestParams params = new RequestParams();
        params.put("id", MyApplication.getInstance().UserInfo.getUserId());
        params.put("avatar", avatar);
        HttpUtil.post(Url.updateBack, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, org.json.JSONObject response) {
                try {
                    DBLog.i("更换背景图", response.toString());
                    if (response.getInt("code") == 0) {
                        mAdapter.notifyDataSetChanged();
                        MyApplication.getInstance().UserInfo.setBackAvatar(avatar);
                        mDataHelper.UpdateUserInfo(MyApplication.getInstance().UserInfo);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                DBLog.e("更换背景图", statusCode + ":" + responseString);
                ShowDialog.dissmiss();
            }
        });
    }
}

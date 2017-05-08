package com.kaichaohulian.baocms.activity;

import android.app.Dialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.AdversDetailAdapter;
import com.kaichaohulian.baocms.app.MyApplication;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.AdversDetailBean;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.util.TitleUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdverDetailActivity extends BaseActivity {

    @BindView(R.id.tv_adver_detail_time)
    TextView tvTime;
    @BindView(R.id.lv_adver_detail)
    RecyclerView mRecyclerView;
    private TextView dialogMoney;

    private View headView;
    private AdversDetailAdapter mAdapter;
    private List<String> mList;
    private ImageView headAvatar;
    private TextView tvTitle, tvContent,tvId,tvName;

    private String adverId;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_adver_detail);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        adverId = getIntent().getStringExtra("adverId");
        Log.e("gy","广告Id:"+adverId);
        new TitleUtils(this).setTitle("详情");
        laodAdverDetail();
    }

    @Override
    public void initView() {
        headView = View.inflate(this, R.layout.head_adver_detail, null);
        headAvatar = (ImageView) headView.findViewById(R.id.img_adver_detail_avatar);
        tvName = (TextView) headView.findViewById(R.id.tv_adver_detail_name);
        tvTitle = (TextView) headView.findViewById(R.id.tv_adver_detail_title);
        tvContent = (TextView) headView.findViewById(R.id.tv_adver_detail_content);
        tvId = (TextView) headView.findViewById(R.id.tv_adver_detail_id);
        mList = new ArrayList<>();
        mAdapter = new AdversDetailAdapter(R.layout.item_adver_detail, mList);
        mAdapter.addHeaderView(headView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initEvent() {
    }

    private void laodAdverDetail() {
        map.put("userId", MyApplication.getInstance().UserInfo.getUserId()+"");
        map.put("advertId",adverId);
        RetrofitClient.getInstance().createApi().adverDetail(map)
                .compose(RxUtils.<HttpResult<AdversDetailBean>>io_main())
                .subscribe(new BaseObjObserver<AdversDetailBean>(this, "记载中") {
                    @Override
                    protected void onHandleSuccess(AdversDetailBean adversDetailBean) {
                        setInfo(adversDetailBean);
                    }
                });
    }

    private void setInfo(AdversDetailBean adversDetailBean) {
        tvTime.setText(adversDetailBean.getAdvert().getCreatedTime());
        Glide.with(MyApplication.getInstance())
                .load(adversDetailBean.getAdvert().getAvter())
                .error(R.mipmap.default_image)
                .crossFade()
                .into(headAvatar);
        tvName.setText(adversDetailBean.getAdvert().getUserName()+"");
        tvId.setText(adversDetailBean.getAdvert().getUserId()+"");
        tvTitle.setText(adversDetailBean.getAdvert().getTitle());
        tvContent.setText(adversDetailBean.getAdvert().getContext());
        if(!TextUtils.isEmpty(adversDetailBean.getRedMoney())){
            openRedPackageDialog(adversDetailBean.getRedMoney());
        }
        mList.clear();
        String[] split = adversDetailBean.getAdvert().getImage().split(",");
        for (int i = 0; i < split.length; i++) {
            mList.add(split[i]);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 红包对话框
     */
    private void openRedPackageDialog(String redMoney) {
        View view = View.inflate(this, R.layout.pop_adver_detail_red, null);
        dialogMoney = (TextView) view.findViewById(R.id.tv_pop_red_package_money);
        dialogMoney.setText(redMoney);
        ImageView dialogImg = (ImageView) view.findViewById(R.id.img_pop_red_package_close);
        final Dialog dialog = new Dialog(this, R.style.dialog_type);
        dialog.setContentView(view);
        dialog.show();
        dialogImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

}

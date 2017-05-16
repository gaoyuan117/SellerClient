package com.kaichaohulian.baocms.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.EvaluateAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.LableBean;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.util.ArrayList;
import java.util.List;

public class EvaluateActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private GridView mGridView;
    private EvaluateAdapter mAdapter;
    private List<String> mList;
    private TextView tvLabel;
    private StringBuffer stringBuffer;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContent() {
        setContentView(R.layout.activity_evaluate);
    }

    @Override
    public void initData() {
        loadLable();
    }

    @Override
    public void initView() {
        tvLabel = (TextView) findViewById(R.id.tv_evaluate_label);
        mGridView = (GridView) findViewById(R.id.gv_evaluate);
        mList = new ArrayList<>();
        list = new ArrayList<>();
        stringBuffer = new StringBuffer();
        mAdapter = new EvaluateAdapter(mList, this);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(this);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

    }


    @Override
    public void initEvent() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_item_evaluate_label);
        if (!checkBox.isChecked()) {

            list.add(mList.get(position));
            checkBox.setChecked(true);
        } else {
            list.remove(mList.get(position));
            checkBox.setChecked(false);
        }

        for (int i = 0; i < list.size(); i++) {
            stringBuffer.append(list.get(i) + " ");
        }
        if (!TextUtils.isEmpty(stringBuffer.toString())) {
            tvLabel.setText(stringBuffer.toString());
        } else {
            tvLabel.setText("请给他(她)一个评价吧");
        }
        stringBuffer.setLength(0);
    }

    private void loadLable() {
        RetrofitClient.getInstance().createApi().loadLable().compose(RxUtils.<HttpArray<LableBean>>io_main())
                .subscribe(new BaseListObserver<LableBean>(this, "") {
                    @Override
                    protected void onHandleSuccess(List<LableBean> list) {
                        if (list == null) return;
                            mList.clear();
                        for (int i = 0; i < list.size(); i++) {
                            mList.add(list.get(i).getName());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }
}


package com.kaichaohulian.baocms;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.activity.PositionActivity;
import com.kaichaohulian.baocms.adapter.EvaluateAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.LableBean;
import com.kaichaohulian.baocms.entity.PositionEntity;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;
import com.kaichaohulian.baocms.util.TitleUtils;

import java.util.ArrayList;
import java.util.List;

public class HobbyActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private GridView mGridView;
    private EvaluateAdapter mAdapter;
    private List<String> mList;
    private TextView tvLabel;
    private StringBuffer stringBuffer;
    private List<String> list;
    private TextView tvEva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContent() {
        setContentView(R.layout.activity_hobby);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setCenterTitle("我的爱好");
        tvLabel = (TextView) findViewById(R.id.tv_hobby_label);
        mGridView = (GridView) findViewById(R.id.gv_hobby);
        tvEva = (TextView) findViewById(R.id.tv_hobby_eva);
        mList = new ArrayList<>();
        list = new ArrayList<>();
        stringBuffer = new StringBuffer();
        mAdapter = new EvaluateAdapter(mList, this);
        mGridView.setAdapter(mAdapter);
        LoadData();
        mGridView.setOnItemClickListener(this);
        mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        tvEva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder builder = new StringBuilder();
                if (list.size() == 0) return;
                for (int i = 0; i < list.size(); i++) {
                    builder.append(list.get(i) + " ");
                }
                Intent intent = new Intent();
                intent.putExtra("result", builder.toString());
                setResult(RESULT_OK, intent);

                finish();

            }
        });

    }

    private void LoadData(){
        RetrofitClient.getInstance().createApi().getall(2)
                .compose(RxUtils.<HttpArray<PositionEntity>>io_main())
                .subscribe(new BaseListObserver<PositionEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(List<PositionEntity> l) {
                        for (int i = 0; i < l.size(); i++) {
                            mList.add(l.get(i).getName());
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
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
            tvLabel.setText("请选择");
        }
        stringBuffer.setLength(0);
    }

}

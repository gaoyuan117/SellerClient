package com.kaichaohulian.baocms.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.adapter.MyAlbumAdapter;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.PositionEntity;
import com.kaichaohulian.baocms.http.HttpArray;
import com.kaichaohulian.baocms.http.HttpResult;
import com.kaichaohulian.baocms.retrofit.RetrofitClient;
import com.kaichaohulian.baocms.rxjava.BaseListObserver;
import com.kaichaohulian.baocms.rxjava.BaseObjObserver;
import com.kaichaohulian.baocms.rxjava.RxUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 职业
 */
public class PositionActivity extends BaseActivity implements AdapterView.OnItemClickListener {


    private String position;
    @BindView(R.id.position_list)
    ListView mListView;
    private ArrayList<PositionEntity> data;
    private Adapter adapter;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_position);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        setCenterTitle("我的职业");
    }

    @Override
    public void initView() {
        data = new ArrayList<>();
        adapter = new Adapter(this, data);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        loadData();
    }

    private void loadData() {
        RetrofitClient.getInstance().createApi().getall(3)
                .compose(RxUtils.<HttpArray<PositionEntity>>io_main())
                .subscribe(new BaseListObserver<PositionEntity>(getActivity()) {
                    @Override
                    protected void onHandleSuccess(List<PositionEntity> list) {
                        data.addAll(list);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent();
        intent.putExtra("result", data.get(i).name);
        setResult(RESULT_OK, intent);
        finish();
    }


    class Adapter extends BaseAdapter {
        private Context context;
        private List<PositionEntity> list;

        public Adapter(Context context, List<PositionEntity> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list == null ? 0 : list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            try{
                ViewHolder vh;
                if (view == null) {
                    view = View.inflate(context, R.layout.item_positionlist, null);
                    vh = new ViewHolder(view);
                    view.setTag(vh);
                } else {
                    vh = (ViewHolder) view.getTag();
                }
                PositionEntity entity = (PositionEntity) getItem(i);
                vh.tv.setText(entity.name);
                vh.title.setText(entity.remark);
            }catch (Exception e){
                e.printStackTrace();
            }
            return view;

        }

        class ViewHolder {
            TextView tv, title;

            public ViewHolder(View view) {
                tv = (TextView) view.findViewById(R.id.tv_position);
                title = (TextView) tv.findViewById(R.id.title_position);
            }
        }
    }

}

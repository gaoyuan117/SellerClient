package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;
import com.kaichaohulian.baocms.entity.WhoCanSeeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LMS on 2017/2/18.
 */

public class ReleaseTalkWhoCanSeeActivity extends BaseActivity {

    private ListView listWhoCanSee;
    private ListItemAdapter adapter;

    private List<WhoCanSeeEntity> data;
    private LayoutInflater inflater;

    private String title;
    private String privilegeType;

    private int USERS_CODE = 500;

    private ArrayList<Integer> users;

    @Override
    public void setContent() {
        setContentView(R.layout.activity_who_can_see);
    }

    @Override
    public void initData() {
        data = new ArrayList<>();
        data.add(new WhoCanSeeEntity("公开", "所以朋友可见", true));
        data.add(new WhoCanSeeEntity("秘密", "仅自己可见", false));
        data.add(new WhoCanSeeEntity("部分可见", "选中的朋友可见", false));
        data.add(new WhoCanSeeEntity("不给谁看", "选中的朋友不可见", false));
        inflater = LayoutInflater.from(this);
    }

    @Override
    public void initView() {
        setLeftTitle("取消");
        setCenterTitle("谁可以看");
        TextView rightTv = setRightTitle("完成");
        rightTv.setTextColor(getResources().getColor(R.color.green));

        listWhoCanSee = getId(R.id.list_item_who_can_see);
        adapter = new ListItemAdapter();
        listWhoCanSee.setAdapter(adapter);

        rightTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("title", title);
                intent.putExtra("privilegeType", privilegeType);
                intent.putIntegerArrayListExtra("users",users);
                ReleaseTalkWhoCanSeeActivity.this.setResult(0, intent);
                ReleaseTalkWhoCanSeeActivity.this.finish();
            }
        });
    }

    @Override
    public void initEvent() {
        listWhoCanSee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < data.size(); i++) {
                    WhoCanSeeEntity whoCanSeeEntity = data.get(i);
                    if (i == position) {
                        whoCanSeeEntity.setSelect(true);
                        title = whoCanSeeEntity.getKey();
                    } else {
                        whoCanSeeEntity.setSelect(false);
                    }
                }
                adapter.notifyDataSetChanged();
                if (position == 0) {
                    privilegeType = "PUBLIC";
                } else if (position == 1) {
                    privilegeType = "PRIVATE";
                } else if (position == 2) {
                    privilegeType = "SOME";
                } else {
                    privilegeType = "SOMENO";
                }
            }
        });

    }

    class ListItemAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = inflater.inflate(R.layout.who_can_see_item, null);
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.subTitle = (TextView) convertView.findViewById(R.id.sub_title);
                viewHolder.isSelect = (ImageView) convertView.findViewById(R.id.is_select_img);
                viewHolder.fromContact = (TextView) convertView.findViewById(R.id.from_contact);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            WhoCanSeeEntity whoCanSeeEntity = data.get(position);
            viewHolder.title.setText(whoCanSeeEntity.getKey());
            viewHolder.subTitle.setText(whoCanSeeEntity.getValue());
            if (whoCanSeeEntity.isSelect()) {
                viewHolder.isSelect.setVisibility(View.VISIBLE);
                if(position==2 || position == 3){
                    viewHolder.fromContact.setVisibility(View.VISIBLE);
                }
            } else {
                viewHolder.isSelect.setVisibility(View.INVISIBLE);
                if(position==2 || position == 3){
                    viewHolder.fromContact.setVisibility(View.GONE);
                }
            }

            viewHolder.fromContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ReleaseTalkWhoCanSeeActivity.this, RemindFriendsSeeActivity.class);
                    intent.putExtra("type",0);
                    startActivityForResult(intent,USERS_CODE);
                }
            });

            return convertView;
        }

        class ViewHolder {
            TextView title;
            TextView subTitle;
            TextView fromContact;
            ImageView isSelect;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == USERS_CODE){
            users = data.getIntegerArrayListExtra("users");
        }
    }
}

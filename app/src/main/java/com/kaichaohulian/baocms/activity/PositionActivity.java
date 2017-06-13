package com.kaichaohulian.baocms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kaichaohulian.baocms.R;
import com.kaichaohulian.baocms.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 职业
 */
public class PositionActivity extends BaseActivity {

    @BindView(R.id.tv_position_it)
    TextView tvPositionIt;
    @BindView(R.id.tv_position_zz)
    TextView tvPositionZz;
    @BindView(R.id.tv_position_yl)
    TextView tvPositionYl;
    @BindView(R.id.tv_position_jr)
    TextView tvPositionJr;
    @BindView(R.id.tv_position_sy)
    TextView tvPositionSy;
    @BindView(R.id.tv_position_wh)
    TextView tvPositionWh;
    @BindView(R.id.tv_position_ys)
    TextView tvPositionYs;
    @BindView(R.id.tv_position_fl)
    TextView tvPositionFl;
    @BindView(R.id.tv_position_jy)
    TextView tvPositionJy;
    @BindView(R.id.tv_position_xz)
    TextView tvPositionXz;
    @BindView(R.id.tv_position_xs)
    TextView tvPositionXs;
    @BindView(R.id.tv_position_other)
    TextView tvPositionOther;

    private String position;

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

    }

    @Override
    public void initEvent() {

    }

    @OnClick({R.id.tv_position_it, R.id.tv_position_zz, R.id.tv_position_yl, R.id.tv_position_jr, R.id.tv_position_sy, R.id.tv_position_wh, R.id.tv_position_ys, R.id.tv_position_fl, R.id.tv_position_jy, R.id.tv_position_xz, R.id.tv_position_xs, R.id.tv_position_other})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_position_it:
                position = tvPositionIt.getText().toString();
                break;
            case R.id.tv_position_zz:
                position = tvPositionZz.getText().toString();
                break;
            case R.id.tv_position_yl:
                position = tvPositionYl.getText().toString();
                break;
            case R.id.tv_position_jr:
                position = tvPositionJr.getText().toString();
                break;
            case R.id.tv_position_sy:
                position = tvPositionSy.getText().toString();
                break;
            case R.id.tv_position_wh:
                position = tvPositionWh.getText().toString();
                break;
            case R.id.tv_position_ys:
                position = tvPositionYs.getText().toString();
                break;
            case R.id.tv_position_fl:
                position = tvPositionFl.getText().toString();
                break;
            case R.id.tv_position_jy:
                position = tvPositionJy.getText().toString();
                break;
            case R.id.tv_position_xz:
                position = tvPositionXz.getText().toString();
                break;
            case R.id.tv_position_xs:
                position = tvPositionXs.getText().toString();
                break;
            case R.id.tv_position_other:
                position = "默认";
                break;

        }
        Intent intent = new Intent();
        intent.putExtra("result", position);
        setResult(RESULT_OK, intent);

        finish();
    }
}

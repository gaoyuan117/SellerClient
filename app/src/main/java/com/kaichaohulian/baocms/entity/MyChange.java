package com.kaichaohulian.baocms.entity;

import android.content.Context;

import com.kaichaohulian.baocms.utils.SharedPrefsUtil;

/**
 * Created by liuyu on 2017/1/4.
 */

public class MyChange {

    private double myMoney = 835.50;
    private static MyChange myChange;

    private MyChange(){}

    public  static MyChange getInstance(){

        if(myChange == null){
            myChange = new MyChange();
            return  myChange;
        }else {
            return  myChange;
        }
    }

    public double getMyMoney(Context context){
        String rest = SharedPrefsUtil.getValue(context, "mychange", "0");

        return Double.valueOf(rest);
    }

    public void setMyMoney(Context context,double myMoney) {
        SharedPrefsUtil.putValue(context,"mychange", myChange+"");

    }


}

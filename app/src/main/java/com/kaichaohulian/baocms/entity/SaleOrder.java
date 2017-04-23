package com.kaichaohulian.baocms.entity;

/**
 * Created by kenton on 2017/2/9.
 */

public class SaleOrder {
    private String goodName;
    private String createTime;
    private int money;

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}

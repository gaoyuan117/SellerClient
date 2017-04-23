package com.kaichaohulian.baocms.entity;

/**
 * Created by liuyu on 2017/1/14.
 */

public class SellEntity {

    String headAvator;
    String date;
    String time;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    double income;

    public String getHeadAvator() {
        return headAvator;
    }

    public void setHeadAvator(String headAvator) {
        this.headAvator = headAvator;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}

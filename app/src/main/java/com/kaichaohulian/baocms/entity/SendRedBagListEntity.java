package com.kaichaohulian.baocms.entity;

/**
 * Created by liuyu on 2016/12/16.
 */

public class SendRedBagListEntity {

    private String redtype;
    private int type;           // 0是普通红包1是拼手气红包
    private String createdTime;
    private double sum;            // 红包总金额
    private String status;      //
    private int redCount;       // 红包个数
    private int leftRedCount;  // 剩余红包个数

    public String getRedtype() {
        return redtype;
    }

    public void setRedtype(String redtype) {
        this.redtype = redtype;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRedCount() {
        return redCount;
    }

    public void setRedCount(int redCount) {
        this.redCount = redCount;
    }

    public int getLeftRedCount() {
        return leftRedCount;
    }

    public void setLeftRedCount(int leftRedCount) {
        this.leftRedCount = leftRedCount;
    }

    @Override
    public String toString() {
        return "SendRedBagListEntity{" +
                "redtype='" + redtype + '\'' +
                ", type=" + type +
                ", createdTime='" + createdTime + '\'' +
                ", sum=" + sum +
                ", status='" + status + '\'' +
                ", redCount=" + redCount +
                ", leftRedCount=" + leftRedCount +
                '}';
    }
}

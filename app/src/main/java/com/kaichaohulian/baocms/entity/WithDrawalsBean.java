package com.kaichaohulian.baocms.entity;

/**
 * Created by Jimq on 2017/2/19.
 */

public class WithDrawalsBean {

    private int cashId;

    private int userId;

    private int money;

    private String addtime;

    private boolean status;

    private String account;

    private String bankName;

    private String bankNum;

    private String bankBranch;

    private String bankRealname;

    private String weixinAccount;

    private String zfbAccount;

    private String reason;

    public void setCashId(int cashId) {
        this.cashId = cashId;
    }

    public int getCashId() {
        return this.cashId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return this.money;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getAddtime() {
        return this.addtime;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean getStatus() {
        return this.status;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccount() {
        return this.account;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public String getBankNum() {
        return this.bankNum;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getBankBranch() {
        return this.bankBranch;
    }

    public void setBankRealname(String bankRealname) {
        this.bankRealname = bankRealname;
    }

    public String getBankRealname() {
        return this.bankRealname;
    }

    public void setWeixinAccount(String weixinAccount) {
        this.weixinAccount = weixinAccount;
    }

    public String getWeixinAccount() {
        return this.weixinAccount;
    }

    public void setZfbAccount(String zfbAccount) {
        this.zfbAccount = zfbAccount;
    }

    public String getZfbAccount() {
        return this.zfbAccount;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return this.reason;
    }
}

package com.kaichaohulian.baocms.entity;

/**
 * Created by liuyu on 2017/1/16.
 */

public class WithdrawDetailEntity {

    String bankAvartor;
    String lastFourNumber;
    String whichBank;
    String date;
    String time;
    String bankName;
    double account;
    double money;
    String bankNum;
    String addtime;

    public String getBankNum() {
        return bankNum;
    }

    public void setBankNum(String bankNum) {
        this.bankNum = bankNum;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAvartor() {
        return bankAvartor;
    }

    public void setBankAvartor(String bankAvartor) {
        this.bankAvartor = bankAvartor;
    }

    public String getLastFourNumber() {
        return lastFourNumber;
    }

    public void setLastFourNumber(String lastFourNumber) {
        this.lastFourNumber = lastFourNumber;
    }

    public String getWhichBank() {
        return whichBank;
    }

    public void setWhichBank(String whichBank) {
        this.whichBank = whichBank;
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

    public double getAccount() {
        return account;
    }

    public void setAccount(double account) {
        this.account = account;
    }
}

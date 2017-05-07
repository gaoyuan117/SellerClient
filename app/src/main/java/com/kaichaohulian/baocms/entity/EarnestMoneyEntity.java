package com.kaichaohulian.baocms.entity;

/**
 * Created by xzwzz on 2017/5/7.
 */

public class EarnestMoneyEntity {

    /**
     * getEarnestMoney : null
     * payEarnestMoney : null
     * beToAdd : 1
     * beInvite : null
     * appointment : null
     * noAppointment : null
     */

    private Object getEarnestMoney;
    private Object payEarnestMoney;
    private int beToAdd;
    private Object beInvite;
    private Object appointment;
    private Object noAppointment;

    public Object getGetEarnestMoney() {
        return getEarnestMoney;
    }

    public void setGetEarnestMoney(Object getEarnestMoney) {
        this.getEarnestMoney = getEarnestMoney;
    }

    public Object getPayEarnestMoney() {
        return payEarnestMoney;
    }

    public void setPayEarnestMoney(Object payEarnestMoney) {
        this.payEarnestMoney = payEarnestMoney;
    }

    public int getBeToAdd() {
        return beToAdd;
    }

    public void setBeToAdd(int beToAdd) {
        this.beToAdd = beToAdd;
    }

    public Object getBeInvite() {
        return beInvite;
    }

    public void setBeInvite(Object beInvite) {
        this.beInvite = beInvite;
    }

    public Object getAppointment() {
        return appointment;
    }

    public void setAppointment(Object appointment) {
        this.appointment = appointment;
    }

    public Object getNoAppointment() {
        return noAppointment;
    }

    public void setNoAppointment(Object noAppointment) {
        this.noAppointment = noAppointment;
    }
}

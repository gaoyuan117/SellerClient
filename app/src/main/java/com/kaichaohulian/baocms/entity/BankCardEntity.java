package com.kaichaohulian.baocms.entity;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by liuyu on 2016/12/24.
 */

public class BankCardEntity implements Serializable {

    private int id;

    private String createdTime;

    private int creator;

    private boolean isLocked;

    private String lastModifiedTime;

    private int lastModifier;

    private String timeStamp;

    private String cardNo;

    private String cardType;

    private String bankName;

    private String idcard;

    private String phoneNumber;

    private String username;

    private String oneOuota;

    private String dayusername;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedTime() {
        return this.createdTime;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getCreator() {
        return this.creator;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean getIsLocked() {
        return this.isLocked;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getLastModifiedTime() {
        return this.lastModifiedTime;
    }

    public void setLastModifier(int lastModifier) {
        this.lastModifier = lastModifier;
    }

    public int getLastModifier() {
        return this.lastModifier;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardType() {
        return this.cardType;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getIdcard() {
        return this.idcard;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setOneOuota(String oneOuota) {
        this.oneOuota = oneOuota;
    }

    public String getOneOuota() {
        return this.oneOuota;
    }

    public void setDayusername(String dayusername) {
        this.dayusername = dayusername;
    }

    public String getDayusername() {
        return this.dayusername;
    }
}

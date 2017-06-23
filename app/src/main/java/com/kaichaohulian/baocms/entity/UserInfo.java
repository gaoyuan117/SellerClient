package com.kaichaohulian.baocms.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 个人用户
 * Created by ljl on 2016/12/11.
 */
public class UserInfo implements Serializable {
    public static final String NEW_FRIENDS_USERNAME = "item_new_friends";
    public static final String GROUP_USERNAME = "item_groups";
    public static final String ID = "_id";
    public static final String USERID = "userId";
    public static final String CREATEDTIME = "createdTime";
    public static final String CREATOR = "creator";
    public static final String ISLOCKED = "isLocked";
    public static final String LASTMODIFIEDTIME = "lastModifiedTime";
    public static final String LASTMODIFIER = "lastModifier";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ACCOUNTNUMBER = "accountNumber";
    public static final String QRCODE = "qrCode";
    public static final String DISTRICTID = "districtId";
    public static final String SEX = "sex";
    public static final String THERMALSIGNATRUE = "thermalSignatrue";
    public static final String PHONENUMBER = "phoneNumber";
    public static final String USEREMAIL = "userEmail";
    public static final String BALANCE = "balance";
    public static final String AVATAR = "avatar";
    public static final String BACKAVATAR = "backAvatar";
    public static final String LOGINFAILEDCOUNT = "loginFailedCount";
    public static final String PAYPASSWORD = "payPassword";
    public static final String HOBBY="hobby";
    public static final String AGE="age";
    public static final String JOB="job";
    private int id;
    private int userId;
    private String createdTime;
    private String creator;
    private boolean isLocked;
    private String lastModifiedTime;
    private String lastModifier;
    private String username;
    private String password;
    private String accountNumber;
    private String qrCode;
    private String districtId;
    private String sex;
    private String thermalSignatrue;
    private String phoneNumber;
    private String userEmail;
    private String balance;
    private String avatar;
    private String backAvatar;
    private String industry;
    private int loginFailedCount;
    private int isfriend;
    private List<String> images;
    private String header; // 首字母
    private String token;
    private String labelName;
    private String payPassword; //支付密码
    private int age;
    private String job;
    private String hobby;
    private boolean isIsFirstLogin;

    public boolean getisFirstLogin() {
        return isIsFirstLogin;
    }

    public void setIsFirstLogin(boolean isFirstLogin) {
        isIsFirstLogin = isFirstLogin;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getThermalSignatrue() {
        return thermalSignatrue;
    }

    public void setThermalSignatrue(String thermalSignatrue) {
        this.thermalSignatrue = thermalSignatrue;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBackAvatar() {
        return backAvatar;
    }

    public void setBackAvatar(String backAvatar) {
        this.backAvatar = backAvatar;
    }

    public int getLoginFailedCount() {
        return loginFailedCount;
    }

    public void setLoginFailedCount(int loginFailedCount) {
        this.loginFailedCount = loginFailedCount;
    }

    public int getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(int isfriend) {
        this.isfriend = isfriend;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", createdTime='" + createdTime + '\'' +
                ", creator='" + creator + '\'' +
                ", isLocked=" + isLocked +
                ", lastModifiedTime='" + lastModifiedTime + '\'' +
                ", lastModifier='" + lastModifier + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", qrCode='" + qrCode + '\'' +
                ", districtId='" + districtId + '\'' +
                ", sex='" + sex + '\'' +
                ", thermalSignatrue='" + thermalSignatrue + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", balance='" + balance + '\'' +
                ", avatar='" + avatar + '\'' +
                ", backAvatar='" + backAvatar + '\'' +
                ", industry='" + industry + '\'' +
                ", loginFailedCount=" + loginFailedCount +
                ", isfriend=" + isfriend +
                ", images=" + images +
                ", header='" + header + '\'' +
                ", token='" + token + '\'' +
                ", labelName='" + labelName + '\'' +
                ", payPassword='" + payPassword + '\'' +
                ", age='" + age + '\'' +
                ", job='" + job + '\'' +
                ", hobby='" + hobby + '\'' +
                '}';
    }
}

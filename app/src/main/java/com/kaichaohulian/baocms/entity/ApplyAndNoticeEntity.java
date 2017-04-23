package com.kaichaohulian.baocms.entity;

import java.io.Serializable;

/**
 * 申请与通知
 * Created by ljl on 2016/12/19 0019.
 */
public class ApplyAndNoticeEntity implements Serializable {

    private int id;

    private String createdTime;

    private String status;

    private int userId;

    private String message;

    private String nickname;

    private String avatar;

    private String requestType;

    private int auditedBy;

    private String type;

    private int targetId;

    private String header;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public int getAuditedBy() {
        return auditedBy;
    }

    public void setAuditedBy(int auditedBy) {
        this.auditedBy = auditedBy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    @Override
    public String toString() {
        return "ApplyAndNoticeEntity{" +
                "id=" + id +
                ", createdTime='" + createdTime + '\'' +
                ", status='" + status + '\'' +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", requestType='" + requestType + '\'' +
                ", auditedBy=" + auditedBy +
                ", type='" + type + '\'' +
                ", targetId=" + targetId +
                ", header='" + header + '\'' +
                '}';
    }
}

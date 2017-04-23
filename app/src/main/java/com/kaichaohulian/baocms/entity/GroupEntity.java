package com.kaichaohulian.baocms.entity;

import java.util.List;

/**
 * Created by Administrator on 2017/1/1.
 */

public class GroupEntity {
    private int id;                 //
    private String createdTime;    //
    private String name;            //
    private int memberCount;       //
    private String status;          // 群状态 （活跃）
    private String groupNumber;    // 群号码
    private int capacity;          // 群最多人数
    private String chatGroupId;    // 荣联群id
    private String role;            // 群角色 OWNER 群主 USER 不通用户  ADMIN 管理员
    private List<String> avatar;    // 头像

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getChatGroupId() {
        return chatGroupId;
    }

    public void setChatGroupId(String chatGroupId) {
        this.chatGroupId = chatGroupId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<String> getAvatar() {
        return avatar;
    }

    public void setAvatar(List<String> avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "GroupEntity{" +
                "id=" + id +
                ", createdTime='" + createdTime + '\'' +
                ", name='" + name + '\'' +
                ", memberCount=" + memberCount +
                ", status='" + status + '\'' +
                ", groupNumber='" + groupNumber + '\'' +
                ", capacity=" + capacity +
                ", chatGroupId='" + chatGroupId + '\'' +
                ", role='" + role + '\'' +
                ", avatar=" + avatar +
                '}';
    }
}

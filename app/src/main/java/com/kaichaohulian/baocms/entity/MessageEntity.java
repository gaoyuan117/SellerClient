package com.kaichaohulian.baocms.entity;

import java.io.Serializable;

/**
 * 消息列表
 * Created by ljl on 2017/1/7.
 */
public class MessageEntity implements Serializable{

    public static final String ID = "_id";
    public static final String MSGID = "MsgId";
    public static final String TOID = "toId";
    public static final String NAME = "name";
    public static final String AVATAR = "avatar";
    public static final String TYPE = "type";
    public static final String MESSAGE = "message";
    public static final String CREATEDTIME = "createdTime";
    public static final String ISSEND = "isSend";
    public static final String ISREND = "isRend";
    public static final String DIRECTION = "Direction";
    public static final String MYUSERID = "MyUserId";
    public static final String ISTOP = "isTop";

    private int _id;
    private String MsgId;       // 消息ID
    private String toId;        // 发送者
    private String name;        // 发送者昵称
    private String avatar;      // 发送者头像
    private String type;           // 消息类型
    private String message;     // 消息内容
    private String createdTime; // 创建时间
    private int isRend;         // 是否已读 0 已读 1未读
    private int isTop;          // 是否置顶 0 是 1 否
    private String isSend;      // 消息发送状态
    private String Direction;   // 发送方
    private String MyUserId;    // 消息归属人

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getIsRend() {
        return isRend;
    }

    public void setIsRend(int isRend) {
        this.isRend = isRend;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public int getIsTop() {
        return isTop;
    }

    public void setIsTop(int isTop) {
        this.isTop = isTop;
    }

    public String getDirection() {
        return Direction;
    }

    public void setDirection(String direction) {
        Direction = direction;
    }

    public String getMyUserId() {
        return MyUserId;
    }

    public void setMyUserId(String myUserId) {
        MyUserId = myUserId;
    }

    @Override
    public String toString() {
        return "MessageEntity{" +
                "_id=" + _id +
                ", MsgId='" + MsgId + '\'' +
                ", toId='" + toId + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", isRend=" + isRend +
                ", isSend=" + isSend +
                ", isTop=" + isTop +
                ", Direction=" + Direction +
                ", MyUserId='" + MyUserId + '\'' +
                '}';
    }
}

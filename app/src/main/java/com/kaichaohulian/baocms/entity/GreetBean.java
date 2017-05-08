package com.kaichaohulian.baocms.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaoyuan on 2017/5/7.
 */

public class GreetBean implements Serializable {

    /**
     * rembers : 2
     * requestDTOs1 : [{"id":7,"createdTime":"2017-05-07 00:00:00","status":"接受","userId":7240,"message":"","nickname":"高原2号","avatar":"http://oez2a4f3v.bkt.clouddn.com/Fisv14RRoy-FaAcXO7KCY1UIdSZP","requestType":"FRIEND_APPLY","auditedBy":7236,"type":"RECEIVED","targetId":7236,"thermalSignatrue":null,"rembers":0},{"id":5,"createdTime":"2017-05-07 00:00:00","status":"接受","userId":7240,"message":"","nickname":"高原2号","avatar":"http://oez2a4f3v.bkt.clouddn.com/Fisv14RRoy-FaAcXO7KCY1UIdSZP","requestType":"FRIEND_APPLY","auditedBy":7240,"type":"SENT","targetId":7240,"thermalSignatrue":null,"rembers":0}]
     */

    private int rembers;
    private List<RequestDTOs1Bean> requestDTOs1;

    public int getRembers() {
        return rembers;
    }

    public void setRembers(int rembers) {
        this.rembers = rembers;
    }

    public List<RequestDTOs1Bean> getRequestDTOs1() {
        return requestDTOs1;
    }

    public void setRequestDTOs1(List<RequestDTOs1Bean> requestDTOs1) {
        this.requestDTOs1 = requestDTOs1;
    }

    public static class RequestDTOs1Bean {
        /**
         * id : 7
         * createdTime : 2017-05-07 00:00:00
         * status : 接受
         * userId : 7240
         * message :
         * nickname : 高原2号
         * avatar : http://oez2a4f3v.bkt.clouddn.com/Fisv14RRoy-FaAcXO7KCY1UIdSZP
         * requestType : FRIEND_APPLY
         * auditedBy : 7236
         * type : RECEIVED
         * targetId : 7236
         * thermalSignatrue : null
         * rembers : 0
         */

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
        private Object thermalSignatrue;
        private int rembers;

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

        public Object getThermalSignatrue() {
            return thermalSignatrue;
        }

        public void setThermalSignatrue(Object thermalSignatrue) {
            this.thermalSignatrue = thermalSignatrue;
        }

        public int getRembers() {
            return rembers;
        }

        public void setRembers(int rembers) {
            this.rembers = rembers;
        }
    }
}

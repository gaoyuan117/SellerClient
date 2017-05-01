package com.kaichaohulian.baocms.entity;

/**
 * Created by xzwzz on 2017/5/1.
 */

public class OnlineServiceEntity {

        public int user_id;
        public Object account;
        public String password;
        public String avator;
        public String username;
        public String balance;
        public int createdTime;
        public Object lastTime;
        public String phoneNumber;
        public Object email;
        public int bshop;
        public int audit;
        public Object regip;
        public int invite6;
        public int customerServiceStatus;

        @Override
        public String toString() {
                return "OnlineServiceEntity{" +
                        "user_id=" + user_id +
                        ", account=" + account +
                        ", password='" + password + '\'' +
                        ", avator='" + avator + '\'' +
                        ", username='" + username + '\'' +
                        ", balance='" + balance + '\'' +
                        ", createdTime=" + createdTime +
                        ", lastTime=" + lastTime +
                        ", phoneNumber='" + phoneNumber + '\'' +
                        ", email=" + email +
                        ", bshop=" + bshop +
                        ", audit=" + audit +
                        ", regip=" + regip +
                        ", invite6=" + invite6 +
                        ", customerServiceStatus=" + customerServiceStatus +
                        '}';
        }
}

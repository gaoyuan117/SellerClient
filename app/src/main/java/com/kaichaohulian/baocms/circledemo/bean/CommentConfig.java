package com.kaichaohulian.baocms.circledemo.bean;

/**
 * Created by yiwei on 16/3/2.
 */
public class CommentConfig {
    public enum Type{
        PUBLIC("public"), REPLY("reply");

        private String value;
        Type(String value){
            this.value = value;
        }

    }

    public String exprienceId;
    public String evaluateId;
    public int circlePosition;
    public int commentPosition;
    public Type commentType;
    public User replyUser;

    @Override
    public String toString() {
        String replyUserStr = "";
        if(replyUser != null){
            replyUserStr = replyUser.toString();
        }
        return "circlePosition = " + circlePosition
                + "; commentPosition = " + commentPosition
                + "; commentType ＝ " + commentType
                + "; replyUser = " + replyUserStr;
    }
}

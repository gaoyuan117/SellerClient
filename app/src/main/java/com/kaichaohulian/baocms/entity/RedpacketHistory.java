package com.kaichaohulian.baocms.entity;

public class RedpacketHistory {
    public String name;
    public String avatar;
    public String time;
    public String useracount;
    public boolean best;

    @Override
    public String toString() {
        return "RedpacketHistory{" +
                "name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", time='" + time + '\'' +
                ", useracount='" + useracount + '\'' +
                ", best=" + best +
                '}';
    }
}

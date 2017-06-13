package com.kaichaohulian.baocms.entity;
import java.io.Serializable;
import java.util.List;

public class GroupDetail  implements Serializable {
    public String code;
    public String errorDescription;
    public DataObject dataObject;

    public class DataObject  implements Serializable{
        public String messageNo;
        public String qrcode;
        public String displayName;
        public String saveMail;
        public int memberCount;
        public int capacity;
        public String topmessage;
        public String chatGroupId;
        public String name;
        public String createdTime;
        public int id;
        public int owner;
        public String introduction;
        public String status;
        public List<Members> members;

        public class Members implements Serializable {
            public String thermalSignatrue;
            public String gradeName;
            public String images;
            public String nameInGroup;
            public String role;
            public String districtName;
            public int isfriend;
            public int id;
            public String avatar;
            public boolean enabled;
            public String username;
            public String imNumber;
            public UserInfo UserInfo;
        }
    }
}

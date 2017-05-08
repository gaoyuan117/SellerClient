package com.kaichaohulian.baocms.entity;

/**
 * Created by gaoyuan on 2017/5/5.
 */

public class AdversDetailBean {

        /**
         * advert : {"id":23,"createdTime":"2017-05-07 18:18:55","creator":null,"isLocked":false,"lastModifiedTime":"2017-05-07 18:18:55","lastModifier":null,"timeStamp":"1494152335218","userId":7237,"title":"健健康康","context":"快快乐乐","type":1,"receive":"7236,","receiveGroup":null,"image":"FvG4z7NJk5TMaJwq8ETeecVtmUJf,","redMoney":1,"hasGetMoney":0,"pay":2,"readNum":1,"userName":null,"avter":null}
         * redMoney : 1.00
         */

        private AdvertBean advert;
        private String redMoney;

        public AdvertBean getAdvert() {
            return advert;
        }

        public void setAdvert(AdvertBean advert) {
            this.advert = advert;
        }

        public String getRedMoney() {
            return redMoney;
        }

        public void setRedMoney(String redMoney) {
            this.redMoney = redMoney;
        }

        public static class AdvertBean {
            /**
             * id : 23
             * createdTime : 2017-05-07 18:18:55
             * creator : null
             * isLocked : false
             * lastModifiedTime : 2017-05-07 18:18:55
             * lastModifier : null
             * timeStamp : 1494152335218
             * userId : 7237
             * title : 健健康康
             * context : 快快乐乐
             * type : 1
             * receive : 7236,
             * receiveGroup : null
             * image : FvG4z7NJk5TMaJwq8ETeecVtmUJf,
             * redMoney : 1.0
             * hasGetMoney : 0.0
             * pay : 2.0
             * readNum : 1
             * userName : null
             * avter : null
             */

            private int id;
            private String createdTime;
            private Object creator;
            private boolean isLocked;
            private String lastModifiedTime;
            private Object lastModifier;
            private String timeStamp;
            private int userId;
            private String title;
            private String context;
            private int type;
            private String receive;
            private Object receiveGroup;
            private String image;
            private double redMoney;
            private double hasGetMoney;
            private double pay;
            private int readNum;
            private Object userName;
            private Object avter;

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

            public Object getCreator() {
                return creator;
            }

            public void setCreator(Object creator) {
                this.creator = creator;
            }

            public boolean isIsLocked() {
                return isLocked;
            }

            public void setIsLocked(boolean isLocked) {
                this.isLocked = isLocked;
            }

            public String getLastModifiedTime() {
                return lastModifiedTime;
            }

            public void setLastModifiedTime(String lastModifiedTime) {
                this.lastModifiedTime = lastModifiedTime;
            }

            public Object getLastModifier() {
                return lastModifier;
            }

            public void setLastModifier(Object lastModifier) {
                this.lastModifier = lastModifier;
            }

            public String getTimeStamp() {
                return timeStamp;
            }

            public void setTimeStamp(String timeStamp) {
                this.timeStamp = timeStamp;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContext() {
                return context;
            }

            public void setContext(String context) {
                this.context = context;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getReceive() {
                return receive;
            }

            public void setReceive(String receive) {
                this.receive = receive;
            }

            public Object getReceiveGroup() {
                return receiveGroup;
            }

            public void setReceiveGroup(Object receiveGroup) {
                this.receiveGroup = receiveGroup;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public double getRedMoney() {
                return redMoney;
            }

            public void setRedMoney(double redMoney) {
                this.redMoney = redMoney;
            }

            public double getHasGetMoney() {
                return hasGetMoney;
            }

            public void setHasGetMoney(double hasGetMoney) {
                this.hasGetMoney = hasGetMoney;
            }

            public double getPay() {
                return pay;
            }

            public void setPay(double pay) {
                this.pay = pay;
            }

            public int getReadNum() {
                return readNum;
            }

            public void setReadNum(int readNum) {
                this.readNum = readNum;
            }

            public Object getUserName() {
                return userName;
            }

            public void setUserName(Object userName) {
                this.userName = userName;
            }

            public Object getAvter() {
                return avter;
            }

            public void setAvter(Object avter) {
                this.avter = avter;
            }
    }
}

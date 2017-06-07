package com.kaichaohulian.baocms.entity;

import java.util.List;

/**
 * Created by huqi1 on 2017/2/7.
 */

public class SmallMoneyBean {


    /**
     * code : 0
     * errorDescription : 获取资金明细成功
     * dataObject : [{"name":"转账支出","money":"-3","time":"2017-06-05 15:00:32"},{"name":"转账收入","money":"+2","time":"2017-06-05 14:58:28"},{"name":"转账收入","money":"+1","time":"2017-06-05 14:54:05"},{"name":"转账收入","money":"+1","time":"2017-06-05 14:08:46"},{"name":"转账收入","money":"+1","time":"2017-06-05 00:42:43"},{"name":"转账收入","money":"+2","time":"2017-06-05 00:40:49"},{"name":"转账收入","money":"+1","time":"2017-06-05 00:18:38"},{"name":"转账支出","money":"-1","time":"2017-06-05 00:16:43"},{"name":"转账支出","money":"-1","time":"2017-06-04 23:08:13"},{"name":"转账支出","money":"-1","time":"2017-06-04 22:58:46"},{"name":"充值","money":"+0.01","time":"2017-06-04 17:39:30"},{"name":"充值","money":"+0.01","time":"2017-06-04 17:38:45"},{"name":"发布广告","money":"-0.06","time":"2017-06-04 17:11:32"},{"name":"发布广告","money":"-0.06","time":"2017-06-04 16:50:02"},{"name":"充值","money":"+0.01","time":"2017-06-04 16:40:27"}]
     */

    private String code;
    private String errorDescription;
    private List<DataObjectBean> dataObject;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public List<DataObjectBean> getDataObject() {
        return dataObject;
    }

    public void setDataObject(List<DataObjectBean> dataObject) {
        this.dataObject = dataObject;
    }

    public static class DataObjectBean {
        /**
         * name : 转账支出
         * money : -3
         * time : 2017-06-05 15:00:32
         */

        private String name;
        private String money;
        private String time;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}

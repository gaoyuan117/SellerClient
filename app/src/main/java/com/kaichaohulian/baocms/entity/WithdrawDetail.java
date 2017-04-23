package com.kaichaohulian.baocms.entity;

import java.util.List;

/**
 * Created by huqi1 on 2017/1/22.
 */

public class WithdrawDetail {
    private int totalMoney;
    private List<WithdrawDetailEntity> userCashs;

    public int getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(int totalMoney) {
        this.totalMoney = totalMoney;
    }

    public List<WithdrawDetailEntity> getUserCashs() {
        return userCashs;
    }

    public void setUserCashs(List<WithdrawDetailEntity> userCashs) {
        this.userCashs = userCashs;
    }
}

package com.kaichaohulian.baocms.entity;

/**
 * Created by Jimq on 2017/3/11.
 */
public class StatusModel {

    /**
     * uId : 0
     * addfriendflag : 0
     * pushphonebook : 1
     * allowtenphoto : null
     * phonefind : null
     * weixinfind : null
     * groupchatadd : 1
     * qqfind : null
     * erweiadd : 1
     * cardadd : 0
     */

    private int uId;
    private String addfriendflag;
    private String pushphonebook;
    private Object allowtenphoto;
    private Object phonefind;
    private Object weixinfind;
    private String groupchatadd;
    private Object qqfind;
    private String erweiadd;
    private String cardadd;

    public int getUId() {
        return uId;
    }

    public void setUId(int uId) {
        this.uId = uId;
    }

    public String getAddfriendflag() {
        return addfriendflag;
    }

    public void setAddfriendflag(String addfriendflag) {
        this.addfriendflag = addfriendflag;
    }

    public String getPushphonebook() {
        return pushphonebook;
    }

    public void setPushphonebook(String pushphonebook) {
        this.pushphonebook = pushphonebook;
    }

    public Object getAllowtenphoto() {
        return allowtenphoto;
    }

    public void setAllowtenphoto(Object allowtenphoto) {
        this.allowtenphoto = allowtenphoto;
    }

    public Object getPhonefind() {
        return phonefind;
    }

    public void setPhonefind(Object phonefind) {
        this.phonefind = phonefind;
    }

    public Object getWeixinfind() {
        return weixinfind;
    }

    public void setWeixinfind(Object weixinfind) {
        this.weixinfind = weixinfind;
    }

    public String getGroupchatadd() {
        return groupchatadd;
    }

    public void setGroupchatadd(String groupchatadd) {
        this.groupchatadd = groupchatadd;
    }

    public Object getQqfind() {
        return qqfind;
    }

    public void setQqfind(Object qqfind) {
        this.qqfind = qqfind;
    }

    public String getErweiadd() {
        return erweiadd;
    }

    public void setErweiadd(String erweiadd) {
        this.erweiadd = erweiadd;
    }

    public String getCardadd() {
        return cardadd;
    }

    public void setCardadd(String cardadd) {
        this.cardadd = cardadd;
    }
}

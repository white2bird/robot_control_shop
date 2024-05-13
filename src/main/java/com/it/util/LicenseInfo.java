package com.it.util;

import java.util.Date;

/**
 * @author 
 * @date 2024/4/8 11:02
 */
public class LicenseInfo {

    private String linceseCde;


    private String mac;
    /**
     * 创建时期
     */
    private Date createDate;

    /**
     * 有效期 毫秒为单位 -1 为永久
     */
    private Long validTime;

    public String getLinceseCde() {
        return linceseCde;
    }

    public void setLinceseCde(String linceseCde) {
        this.linceseCde = linceseCde;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getValidTime() {
        return validTime;
    }

    public void setValidTime(Long validTime) {
        this.validTime = validTime;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "LicenseInfo{" +
                "linceseCde='" + linceseCde + '\'' +
                ", mac='" + mac + '\'' +
                ", createDate=" + createDate +
                ", validTime=" + validTime +
                '}';
    }
}

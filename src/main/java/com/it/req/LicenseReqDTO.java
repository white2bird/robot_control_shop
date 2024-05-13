package com.it.req;



import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author 
 * @date 2024/4/8 15:04
 */
public class LicenseReqDTO {


    @NotNull(message = "授权开始时间必须设置")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    /**
     * mac地址
     */
    @NotEmpty(message = "mac地址不能传空")
    private String mac;

    /**
     * 过期天数
     */
    @NotNull(message = "过期天数必须设置")
    private Integer expireDay;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Integer getExpireDay() {
        return expireDay;
    }

    public void setExpireDay(Integer expireDay) {
        this.expireDay = expireDay;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "LicenseReqDTO{" +
                "startDate=" + startDate +
                ", mac='" + mac + '\'' +
                ", expireDay=" + expireDay +
                '}';
    }
}

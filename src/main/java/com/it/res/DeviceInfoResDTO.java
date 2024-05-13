package com.it.res;

import com.it.service.DeviceInfoService;

/**
 * @author 
 * @date 2024/4/5 23:56
 */
public class DeviceInfoResDTO {

    private Double devicePower;

    private Integer deviceStatus;

    public DeviceInfoResDTO(){

    }

    public Double getDevicePower() {
        return devicePower;
    }

    public void setDevicePower(Double devicePower) {
        this.devicePower = devicePower;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }
}

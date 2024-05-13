package com.it.constants;

import com.it.util.MacUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 
 * @date 2024/4/8 17:58
 */
@Component
public class MacAddress {

    private String macAddress;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @PostConstruct
    public void init(){
        String mac = MacUtil.getMac();
        this.macAddress = mac;

        System.out.println("-----set ---mac --- successs-----");
    }
}

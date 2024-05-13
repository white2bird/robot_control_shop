package com.it.constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 
 * @date 2024/4/8 15:33
 */
@Configuration
@ConfigurationProperties(prefix = "rsa")
public class PublicPrivate {

    private String privateStr;

    private String publicStr;


    public String getPrivateStr() {
        return privateStr;
    }

    public void setPrivateStr(String privateStr) {
        this.privateStr = privateStr;
    }

    public String getPublicStr() {
        return publicStr;
    }

    public void setPublicStr(String publicStr) {
        this.publicStr = publicStr;
    }
}

package com.it.constants;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;

/**
 * @author 
 * @date 2024/4/8 18:15
 */
@Configuration
public class SignInfo {

    private String sign;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @PostConstruct
    public void init() throws Exception{
        File file = ResourceUtils.getFile("license.txt");
        if(!file.exists()){
            this.sign = "";
            return;
        }
        FileReader fileReader = new FileReader(file);
        String content = IOUtils.toString(fileReader);
        if(StringUtils.hasText(content)){
            this.sign = content.trim();
        }
    }

}

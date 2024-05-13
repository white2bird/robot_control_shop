package com.it.config;

import org.apache.tika.Tika;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yiming@micous.com
 * @date 2024/4/20 14:05
 */
@Configuration
public class UtilBeanConfiguration {

    @Bean
    public Tika tika(){
        return new Tika();
    }
}

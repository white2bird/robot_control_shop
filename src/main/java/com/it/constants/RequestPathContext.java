package com.it.constants;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yiming@micous.com
 * @date 2024/4/20 18:00
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "static")
public class RequestPathContext {

    private String path;
}

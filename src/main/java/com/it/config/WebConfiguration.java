package com.it.config;

import com.it.constants.MacAddress;
import com.it.constants.PublicPrivate;
import com.it.constants.SignInfo;
import com.it.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author 
 * @date 2024/4/5 10:36
 */
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Resource
    private PublicPrivate publicPrivate;

    @Resource
    private MacAddress macAddress;

    @Resource
    private SignInfo signInfo;

    @Resource
    private LoginInterceptor loginInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/","classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:/Users/mico/uploadFile/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LicenseInterceptor(publicPrivate, macAddress, signInfo)).addPathPatterns("/**")
//                .excludePathPatterns("/swagger-ui.html", "/webjars/**",  "/doc.html", "/swagger-resources/**", "/v2/**")
//                .excludePathPatterns("/login");

        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/swagger-ui.html", "/webjars/**",  "/doc.html", "/swagger-resources/**", "/v2/**", "/static/**", "/error/**")
                .excludePathPatterns("/user/login");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有接口
                .allowCredentials(true) // 是否发送 Cookie
                .allowedOriginPatterns("*") // 支持域
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 支持方法
                .allowedHeaders("*")
                .exposedHeaders("*");

    }
}

package com.it.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @date 2024/4/21 11:47
 */
@Component
public class MyBatisDefaultValueObjectHandler implements MetaObjectHandler {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createDate", new Date(), metaObject);
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("orderNo", genOrderNo(), metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }

    private String genOrderNo(){
        //获取当前时间
        LocalDateTime now = LocalDateTime.now();
        String YYMMDD = StringUtils.substring(DateUtil.format(now, DatePattern.PURE_DATE_PATTERN),2);
        String seconds = now.getHour()*3600+now.getMinute()*60+now.getSecond()+"";
        String secondsFMT = StringUtils.leftPad(seconds, 5, "0");

        String orderNoPrefix = YYMMDD+secondsFMT;
        String redisKeyPrefix = "order_no:"+orderNoPrefix;
        Long increment = redisTemplate.boundValueOps(redisKeyPrefix).increment();
        if(increment == 1){
            //每一秒的第一个订单号，设置一下key过期时间
            redisTemplate.boundValueOps(redisKeyPrefix).expire(60, TimeUnit.SECONDS);
        }
        return orderNoPrefix+StringUtils.leftPad(String.valueOf(increment),4,"0");
    }
}

package com.it.res;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * 
 * @date 2024/4/21 21:00
 */
@Data
public class OrderShowResVO {
    private Long id;

    private String orderNo;

    private String goodName;

    private String username;

    private Long goodCount;

    private String goodPic;

    private BigDecimal totalPrice;

    private BigDecimal singlePrice;

    private Date createTime;


}

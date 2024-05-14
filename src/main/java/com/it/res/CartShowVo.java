package com.it.res;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 
 * @date 2024/4/22 00:49
 */
@Data
public class CartShowVo {

    private Long id;

    private Long goodId;

    private String name;

    private BigDecimal price;

    private Long quantity;

    private String goodPic;

}

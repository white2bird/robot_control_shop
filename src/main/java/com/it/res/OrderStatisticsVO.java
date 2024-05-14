package com.it.res;

import lombok.Data;


/**
 * 
 * @date 2024/4/22 10:03
 */
@Data
public class OrderStatisticsVO {

    private String day;

    private Long orderCount;

    private Long goodCounts;
}

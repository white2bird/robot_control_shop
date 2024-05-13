package com.it.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 15:59
 */
@Data
@TableName("good")
public class Good {

    @TableId(type = IdType.AUTO)
    private Long id;

    private BigDecimal price;

    private String goodName;

    private Long goodCount;

    private String goodPic;

    private String detail;
}

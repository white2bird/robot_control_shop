package com.it.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 15:59
 */
@Data
@TableName("orders")
public class Order {


    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(fill = FieldFill.INSERT)
    private String orderNo;

    private Long goodId;

    private Long userId;

    private Long goodCount;

    private BigDecimal singlePrice;

    private BigDecimal totalPrice;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}

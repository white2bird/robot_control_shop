package com.it.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @date 2024/5/14 11:40
 */
@Data
@TableName("food_menu")
public class FoodMenu {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String name;

    private String menuDesc;

    private String pic;

    private String steps;
}

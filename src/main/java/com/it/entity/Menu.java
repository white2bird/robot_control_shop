package com.it.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @date 2024/4/18 17:12
 */
@Data
@TableName("menu")
public class Menu {

    private Long id;

    private String name;

    private String menuName;

    private String path;

    private String icon;
}

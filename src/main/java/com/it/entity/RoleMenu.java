package com.it.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @date 2024/4/21 13:24
 */
@Data
@TableName("role_menu")
public class RoleMenu {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long roleId;

    private Long menuId;
}

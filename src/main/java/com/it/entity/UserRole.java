package com.it.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Date;

/**
 * 
 * @date 2024/4/21 14:39
 */
@TableName("sys_role_user")
@Data
public class UserRole {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long roleId;

    private Long userId;

    @TableField(fill = FieldFill.INSERT)
    private Date createDate;
}

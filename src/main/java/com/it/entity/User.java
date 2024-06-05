package com.it.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

/**
 * @author 
 * @date 2024/4/13 22:30
 */
@Data
@TableName("sys_user")
public class User {

    @TableId(type = IdType.AUTO)
    @JsonSerialize(using= ToStringSerializer.class)
    private Long id;

    private String username;

    private String password;

    private String realName;

    private Integer gender;

    private Integer superAdmin;

    private String mobile;

    private String email;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

}

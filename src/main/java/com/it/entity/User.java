package com.it.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

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

}

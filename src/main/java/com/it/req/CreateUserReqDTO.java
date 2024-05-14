package com.it.req;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 
 * @date 2024/4/15 00:10
 */
@Data
public class CreateUserReqDTO {

    private Long id;

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "账号密码不能为空")
    private String password;

    private String realName;

    private Integer gender;

    private Integer sex;

    private Integer superAdmin;

    private String mobile;

    private String email;
}

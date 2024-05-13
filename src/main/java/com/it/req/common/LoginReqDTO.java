package com.it.req.common;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author 
 * @date 2024/4/13 22:18
 */
@Data
public class LoginReqDTO {

    @NotEmpty(message = "用户名不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;


}

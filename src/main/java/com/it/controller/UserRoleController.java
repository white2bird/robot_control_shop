package com.it.controller;

import com.it.common.Result;
import com.it.req.RoleMenuUpdateReqDTO;
import com.it.req.UserRoleUpdateReqDTO;
import com.it.service.UserRoleService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 14:42
 */
@RestController
@RequestMapping("/userRole")
public class UserRoleController {

    @Resource
    private UserRoleService userRoleService;

    @PostMapping("/update")
    public Result updateRoleMenu(@RequestBody UserRoleUpdateReqDTO userRoleUpdateReqDTO){
        Boolean res = userRoleService.updateUserRole(userRoleUpdateReqDTO);
        return Result.ok(res, 200, "success");
    }
}

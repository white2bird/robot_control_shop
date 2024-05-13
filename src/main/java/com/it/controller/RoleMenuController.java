package com.it.controller;

import com.it.common.Result;
import com.it.req.RoleMenuUpdateReqDTO;
import com.it.service.RoleMenuService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 13:27
 */
@RestController
@RequestMapping("/roleMenu")
public class RoleMenuController {

    @Resource
    private RoleMenuService roleMenuService;

    @PostMapping("/update")
    public Result updateRoleMenu(@RequestBody RoleMenuUpdateReqDTO roleMenuUpdateReqDTO){
        Boolean res = roleMenuService.updateRoleMenu(roleMenuUpdateReqDTO);
        return Result.ok(res, 200, "success");
    }
}

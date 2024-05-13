package com.it.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.common.Page;
import com.it.common.Result;
import com.it.entity.Role;
import com.it.entity.RoleMenu;
import com.it.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 11:28
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @PostMapping("/getPageList")
    public Result getPageList(@RequestBody Page page){
        IPage allRole = roleService.getAllRole(page);
        return Result.ok(allRole.getRecords(), 200, "success", allRole.getTotal());
    }

    @PostMapping("/createRole")
    public Result createRole(@RequestBody Role role){
        role.setCreateDate(null);
        Boolean res = roleService.save(role);
        return Result.ok(res, 200, "success");
    }

    @GetMapping("/getRolesByUserId")
    public Result getRolesByUserId(@RequestParam("userId") Long userId){
        List<Role> rolesByUserId = roleService.getRolesByUserId(userId);
        return Result.ok(rolesByUserId, 200, "success");
    }
}

package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.it.common.Result;
import com.it.entity.Menu;
import com.it.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * @date 2024/4/18 18:43
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    @GetMapping("/menuList")
    public Result getMenuList(){
        List<Menu> userMenuListByPermission = menuService.getUserMenuListByPermission();
        return Result.ok(userMenuListByPermission, 200, "successs");
    }

    @GetMapping("/getAllMenuList")
    public Result getAllMenuList(){
        List<Menu> userMenuListByPermission = menuService.list();
        return Result.ok(userMenuListByPermission, 200, "successs");
    }

    @GetMapping("/menuListByRoleId")
    public Result menuListByRoleId(@RequestParam("roleId") Long roleId){
        List<Menu> userMenuListByPermission = menuService.getUserMenuListByRoleId(roleId);
        return Result.ok(userMenuListByPermission, 200, "successs");
    }
}

package com.it.controller;

import com.it.common.Result;
import com.it.res.MenuMainVO;
import com.it.service.FoodMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * @date 2024/5/14 18:37
 */
@RestController
@RequestMapping("/foodMenu")
public class FoodMenuController {

    @Resource
    private FoodMenuService foodMenuService;

    @GetMapping("/search")
    public Result search(@RequestParam(value = "menuType", required = false) Integer menuType){
        List<MenuMainVO> search = foodMenuService.search(menuType);
        return Result.ok(search);
    }
}

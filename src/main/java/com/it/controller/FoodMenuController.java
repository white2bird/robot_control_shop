package com.it.controller;

import com.it.common.Result;
import com.it.req.LikeRelationReq;
import com.it.res.FoodMenuDetailVO;
import com.it.res.FoodMenuVO;
import com.it.res.MenuMainVO;
import com.it.service.FoodMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/searchByName")
    public Result searchByName(@RequestParam(value = "name", required = false) String name){
        List<MenuMainVO> search = foodMenuService.searchByName(name);
        return Result.ok(search);
    }

    @GetMapping("/detail/{id}")
    public Result detial(@PathVariable("id") Long id){
        FoodMenuDetailVO detail = foodMenuService.detail(id);
        return Result.ok(detail);
    }

    @PostMapping("/relationChange")
    public Result relationChange(@RequestBody LikeRelationReq likeRelationReq){
        Boolean res = foodMenuService.likeRelation(likeRelationReq);
        return Result.ok(res);
    }

    @GetMapping("/getStoredMenus")
    public Result getStoredMenus(){
        List<MenuMainVO> storeMenus = foodMenuService.getStoreMenus();
        return Result.ok(storeMenus);
    }

    @GetMapping("/getStoredMenusById")
    public Result getStoredMenusById(@RequestParam("userId") Long userId){
        MenuMainVO storeMenus = foodMenuService.getStoreMenus(userId);
        return Result.ok(storeMenus);
    }



    @GetMapping("/recommend/{foodType}")
    public Result recommend(@PathVariable("foodType") Long foodType){
        FoodMenuDetailVO recommend = foodMenuService.recommend(foodType);
        return Result.ok(recommend);
    }

    @GetMapping("/getBanner")
    public Result getBanner(){
        List<FoodMenuVO> banner = foodMenuService.getBanner();
        return Result.ok(banner);
    }
}

package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.common.Result;
import com.it.entity.Good;
import com.it.req.GoodSearchReqDTO;
import com.it.service.GoodService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 18:34
 */
@RestController
@RequestMapping("/good")
public class GoodController {

    @Resource
    private GoodService goodService;

    @GetMapping("/getDetailById")
    public Result getDetailById(@RequestParam("id") Long id){
        Good byId = goodService.getById(id);
        return Result.ok(byId);
    }

    @PostMapping("/getList")
    public Result getList(@RequestBody GoodSearchReqDTO goodSearchReqDTO){
        IPage page = goodService.searchGood(goodSearchReqDTO);
        return Result.ok(page.getRecords(), 200, "success", page.getTotal());
    }

    @PostMapping("/createGood")
    public Result createGood(@RequestBody Good good){
        boolean save = goodService.save(good);
        return Result.ok(save);
    }

    @PostMapping("/updateGood")
    public Result updateGood(@RequestBody Good good){
        boolean update = goodService.update(good, new LambdaQueryWrapper<Good>().eq(Good::getId, good.getId()));
        return Result.ok(update);
    }
}

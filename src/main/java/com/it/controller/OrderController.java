package com.it.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.common.Result;
import com.it.entity.Good;
import com.it.entity.Order;
import com.it.req.GoodSearchReqDTO;
import com.it.req.OderSearchReqDTO;
import com.it.res.CartShowVo;
import com.it.res.OrderStatisticsVO;
import com.it.service.GoodService;
import com.it.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 18:33
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/getList")
    public Result getList(@RequestBody OderSearchReqDTO oderSearchReqDTO){
        IPage page = orderService.searchWithUser(oderSearchReqDTO, false);
        return Result.ok(page.getRecords(), 200, "success", page.getTotal());
    }

    @GetMapping("/getListWithUser")
    public Result getListWithUser(){
        IPage page = orderService.searchWithUser(new OderSearchReqDTO(), true);
        return Result.ok(page.getRecords(), 200, "success", page.getTotal());
    }

    @PostMapping("/createOrder")
    public Result createGood(@RequestBody Order order){
        boolean save = orderService.save(order);
        return Result.ok(save);
    }

    @PostMapping("/createOrders")
    public Result createOrders(@RequestBody List<CartShowVo> orders){

        Boolean res = orderService.createOrders(orders);
        return Result.ok(res);
    }

    @GetMapping("/orderStatistics")
    public Result orderStatistics(){
        List<OrderStatisticsVO> orderStatisticsVOS = orderService.orderStatistics();
        return Result.ok(orderStatisticsVOS);
    }

//    @PostMapping("/updateGood")
//    public Result updateGood(@RequestBody Order good){
//        boolean update = goodService.update(good, new LambdaQueryWrapper<Good>().eq(Good::getId, good.getId()));
//        return Result.ok(update);
//    }
}

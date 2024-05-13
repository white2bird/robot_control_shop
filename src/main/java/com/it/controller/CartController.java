package com.it.controller;

import com.it.common.Result;
import com.it.entity.Cart;
import com.it.res.CartShowVo;
import com.it.service.CartService;
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
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartService;

    @PostMapping("/addCart")
    public Result addCart(@RequestBody Cart cart){
        Boolean res = cartService.addCart(cart);
        return Result.ok(res);
    }

    @GetMapping("/getCartList")
    public Result getCartList(){
        List<CartShowVo> cartShowVoList = cartService.getCartList();
        return Result.ok(cartShowVoList);
    }
}

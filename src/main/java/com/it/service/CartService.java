package com.it.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.entity.Cart;
import com.it.res.CartShowVo;

import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 18:27
 */
public interface CartService extends IService<Cart> {
    Boolean addCart(Cart cart);

    List<CartShowVo> getCartList();
}

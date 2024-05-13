package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.context.UserContext;
import com.it.convert.CartShowConvert;
import com.it.entity.Cart;
import com.it.entity.Good;
import com.it.mapper.CartMapper;
import com.it.res.CartShowVo;
import com.it.service.CartService;
import com.it.service.GoodService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 18:28
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {

    @Resource
    private GoodService goodService;

    @Resource
    private CartShowConvert cartShowConvert;

    @Override
    public Boolean addCart(Cart cart) {
        Long userId = UserContext.getUserId();
        cart.setGoodCount(1L);
        cart.setUserId(userId);
        LambdaQueryWrapper<Cart> query = new LambdaQueryWrapper<>();
        query.eq(Cart::getUserId, userId);
        query.eq(Cart::getGoodId, cart.getGoodId());
        Cart one = this.getOne(query);
        if(Objects.nonNull(one)){
            one.setGoodCount(one.getGoodCount() + 1);
            return updateById(one);
        }
        return this.save(cart);
    }


    @Override
    public List<CartShowVo> getCartList() {
        Long userId = UserContext.getUserId();
        LambdaQueryWrapper<Cart> query = new LambdaQueryWrapper<>();
        query.eq(Cart::getUserId, userId);
        List<Cart> list = this.list(query);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        List<Long> goods = list.stream().map(Cart::getGoodId).distinct().collect(Collectors.toList());
        Map<Long, Good> goodInfoMap = goodService.getGoodInfoMap(goods);
        List<CartShowVo> res = new ArrayList<>();
        for(Cart cart : list){
            CartShowVo from = cartShowConvert.from(goodInfoMap.get(cart.getGoodId()), cart);
            res.add(from);
        }
        return res;

    }
}

package com.it.convert;

import com.it.entity.Cart;
import com.it.entity.Good;
import com.it.res.CartShowVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 
 * @date 2024/4/22 00:55
 */
@Mapper(componentModel = "spring")
public interface CartShowConvert {

    @Mapping(source = "cart.id", target = "id")
    @Mapping(source = "good.id", target = "goodId")
    @Mapping(source = "cart.goodCount", target = "quantity")
    @Mapping(source = "good.price", target = "price")
    @Mapping(source = "good.goodName", target = "name")
    @Mapping(source = "good.goodPic", target = "goodPic")
    CartShowVo from(Good good, Cart cart);
}

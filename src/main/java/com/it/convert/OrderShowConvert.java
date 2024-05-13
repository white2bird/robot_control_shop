package com.it.convert;

import com.it.entity.Order;
import com.it.res.OrderShowResVO;
import org.mapstruct.Mapper;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 21:03
 */
@Mapper(componentModel = "spring")
public interface OrderShowConvert {

    OrderShowResVO toShow(Order order);
}

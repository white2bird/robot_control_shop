package com.it.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.entity.Order;
import com.it.req.OderSearchReqDTO;
import com.it.res.CartShowVo;
import com.it.res.OrderStatisticsVO;

import java.util.List;

/**
 * 
 * @date 2024/4/21 18:27
 */
public interface OrderService extends IService<Order> {


    IPage searchWithUser(OderSearchReqDTO oderSearchReqDTO, Boolean withUser);

    Boolean createOrders(List<CartShowVo> orders);

    List<OrderStatisticsVO> orderStatistics();
}

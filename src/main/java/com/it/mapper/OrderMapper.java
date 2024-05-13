package com.it.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.Order;
import com.it.res.OrderStatisticsVO;

import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 18:25
 */
public interface OrderMapper extends BaseMapper<Order> {

    List<OrderStatisticsVO> orderStatics();
}

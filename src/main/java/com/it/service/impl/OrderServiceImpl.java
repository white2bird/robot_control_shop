package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.context.UserContext;
import com.it.convert.OrderShowConvert;
import com.it.entity.Good;
import com.it.entity.Order;
import com.it.mapper.GoodMapper;
import com.it.mapper.OrderMapper;
import com.it.req.OderSearchReqDTO;
import com.it.res.CartShowVo;
import com.it.res.OrderShowResVO;
import com.it.res.OrderStatisticsVO;
import com.it.service.AdminUserService;
import com.it.service.CartService;
import com.it.service.GoodService;
import com.it.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 * @date 2024/4/21 18:30
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderShowConvert orderShowConvert;

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private GoodMapper goodMapper;

    @Resource
    private GoodService goodService;

    @Resource
    private CartService cartService;

    @Override
    public IPage searchWithUser(OderSearchReqDTO oderSearchReqDTO, Boolean withUser) {
        IPage<Order> page = new Page(oderSearchReqDTO.getPageNo(), oderSearchReqDTO.getPageSize());
        LambdaQueryWrapper<Order> query = new LambdaQueryWrapper<Order>();
        if(!StringUtils.isEmpty(oderSearchReqDTO.getOrderNo())){
            query.like(Order::getOrderNo, oderSearchReqDTO.getOrderNo());
        }
        if(withUser){
            query.eq(Order::getUserId, UserContext.getUserId());
        }

        query.orderByAsc(Order::getId);
        IPage<Order> res = this.page(page, query);
        List<Order> records = res.getRecords();
        if(CollectionUtils.isEmpty(records)){
            return new Page();
        }
        List<Long> userList = records.stream().map(Order::getUserId).distinct().collect(Collectors.toList());
        List<Long> goodList = records.stream().map(Order::getGoodId).distinct().collect(Collectors.toList());
        Map<Long, String> usernameMap = adminUserService.getUsernameMap(userList);
        Map<Long, Good> goodNameMap = goodService.getGoodInfoMap(goodList);


        List<Object> showList = new ArrayList<>();
        for(Order order : records){
            OrderShowResVO orderShowResVO = orderShowConvert.toShow(order);
            orderShowResVO.setGoodName(goodNameMap.get(order.getGoodId()).getGoodName());
            orderShowResVO.setGoodPic(goodNameMap.get(order.getGoodId()).getGoodPic());
            orderShowResVO.setUsername(usernameMap.get(order.getUserId()));
            orderShowResVO.setSinglePrice(goodNameMap.get(order.getGoodId()).getPrice());
            showList.add(orderShowResVO);
        }

        Page<Object> objectPage = new Page<>();
        objectPage.setRecords(showList);
        objectPage.setTotal(res.getTotal());
        return objectPage;
    }

    @Override
    @Transactional
    public Boolean createOrders(List<CartShowVo> orders) {
        List<Order> orderList = new ArrayList<>();
//        Map<Long, Long> goodToDecrease = new HashMap<>();
        for (CartShowVo showVo : orders){
            Order order = new Order();
            order.setGoodCount(showVo.getQuantity());
            order.setSinglePrice(showVo.getPrice());
            order.setTotalPrice(showVo.getPrice().multiply(new BigDecimal(showVo.getQuantity())));
            order.setGoodId(showVo.getGoodId());
            order.setUserId(UserContext.getUserId());
            orderList.add(order);

        }
        Map<Long, Long> goodToDecrease = orderList.stream().collect(Collectors.groupingBy(Order::getGoodId, Collectors.summingLong(Order::getGoodCount)));
        saveBatch(orderList);
        List<Long> cartIds = orders.stream().map(CartShowVo::getId).collect(Collectors.toList());
        for (Map.Entry<Long, Long> entry : goodToDecrease.entrySet()) {
            Long goodId = entry.getKey();
            Long value = entry.getValue();
            Boolean res = goodMapper.subGoodCount(goodId, value);
            if(!res){
                throw new RuntimeException("库存不足");
            }

        }

        cartService.removeByIds(cartIds);
        return true;
    }

    @Override
    public List<OrderStatisticsVO> orderStatistics(){
        return baseMapper.orderStatics();
    }
}

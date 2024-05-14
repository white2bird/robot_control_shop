package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.Good;
import com.it.entity.User;
import com.it.mapper.GoodMapper;
import com.it.req.GoodSearchReqDTO;
import com.it.service.GoodService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 
 * @date 2024/4/21 18:28
 */
@Service
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements GoodService {

    @Override
    public IPage searchGood(GoodSearchReqDTO goodSearchReqDTO) {
        IPage page = new Page(goodSearchReqDTO.getPageNo(), goodSearchReqDTO.getPageSize());
        LambdaQueryWrapper<Good> query = new LambdaQueryWrapper<Good>();
        if(!StringUtils.isEmpty(goodSearchReqDTO.getGoodName())){
            query.like(Good::getGoodName, goodSearchReqDTO.getGoodName());
        }
        return this.page(page, query);
    }

    @Override
    public Map<Long, String> getGoodNameMap(List<Long> goodList) {
        LambdaQueryWrapper<Good> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.in(Good::getId, goodList);
        userLambdaQueryWrapper.select(Good::getGoodName, Good::getId, Good::getPrice);
        List<Good> list = this.list(userLambdaQueryWrapper);
        return list.stream().collect(Collectors.toMap(Good::getId, Good::getGoodName));
    }

    @Override
    public Map<Long, Good> getGoodInfoMap(List<Long> goodList) {
        LambdaQueryWrapper<Good> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.in(Good::getId, goodList);
        userLambdaQueryWrapper.select(Good::getGoodName, Good::getId, Good::getGoodPic, Good::getPrice);
        List<Good> list = this.list(userLambdaQueryWrapper);
        return list.stream().collect(Collectors.toMap(Good::getId, a->a));
    }
}

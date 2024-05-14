package com.it.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.FoodMenu;
import com.it.entity.FoodType;
import com.it.mapper.FoodMenuMapper;
import com.it.res.MenuMainVO;
import com.it.service.FoodMenuService;
import com.it.service.FoodTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @date 2024/5/14 14:08
 */
@Service
public class FoodMenuServiceImpl extends ServiceImpl<FoodMenuMapper, FoodMenu> implements FoodMenuService {

    @Resource
    private FoodTypeService foodTypeService;

    @Override
    public List<MenuMainVO> search(Integer type) {
        List<Integer> types = type == null ? foodTypeService.list().stream().map(FoodType::getId).map(Long::intValue).collect(Collectors.toList())
                : Arrays.asList(type);
        
        // TODO 
        return null;
    }
}

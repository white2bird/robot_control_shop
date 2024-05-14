package com.it.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.FoodType;
import com.it.mapper.FoodTypeMapper;
import com.it.service.FoodTypeService;
import org.springframework.stereotype.Service;

/**
 * 
 * @date 2024/5/14 14:09
 */
@Service
public class FoodTypeServiceImpl extends ServiceImpl<FoodTypeMapper, FoodType> implements FoodTypeService {
}

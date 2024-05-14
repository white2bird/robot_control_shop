package com.it.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.FoodMenu;
import com.it.entity.FoodType;
import com.it.entity.MenuTypeRelation;
import com.it.mapper.FoodMenuMapper;
import com.it.mapper.MenuTypeRelationMapper;
import com.it.res.FoodMenuVO;
import com.it.res.MenuMainVO;
import com.it.service.FoodMenuService;
import com.it.service.FoodTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private MenuTypeRelationMapper menuTypeRelationMapper;

    @Override
    public List<MenuMainVO> search(Integer type) {
        List<Integer> types = type == null ? foodTypeService.list().stream().map(FoodType::getId).map(Long::intValue).collect(Collectors.toList())
                : Arrays.asList(type);

        List<MenuMainVO> res = new ArrayList<>();
        for(Integer searchType : types){
            FoodType foodType = foodTypeService.getById(searchType);
            MenuMainVO vo = new MenuMainVO();
            vo.setName(foodType.getName());
            List<MenuTypeRelation> foodTypeId = menuTypeRelationMapper.selectList(new QueryWrapper<MenuTypeRelation>().eq("food_type_id", searchType));
            if(CollUtil.isEmpty(foodTypeId)){
                continue;
            }
            List<FoodMenu> foodMenus = this.listByIds(foodTypeId.stream().map(MenuTypeRelation::getFoodMenuId).collect(Collectors.toList()));
            List<FoodMenuVO> foodMenuVOList = new ArrayList<>();
            for(FoodMenu foodMenu : foodMenus){
                FoodMenuVO foodMenuVO = new FoodMenuVO();
                foodMenuVO.setId(foodMenu.getId());
                foodMenuVO.setName(foodMenu.getName());
                foodMenuVO.setImg("http://127.0.0.1:8080/static/goose-8740266_640.jpg");
                foodMenuVOList.add(foodMenuVO);
            }
            vo.setSubArr(foodMenuVOList);
            res.add(vo);
        }
        return res;
    }
}

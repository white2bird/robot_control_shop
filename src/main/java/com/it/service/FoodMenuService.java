package com.it.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.entity.FoodMenu;
import com.it.res.MenuMainVO;

import java.util.List;

/**
 * 
 * @date 2024/5/14 14:07
 */
public interface FoodMenuService extends IService<FoodMenu> {

    List<MenuMainVO> search(Integer type);
}

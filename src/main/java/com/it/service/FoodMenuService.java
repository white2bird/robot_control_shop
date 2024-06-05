package com.it.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.entity.FoodMenu;
import com.it.req.LikeRelationReq;
import com.it.res.FoodMenuDetailVO;
import com.it.res.FoodMenuVO;
import com.it.res.MenuMainVO;

import java.util.List;

/**
 * 
 * @date 2024/5/14 14:07
 */
public interface FoodMenuService extends IService<FoodMenu> {

    List<MenuMainVO> search(Integer type);

    List<MenuMainVO> searchByName(String name);

    FoodMenuDetailVO detail(Long id);

    Boolean likeRelation(LikeRelationReq likeRelationReq);

    List<MenuMainVO> getStoreMenus();


    MenuMainVO getStoreMenus(Long userId);

    FoodMenuDetailVO recommend(Long foodType);

    List<FoodMenuVO> getBanner();


}

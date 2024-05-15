package com.it.convert;

import com.it.entity.FoodMenu;
import com.it.res.FoodMenuDetailVO;
import com.it.res.FoodMenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author 
 * @date 2024/5/15 11:28
 */
@Mapper(componentModel = "spring")
public interface FoodMenuConvert {

    FoodMenuDetailVO convertVo(FoodMenu foodMenu);

    @Mapping(target = "img", source = "pic")
    FoodMenuVO convertToVo(FoodMenu foodMenu);

    List<FoodMenuVO> convertoVoList(List<FoodMenu> foodMenus);
}

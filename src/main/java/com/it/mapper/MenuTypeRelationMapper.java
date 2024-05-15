package com.it.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.MenuTypeRelation;

public interface MenuTypeRelationMapper extends BaseMapper<MenuTypeRelation> {

    Long randomRecommend(Long type);
}

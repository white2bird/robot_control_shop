package com.it.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.Menu;

import java.util.List;

/**
 * 
 * @date 2024/4/18 17:34
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenuWithPermission(Long id);


    List<Menu> getMenuWithRoleId(Long roleId);
}

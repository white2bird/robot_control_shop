package com.it.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.entity.Menu;

import java.util.List;

/**
 * 
 * @date 2024/4/18 17:11
 */
public interface MenuService extends IService<Menu> {

    List<Menu> getUserMenuListByPermission();

    List<Menu> getUserMenuListByPermission(Long userId);

    List<Menu> getUserMenuListByRoleId(Long roleId);
}

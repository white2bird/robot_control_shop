package com.it.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.context.UserContext;
import com.it.entity.Menu;
import com.it.mapper.MenuMapper;
import com.it.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * @date 2024/4/18 17:33
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<Menu> getUserMenuListByPermission() {
        Long userId = UserContext.getUserId();
        return this.baseMapper.getMenuWithPermission(userId);
    }

    @Override
    public List<Menu> getUserMenuListByPermission(Long userId) {
        return this.baseMapper.getMenuWithPermission(userId);
    }

    @Override
    public List<Menu> getUserMenuListByRoleId(Long roleId) {
        return this.baseMapper.getMenuWithRoleId(roleId);
    }
}

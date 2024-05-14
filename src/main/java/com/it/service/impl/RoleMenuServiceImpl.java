package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.RoleMenu;
import com.it.mapper.RoleMenuMapper;
import com.it.req.RoleMenuUpdateReqDTO;
import com.it.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @date 2024/4/21 13:29
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {


    @Override
    @Transactional
    public Boolean updateRoleMenu(RoleMenuUpdateReqDTO roleMenuUpdateReqDTO) {
        this.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleMenuUpdateReqDTO.getRoleId()));
        // 批量插入新的
        List<Long> menuId = roleMenuUpdateReqDTO.getMenuId();
        List<RoleMenu> roleMenus = new ArrayList<>();
        for(Long id : menuId){
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(id);
            roleMenu.setRoleId(roleMenuUpdateReqDTO.getRoleId());
            roleMenus.add(roleMenu);
        }
        this.saveBatch(roleMenus);
        return true;
    }
}

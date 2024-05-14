package com.it.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.entity.RoleMenu;
import com.it.req.RoleMenuUpdateReqDTO;

/**
 * 
 * @date 2024/4/21 13:29
 */
public interface RoleMenuService extends IService<RoleMenu> {

    Boolean updateRoleMenu(RoleMenuUpdateReqDTO roleMenuUpdateReqDTO);
}

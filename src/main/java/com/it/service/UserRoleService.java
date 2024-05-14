package com.it.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.entity.UserRole;
import com.it.req.UserRoleUpdateReqDTO;

/**
 * 
 * @date 2024/4/21 14:43
 */
public interface UserRoleService extends IService<UserRole> {
    Boolean updateUserRole(UserRoleUpdateReqDTO userRoleUpdateReqDTO);
}

package com.it.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.entity.Role;

import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 11:30
 */
public interface RoleService extends IService<Role> {
    IPage getAllRole(com.it.common.Page page);

    List<Role> getRolesByUserId(Long userId);
}

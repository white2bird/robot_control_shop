package com.it.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.Role;

import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 11:29
 */
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> getRolesByUserId(Long userId);
}

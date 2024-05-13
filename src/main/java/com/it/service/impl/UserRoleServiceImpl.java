package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.UserRole;
import com.it.mapper.UserRoleMapper;
import com.it.req.UserRoleUpdateReqDTO;
import com.it.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 14:44
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {


    @Override
    @Transactional
    public Boolean updateUserRole(UserRoleUpdateReqDTO userRoleUpdateReqDTO) {
        this.remove(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userRoleUpdateReqDTO.getUserId()));
        List<UserRole> userRoles = new ArrayList<>();
        for(Long roleId : userRoleUpdateReqDTO.getRoleIds()){
            UserRole userRole = new UserRole();
            userRole.setUserId(userRoleUpdateReqDTO.getUserId());
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        }
        this.saveBatch(userRoles);
        return true;
    }
}

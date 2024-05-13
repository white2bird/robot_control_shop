package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.Role;
import com.it.mapper.RoleMapper;
import com.it.service.RoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/4/21 11:30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public IPage getAllRole(com.it.common.Page page) {
        IPage pageParam = new Page(page.getPageNo(), page.getPageSize());
        QueryWrapper<Role> objectQueryWrapper = new QueryWrapper<>();
        return this.page(pageParam, objectQueryWrapper);
    }

    @Override
    public List<Role> getRolesByUserId(Long userId){
        return roleMapper.getRolesByUserId(userId);
    }
}

package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.Resources;
import com.it.mapper.ResourcesMapper;
import com.it.req.ResourcesShowReqDTO;
import com.it.service.ResourcesService;
import org.springframework.stereotype.Service;

/**
 * 
 * @date 2024/4/20 13:33
 */
@Service
public class ResourcesServiceImpl extends ServiceImpl<ResourcesMapper, Resources> implements ResourcesService {


    @Override
    public Boolean uploadResources(Resources resources) {
        return this.save(resources);
    }


    @Override
    public IPage<Resources> getResourcesByTypeList(ResourcesShowReqDTO resourcesShowReqDTO) {
        IPage<Resources> page = new Page<Resources>(resourcesShowReqDTO.getPageNo(), resourcesShowReqDTO.getPageSize());
        LambdaQueryWrapper<Resources> query = new LambdaQueryWrapper<>();
        query.in(Resources::getTypeInt, resourcesShowReqDTO.getType());
        query.orderByAsc(Resources::getId);
        return this.page(page, query);
    }
}

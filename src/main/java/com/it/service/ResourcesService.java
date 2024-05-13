package com.it.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.entity.Resources;
import com.it.req.ResourcesShowReqDTO;

/**
 * @author yiming@micous.com
 * @date 2024/4/20 13:33
 */
public interface ResourcesService extends IService<Resources> {

    Boolean uploadResources(Resources resources);

    IPage<Resources> getResourcesByTypeList(ResourcesShowReqDTO resourcesShowReqDTO);
}

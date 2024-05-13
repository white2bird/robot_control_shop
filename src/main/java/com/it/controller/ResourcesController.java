package com.it.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.common.Result;
import com.it.constants.RequestPathContext;
import com.it.entity.Resources;
import com.it.req.ResourcesShowReqDTO;
import com.it.service.ResourcesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yiming@micous.com
 * @date 2024/4/20 13:39
 */
@RequestMapping("/resources")
@RestController
public class ResourcesController {

    @Resource
    private ResourcesService resourcesService;

    @Resource
    private RequestPathContext requestPathContext;

    @PostMapping("/upload")
    public Result upload(@RequestBody Resources resources){
        Boolean res = resourcesService.uploadResources(resources);
        return Result.ok(res, 200, "success");
    }

    @PostMapping("/getResourcesByType")
    public Result getResourcesByType(@RequestBody ResourcesShowReqDTO resourcesShowReqDTO){
        IPage<Resources> resourcesByTypeList = resourcesService.getResourcesByTypeList(resourcesShowReqDTO);
        List<Resources> records = resourcesByTypeList.getRecords();
        records.forEach(item->{
            item.setHref(String.join("", requestPathContext.getPath(), item.getHref()));
            item.setThumbnail(String.join("", requestPathContext.getPath(), item.getThumbnail()));
        });
        return Result.ok(resourcesByTypeList.getRecords(), 200, "success", resourcesByTypeList.getTotal());
    }
}

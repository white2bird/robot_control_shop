package com.it.controller;

import com.it.common.Result;
import com.it.req.DeviceReqDTO;
import com.it.service.DeviceInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 
 * @date 2024/4/5 11:18
 */
@Api(tags = "设备信息模块")
@RequestMapping
@RestController
public class DeviceInfoController {

    @Resource
    private DeviceInfoService deviceInfoService;

    @PostMapping( "/deviceNormalInfo")
    @ApiOperation(value = "获取设备基础信息")
    public Result deviceNormalInfo(@RequestBody DeviceReqDTO deviceReqDTO){
        return deviceInfoService.deviceNormalInfo(deviceReqDTO);
    }

}

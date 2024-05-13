package com.it.controller;

import com.it.common.Result;
import com.it.entity.OperateHistory;
import com.it.service.OperateHistoryService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 
 * @date 2024/4/5 11:21
 */
@Api(tags = "设备命令模块")
@RestController
public class DeviceOperationController {

    @Resource
    private OperateHistoryService operateHistoryService;

    @PostMapping("/operationList")
    public Result operationList(@RequestBody Long  deviceId){
        List<OperateHistory> historyByDeviceId = operateHistoryService.getHistoryByDeviceId(deviceId);
        return Result.ok(historyByDeviceId, 200, "success");
    }

    @PostMapping("/createHistoryOperation")
    public Result createHistoryOperation(@RequestBody OperateHistory operateHistory){
        Boolean res = operateHistoryService.createOperateHistory(operateHistory);
        return Result.ok(res, 200, "success");
    }



}

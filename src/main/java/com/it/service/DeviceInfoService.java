package com.it.service;

import com.it.common.Result;
import com.it.req.DeviceReqDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 
 * @date 2024/4/5 23:35
 */
public interface DeviceInfoService {

    Result deviceNormalInfo(DeviceReqDTO deviceReqDTO);

    Result deviceList();
}

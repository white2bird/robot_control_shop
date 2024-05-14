package com.it.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.common.Result;
import com.it.entity.DeviceInfo;
import com.it.mapper.DeviceInfoMapper;
import com.it.req.DeviceReqDTO;
import com.it.res.DeviceInfoResDTO;
import com.it.service.DeviceInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 
 * @date 2024/4/5 23:35
 */
@Service
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoMapper, DeviceInfo> implements DeviceInfoService {


    @Override
    public Result deviceNormalInfo(DeviceReqDTO deviceReqDTO) {
        DeviceInfo deviceInfo = this.getById(deviceReqDTO.getId());
        return Result.ok(deviceInfo, 200, "success");
    }

    @Override
    public Result deviceList() {
        List<DeviceInfo> list = this.list();
        return Result.ok(list, 200, "success");
    }
}

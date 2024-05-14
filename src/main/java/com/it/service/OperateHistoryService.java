package com.it.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.entity.OperateHistory;

import java.util.List;

/**
 * @author 
 * @date 2024/4/15 18:10
 */
public interface OperateHistoryService extends IService<OperateHistory> {

    List<OperateHistory> getHistoryByDeviceId(Long deviceId);

    boolean createOperateHistory(OperateHistory operateHistory);
}

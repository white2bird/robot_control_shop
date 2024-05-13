package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.common.Result;
import com.it.entity.OperateHistory;
import com.it.mapper.OperateHistoryMapper;
import com.it.service.OperateHistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 
 * @date 2024/4/15 18:34
 */
@Service
public class OperateHistoryServiceImpl extends ServiceImpl<OperateHistoryMapper, OperateHistory> implements OperateHistoryService {


    @Override
    public List<OperateHistory> getHistoryByDeviceId(Long deviceId) {
        LambdaQueryWrapper<OperateHistory> eq = new LambdaQueryWrapper<OperateHistory>().eq(OperateHistory::getDeviceId, deviceId);
        List<OperateHistory> list = this.list(eq);
        return list;
    }

    @Override
    public boolean createOperateHistory(OperateHistory operateHistory) {
        return this.save(operateHistory);
    }
}

package com.it.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.entity.Task;
import com.it.mapper.TaskMapper;
import com.it.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 
 * @date 2024/4/15 18:21
 */
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Override
    public List<Task> getTaskListByDeviceId(Long deviceId) {
        LambdaQueryWrapper<Task> eq = new LambdaQueryWrapper<Task>().eq(Task::getDeviceId, deviceId);
        return this.list(eq);
    }

    @Override
    public boolean createTask(Task task) {
        return this.save(task);
    }
}

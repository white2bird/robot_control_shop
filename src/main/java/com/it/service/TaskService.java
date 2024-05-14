package com.it.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.it.entity.Task;

import java.util.List;

/**
 * @author 
 * @date 2024/4/15 18:18
 */
public interface TaskService extends IService<Task> {

    List<Task> getTaskListByDeviceId(Long deviceId);

    boolean createTask(Task task);
}

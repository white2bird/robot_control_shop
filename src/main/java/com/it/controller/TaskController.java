package com.it.controller;

import com.it.common.Result;
import com.it.entity.Task;
import com.it.service.TaskService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 
 * @date 2024/4/5 11:01
 */
@Api(tags = "任务模块")
@RestController
public class TaskController {

    @Resource
    private TaskService taskService;

    @PostMapping("/taskList")
    public Result taskList(@RequestBody Long deviceId){
        List<Task> taskListByDeviceId = taskService.getTaskListByDeviceId(deviceId);
        return Result.ok(taskListByDeviceId, 200, "success");
    }

    @PostMapping("/createTask")
    public Result createTask(@RequestBody Task task){
        Boolean res = taskService.createTask(task);
        return Result.ok(res, 200, "success");
    }
}

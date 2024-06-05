package com.it.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 
 * @date 2024/4/15 17:34
 */
@Data
@TableName("task")
public class Task {

    private Long id;

    @TableField("device_id")
    private Long deviceId;

    @TableField("task_no")
    private String taskNo;

    @TableField("task_status")
    private Integer taskStatus;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date update_Time;
}

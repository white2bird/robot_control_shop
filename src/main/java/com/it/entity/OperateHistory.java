package com.it.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 
 * @date 2024/4/15 17:36
 */
@Data
@TableName("operate_history")
public class OperateHistory {

    @TableId
    private Long id;


    @TableField("device_id")
    private Long deviceId;

    private String operation;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date update_Time;
}

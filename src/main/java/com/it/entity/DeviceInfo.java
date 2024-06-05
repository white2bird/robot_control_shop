package com.it.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author 
 * @date 2024/4/15 18:04
 */
@Data
@TableName("device_info")
public class DeviceInfo {

    @TableId
    private Long id;

    @TableField("device_name")
    private String deviceName;

    @TableField("device_power")
    private Double devicePower;

    @TableField("device_status")
    private Integer deviceStatus;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date update_Time;
}

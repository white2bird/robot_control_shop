package com.it.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author yiming@micous.com
 * @date 2024/4/20 13:31
 */
@Data
@TableName("resources")
public class Resources {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField(value = "typeInt")
    private Integer typeInt;

    private String href;

    private String title;

    private String type;

    private String thumbnail;
}

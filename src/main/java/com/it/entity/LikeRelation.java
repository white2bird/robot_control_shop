package com.it.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @date 2024/5/14 13:58
 */
@Data
@TableName("like_relation")
public class LikeRelation {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long foodMenuId;

    private Long userId;
}

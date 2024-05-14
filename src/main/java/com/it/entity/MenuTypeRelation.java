package com.it.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @date 2024/5/14 11:45
 */
@Data
@TableName("menu_type_relation")
public class MenuTypeRelation {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private Long foodMenuId;

    private Long foodTypeId;
}

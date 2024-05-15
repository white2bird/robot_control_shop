package com.it.res;

import lombok.Data;

import java.util.List;

/**
 * @author 
 * @date 2024/5/15 11:23
 */
@Data
public class FoodMenuDetailVO {

    private Long id;

    private String name;

    private String pic;

    private String menuDesc;

    private Integer like;

    private List<String> stepList;
}

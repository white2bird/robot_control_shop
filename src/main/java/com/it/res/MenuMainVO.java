package com.it.res;

import lombok.Data;

import java.util.List;

/**
 * 
 * @date 2024/5/14 18:56
 */
@Data
public class MenuMainVO {

    private String name;

    private List<FoodMenuVO> subArr;
}

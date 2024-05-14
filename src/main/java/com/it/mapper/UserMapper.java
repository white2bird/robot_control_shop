package com.it.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.entity.User;

/**
 * @author 
 * @date 2024/4/13 22:30
 */
public interface UserMapper extends BaseMapper<User> {
    User findUserByName(String userName);
}

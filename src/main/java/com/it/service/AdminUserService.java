package com.it.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.it.common.Result;
import com.it.entity.User;
import com.it.req.CreateUserReqDTO;
import com.it.req.UserShowReqDTO;
import com.it.req.common.LoginReqDTO;

import java.util.List;
import java.util.Map;

/**
 * @author 
 * @date 2024/4/13 22:19
 */
public interface AdminUserService extends IService<User> {


    Result login(LoginReqDTO loginReqDTO);

    IPage getAllUser(UserShowReqDTO userShowReqDTO);

    Boolean createUser(CreateUserReqDTO createUserReqDTO);

    Boolean updateUser(CreateUserReqDTO createUserReqDTO);


    Boolean deleteUserById(Long id);

    Map<Long, String> getUsernameMap(List<Long> userList);

    Boolean logout();
}

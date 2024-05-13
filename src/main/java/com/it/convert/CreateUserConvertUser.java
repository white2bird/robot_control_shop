package com.it.convert;

import com.it.entity.User;
import com.it.req.CreateUserReqDTO;
import org.mapstruct.Mapper;

/**
 * @author 
 * @date 2024/4/15 00:18
 */
@Mapper(componentModel = "spring")
public interface CreateUserConvertUser {

    User convertToUser(CreateUserReqDTO createUserReqDTO);
}

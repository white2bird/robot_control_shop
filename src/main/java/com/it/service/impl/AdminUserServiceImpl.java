package com.it.service.impl;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.it.common.Result;
import com.it.constants.UserType;
import com.it.convert.CreateUserConvertUser;
import com.it.mapper.UserMapper;
import com.it.entity.User;
import com.it.req.CreateUserReqDTO;
import com.it.req.UserShowReqDTO;
import com.it.req.common.LoginReqDTO;
import com.it.service.AdminUserService;
import com.it.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 
 * @date 2024/4/13 22:26
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<UserMapper, User> implements AdminUserService {


    @Resource
    private UserMapper userMapper;

    @Resource
    private CreateUserConvertUser createUserConvertUser;

    @Resource
    private MenuService menuService;


    @Override
    public Result login(LoginReqDTO loginReqDTO) {
        User user = userMapper.findUserByName(loginReqDTO.getUsername());
        if(Objects.isNull(user)){
            throw new RuntimeException("用户密码或者账号错误");
        }
        StpUtil.login(user.getId());
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        Map<String, Object> res = new HashMap<>();
        res.put("token", tokenInfo.getTokenValue());

        res.put("menuList", menuService.getUserMenuListByPermission(user.getId()));
        return Result.ok(res, 200, "success");
    }

    @Override
    public IPage getAllUser(UserShowReqDTO userShowReqDTO) {
        userShowReqDTO.getPageNo();
        IPage page = new Page(userShowReqDTO.getPageNo(), userShowReqDTO.getPageSize());
        QueryWrapper<User> objectQueryWrapper = new QueryWrapper<>();
        return this.page(page, objectQueryWrapper);
    }

    @Override
    public Boolean createUser(CreateUserReqDTO createUserReqDTO){
        User user = createUserConvertUser.convertToUser(createUserReqDTO);
        return this.save(user);
    }

    @Override
    public Boolean updateUser(CreateUserReqDTO createUserReqDTO){
        User user = createUserConvertUser.convertToUser(createUserReqDTO);
        return this.update(user, new LambdaUpdateWrapper<User>().eq(User::getId, createUserReqDTO.getId()));
    }


    @Override
    public Boolean deleteUserById(Long id) {
        return this.removeById(id);
    }

    @Override
    public Map<Long, String> getUsernameMap(List<Long> userList) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.in(User::getId, userList);
        userLambdaQueryWrapper.select(User::getUsername, User::getId);
        List<User> list = this.list(userLambdaQueryWrapper);
        return list.stream().collect(Collectors.toMap(User::getId, User::getUsername));
    }


    @Override
    public Boolean logout() {
        StpUtil.logout();
        return true;
    }
}

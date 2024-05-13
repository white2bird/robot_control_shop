package com.it.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.it.common.Result;
import com.it.req.CreateUserReqDTO;
import com.it.req.UserShowReqDTO;
import com.it.req.common.LoginReqDTO;
import com.it.service.AdminUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author 
 * @date 2024/4/13 21:35
 */
@RequestMapping("/user")
@RestController
public class AdminUserController {


    @Resource
    private AdminUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody @Valid LoginReqDTO loginReqDTO){
        return userService.login(loginReqDTO);
    }

    @Resource
    private AdminUserService adminUserService;

    @PostMapping("/getList")
    public Result getList(@RequestBody(required = false) UserShowReqDTO userShowReqDTO){
        IPage allUser = adminUserService.getAllUser(userShowReqDTO);
        return Result.ok(allUser.getRecords(), 200, "success", allUser.getTotal());
    }

    @PostMapping("/createUser")
    public Result createUser(@RequestBody @Valid CreateUserReqDTO createUserReqDTO){
        Boolean user = userService.createUser(createUserReqDTO);
        return Result.ok(user, 200, "success");
    }

    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody CreateUserReqDTO createUserReqDTO){
        Boolean user = userService.updateUser(createUserReqDTO);
        return Result.ok(user, 200, "success");
    }

    @PostMapping("/deleteUser")
    public Result deleteUser(@RequestBody Long userId){
        Boolean user = userService.deleteUserById(userId);
        return Result.ok(user, 200, "success");
    }

    @GetMapping("/logout")
    public Result logout(){
        return Result.ok(userService.logout(), 200, "success");
    }
}

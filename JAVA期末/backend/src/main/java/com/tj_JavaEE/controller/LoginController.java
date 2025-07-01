package com.tj_JavaEE.controller;


import com.tj_JavaEE.entity.Admin;
import com.tj_JavaEE.entity.Result;
import com.tj_JavaEE.entity.User;
import com.tj_JavaEE.service.LoginService;
import com.tj_JavaEE.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public Result userlogin(@RequestBody User user){
        log.info("用户登录:{}",user);
        if(user.getAccount()==null||user.getPassword()==null||user.getAccount().isEmpty()||user.getPassword().isEmpty()){
            return Result.error(0,"账号或密码为空");
        }

        User e =loginService.userlogin(user);

        log.info("用户登录1:{}",e);
        //登录成功，生成并下发令牌
        if(e!=null){
            Map<String,Object> claims = new HashMap<>();
            claims.put("userId",e.getUserId());
            claims.put("identity","user");
            String jwt=JwtUtils.generateJwt(claims);
            log.info("用户登录2:{}",JwtUtils.parseJwt(jwt));
            return Result.success(1,jwt);
        }

        //登陆失败，返回错误信息


        return Result.error(0,"账号或密码错误");


    }

    @PostMapping("/admin/login")
    public Result adminlogin(@RequestBody Admin admin){
        log.info("管理员登录:{}",admin);
        if(admin.getAccount()==null||admin.getPassword()==null||admin.getAccount().isEmpty()||admin.getPassword().isEmpty()){
            return Result.error(0,"账号或密码为空");
        }

        Admin e =loginService.adminlogin(admin);

        log.info("管理员登录1:{}",e);
        //登录成功，生成并下发令牌
        if(e!=null){
            Map<String,Object> claims = new HashMap<>();
            claims.put("adminId",e.getAdminId());
            claims.put("identity","admin");
            String jwt=JwtUtils.generateJwt(claims);
            log.info("管理员登录2:{}",JwtUtils.parseJwt(jwt));
            return Result.success(1,jwt);
        }

        //登陆失败，返回错误信息


        return Result.error(0,"账号或密码错误");


    }




}

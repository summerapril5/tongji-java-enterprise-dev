package com.tj_JavaEE.controller;

import com.tj_JavaEE.entity.Admin;
import com.tj_JavaEE.entity.Result;
import com.tj_JavaEE.entity.User;
import com.tj_JavaEE.service.RegisterService;
import com.tj_JavaEE.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/user/register")
    public Result userregister(@RequestBody User user){

        User u = registerService.findUserByAccount(user.getAccount());

        if(u==null){
            log.info("用户注册:{}",user);
            registerService.userRegister(user);
            //发放令牌
            if(user!=null){
                Map<String,Object> claims = new HashMap<>();

                claims.put("userId",user.getUserId());
                claims.put("identity","user");

                String jwt= JwtUtils.generateJwt(claims);

                return Result.success(1,"注册成功",jwt);
            }
            return Result.error(0,"令牌发放失败");
        }else{
            return Result.error(0,"注册失败");

        }

    }

    @PostMapping("/admin/register")
    public Result adminregister(@RequestBody Admin admin){

        Admin a = registerService.findAdminByAccount(admin.getAccount());

        if(a==null){
            log.info("管理员注册:{}",admin);
            registerService.adminRegister(admin);
            //发放令牌
            if(admin!=null){
                Map<String,Object> claims = new HashMap<>();

                claims.put("adminId",admin.getAdminId());
                claims.put("identity","admin");

                String jwt= JwtUtils.generateJwt(claims);

                return Result.success(1,"注册成功",jwt);
            }
            return Result.error(0,"令牌发放失败");
        }else{
            return Result.error(0,"注册失败");

        }

    }

}

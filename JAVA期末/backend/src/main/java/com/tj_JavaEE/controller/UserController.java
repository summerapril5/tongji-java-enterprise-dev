package com.tj_JavaEE.controller;

import com.tj_JavaEE.entity.Result;
import com.tj_JavaEE.entity.User;
import com.tj_JavaEE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Result list(){
        System.out.println("Controller");

        return Result.success(userService.list());
    }
}
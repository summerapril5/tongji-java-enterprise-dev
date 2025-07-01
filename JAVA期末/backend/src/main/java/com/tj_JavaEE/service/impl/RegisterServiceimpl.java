package com.tj_JavaEE.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.tj_JavaEE.entity.Admin;
import com.tj_JavaEE.entity.User;
import com.tj_JavaEE.mapper.RegisterMapper;
import com.tj_JavaEE.mapper.UserMapper;
import com.tj_JavaEE.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RegisterServiceimpl implements RegisterService {

    @Autowired
    private RegisterMapper registerMapper;


    @Override
    public User findUserByAccount(String account) {
        return registerMapper.findUserByAccount(account);
    }

    @Override
    public void userRegister(User user) {
        //加密
        user.setPassword(BCrypt.hashpw(user.getPassword()));
        //添加


        registerMapper.userAdd(user);


    }

    @Override
    public Admin findAdminByAccount(String account) {
        return registerMapper.findAdminByAccount(account);
    }

    @Override
    public void adminRegister(Admin admin) {
        //加密
        admin.setPassword(BCrypt.hashpw(admin.getPassword()));
        //添加
        registerMapper.adminAdd(admin);
    }


}

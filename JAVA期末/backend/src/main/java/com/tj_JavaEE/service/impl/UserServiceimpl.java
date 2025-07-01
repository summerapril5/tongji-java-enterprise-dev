package com.tj_JavaEE.service.impl;

import com.tj_JavaEE.entity.User;
import com.tj_JavaEE.mapper.UserMapper;
import com.tj_JavaEE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void save() {
        System.out.println("测试");
    }

    @Override
    public List<User> list() {
        System.out.println("Service");
        return userMapper.list();
    }
}

package com.tj_JavaEE.service;

import com.tj_JavaEE.entity.User;

import java.util.List;

public interface UserService {
    public void save();

    public List<User> list();
}

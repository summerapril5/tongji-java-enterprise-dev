package com.tj_JavaEE.service;

import com.tj_JavaEE.entity.Admin;
import com.tj_JavaEE.entity.User;

public interface RegisterService {

    //依据账号查询用户
    User findUserByAccount(String account);

    //注册
    void userRegister(User user);

    Admin findAdminByAccount(String account);

    void adminRegister(Admin admin);

}

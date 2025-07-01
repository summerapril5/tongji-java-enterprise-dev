package com.tj_JavaEE.service;

import com.tj_JavaEE.entity.Admin;
import com.tj_JavaEE.entity.User;

public interface LoginService {

    User userlogin(User user);

    Admin adminlogin(Admin admin);
}

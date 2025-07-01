package com.tj_JavaEE.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.tj_JavaEE.entity.Admin;
import com.tj_JavaEE.entity.User;
import com.tj_JavaEE.mapper.LoginMapper;
import com.tj_JavaEE.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LoginServiceimpl implements LoginService {

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public User userlogin(User user) {

        User u =loginMapper.findUserByAccount(user);
        log.info("用户登录u:{}",u);
        if(u!=null&&BCrypt.checkpw(user.getPassword(),u.getPassword())){
            return u;
        }
        return null;
    }

    @Override
    public Admin adminlogin(Admin admin) {
        Admin a = loginMapper.findAdminByAccount(admin);
        log.info("管理员登录a:{}",a);
        if(a!=null&&BCrypt.checkpw(admin.getPassword(),a.getPassword())){
            return a;
        }
        return null;
    }

}

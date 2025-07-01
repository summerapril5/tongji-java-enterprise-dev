package com.tj_JavaEE.service.impl;

import com.tj_JavaEE.mapper.AdminMapper;
import com.tj_JavaEE.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceimpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
}

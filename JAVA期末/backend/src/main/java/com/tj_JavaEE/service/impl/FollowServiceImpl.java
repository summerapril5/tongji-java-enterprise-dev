package com.tj_JavaEE.service.impl;

import com.tj_JavaEE.entity.User;
import com.tj_JavaEE.mapper.FollowMapper;
import com.tj_JavaEE.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private FollowMapper followMapper;

    @Override
    public List<User> getFollowsByUserId(Long userId) {
        return followMapper.selectFollowsByUserId(userId);
    }
}

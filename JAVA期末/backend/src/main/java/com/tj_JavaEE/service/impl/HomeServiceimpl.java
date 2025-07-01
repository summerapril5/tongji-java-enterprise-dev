package com.tj_JavaEE.service.impl;

import com.tj_JavaEE.dto.Pst;
import com.tj_JavaEE.mapper.HomeMapper;
import com.tj_JavaEE.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeServiceimpl implements HomeService {

    @Autowired
    private HomeMapper homeMapper;


    @Override
    public List<Long> getUserLikedCategories(long userId) {

        return homeMapper.getUserLike(userId);
    }

    @Override
    public List<Pst> getPostsByCategories(List<Long> categories, int limit) {

        return homeMapper.getPostsByCategories(categories,limit);
        // 实现查询逻辑并返回pst对象列表

    }

    @Override
    public List<Pst> getRandomPosts(int limit, List<Long> excludePostIds) {


        // 实现查询逻辑并返回pst对象列表
        return homeMapper.getRandomPosts(limit);
    }
}

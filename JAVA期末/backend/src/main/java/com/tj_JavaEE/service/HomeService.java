package com.tj_JavaEE.service;

import com.tj_JavaEE.dto.Pst;

import java.util.List;

public interface HomeService {
    // 获取用户喜欢的帖子类别
    List<Long> getUserLikedCategories(long userId);
    
    // 根据类别获取帖子
    List<Pst> getPostsByCategories(List<Long> categories, int limit);
    
    // 获取随机帖子
    List<Pst> getRandomPosts(int limit, List<Long> excludePostIds);
}

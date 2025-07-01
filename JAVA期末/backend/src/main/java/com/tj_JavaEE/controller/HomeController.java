package com.tj_JavaEE.controller;

import com.tj_JavaEE.dto.Pst;
import com.tj_JavaEE.entity.Result;
import com.tj_JavaEE.service.HomeService;
import com.tj_JavaEE.util.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/HomeView")
public class HomeController {
    @Autowired
    private HomeService homeService;

    @GetMapping("/getPosts")
    public Result getPosts(HttpServletRequest request) {
        final int RECOMMEND_COUNT = 10;
        
        // 获取用户 token
        String token = request.getHeader("Authorization");
        String jwt = token.substring(7);
        Claims claims= JwtUtils.parseJwt(jwt);
        String idt = claims.get("identity", String.class);


        if (idt.equals( "user" )) {
            int userId = claims.get("userId", Integer.class);
            
            // 获取用户喜欢的帖子类别
            List<Long> likedCategories = homeService.getUserLikedCategories(userId);
            
            if (!likedCategories.isEmpty()) {
                // 优先获取用户喜欢类别的帖子
                List<Pst> recommendedPosts = homeService.getPostsByCategories(likedCategories, RECOMMEND_COUNT);
                
                // 如果推荐的帖子不够10个，随机补充
                if (recommendedPosts.size() < RECOMMEND_COUNT) {
                    int remainingCount = RECOMMEND_COUNT - recommendedPosts.size();
                    List<Long> existingPostIds = recommendedPosts.stream()
                        .map(Pst::getId)
                        .map(Integer::longValue)
                        .collect(Collectors.toList());
                    
                    List<Pst> randomPosts = homeService.getRandomPosts(remainingCount, existingPostIds);
                    recommendedPosts.addAll(randomPosts);
                }
                
                return Result.success(recommendedPosts);
            }
        }
        
        // 如果用户未登录或没有点赞历史，返回随机帖子
        return Result.success(homeService.getRandomPosts(RECOMMEND_COUNT, new ArrayList<>()));
    }


}

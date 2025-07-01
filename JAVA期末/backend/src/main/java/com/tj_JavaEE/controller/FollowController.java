package com.tj_JavaEE.controller;

import com.tj_JavaEE.entity.User;
import com.tj_JavaEE.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<User>> getFollowsByUserId(@PathVariable Long userId) {
        List<User> follows = followService.getFollowsByUserId(userId);
        return ResponseEntity.ok(follows);
    }
}

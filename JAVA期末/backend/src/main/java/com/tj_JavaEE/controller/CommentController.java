package com.tj_JavaEE.controller;

import com.tj_JavaEE.dto.Commentcontent;
import com.tj_JavaEE.entity.Result;
import com.tj_JavaEE.service.CommentService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.tj_JavaEE.util.JwtUtils;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/{postId}")
    public Result createComment(@PathVariable long postId , @RequestBody Commentcontent comment , @RequestHeader("Authorization") String token) {
        token = token.substring(7);
        Claims claim = JwtUtils.parseJwt(token);
        int useId = claim.get("userId", Integer.class);
        comment.setCommenterId(useId);
        comment.setPostId(postId);
        commentService.addComment(comment);
        return new Result();
    }

    @GetMapping("/{postId}")
    public Result getComments(@PathVariable  long postId ) {


        return Result.success(commentService.getCommentsByPostId(postId));

    }

}

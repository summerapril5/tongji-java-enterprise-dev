package com.tj_JavaEE.dto;

import com.tj_JavaEE.entity.Post;
import lombok.Data;

@Data
public class CommentInfo {
    private Long commentId;
    private String commentContent;
    private Long commenterId;
    private Long postId;
    private String postTitle;
    private String commentTime; // 添加评论时间字段
}

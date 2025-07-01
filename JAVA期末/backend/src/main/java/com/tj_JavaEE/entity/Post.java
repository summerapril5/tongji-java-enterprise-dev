package com.tj_JavaEE.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @TableField("post_id")
    private Long postId;

    @TableField("post_title")
    private String postTitle;

    @TableField("category_id")
    private Long categoryId;

    @TableField("publish_time")
    private LocalDateTime publishTime;

    @TableField("post_content")
    private String postContent;

    @TableField("author_id")
    private Long authorId;

    @TableField("like_count")
    private Long likeCount;

    @TableField("dislike_count")
    private Long dislikeCount;

    private String status;
}

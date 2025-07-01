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
public class Comment {

    @TableField("comment_id")
    private int commentId;

    @TableField("comment_content")
    private String content;

    @TableField("commenter_id")
    private int commenterId;

    @TableField("post_id")
    private long postId;

    @TableField("publish_time")
    private LocalDateTime commentTime;

    @TableField("like_count")
    private int likeCount;

    @TableField("dislike_count")
    private int dislikeCount;




}

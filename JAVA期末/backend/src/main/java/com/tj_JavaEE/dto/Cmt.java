package com.tj_JavaEE.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cmt {
    private int authorId;
    private String authorAvatar;
    private int id;
    private String authorName;
    private String content;
    private String date;
    private int isLiked=0;
    private int isDisliked=0;
    private int likes;
    private int dislikes;
}

package com.tj_JavaEE.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pst {
    private int id;
    private String title;
    private String content;
    private int authorId;
    private String authorName;
    private String authorAvatar;
    private String releaseTime;
    private int likes;
    private int dislikes;
    private int categoryId;
}

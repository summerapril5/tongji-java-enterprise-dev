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
public class AuditPostInfo {
    private Long postId;

    private String postTitle;

    private String categoryName;

    private LocalDateTime publishTime;

    private String postContent;

    private Long authorId;

    private String authorName;
}

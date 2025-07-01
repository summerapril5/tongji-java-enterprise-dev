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
public class PostComplainInfo {
    private Long reportId;

    private String reportReason;

    private Long reporterId;

    private String reporterName;

    private Long reportedPostId;

    private String postTitle;

    private LocalDateTime reportTime;
}

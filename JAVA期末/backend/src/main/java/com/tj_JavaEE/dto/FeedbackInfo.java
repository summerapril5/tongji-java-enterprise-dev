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
public class FeedbackInfo {
    private Long feedbackId;

    private String feedbackContent;

    private Long feedbackUserId;

    private String feedbackUserName;

    private LocalDateTime feedbackTime;
}

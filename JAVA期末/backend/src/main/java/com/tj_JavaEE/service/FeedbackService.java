package com.tj_JavaEE.service;

import com.tj_JavaEE.dto.FeedbackInfo;

import java.util.List;

public interface FeedbackService {
    List<FeedbackInfo> feedbackInfoList();

    void feedbackProcess(Long feedbackId);
}

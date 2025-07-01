package com.tj_JavaEE.service.impl;

import com.tj_JavaEE.dto.FeedbackInfo;
import com.tj_JavaEE.mapper.FeedbackMapper;
import com.tj_JavaEE.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceimpl implements FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @Override
    public List<FeedbackInfo> feedbackInfoList() {
        return feedbackMapper.feedbackInfoList();
    }

    @Override
    public void feedbackProcess(Long feedbackId) {
        feedbackMapper.feedbackProcess(feedbackId);
    }
}

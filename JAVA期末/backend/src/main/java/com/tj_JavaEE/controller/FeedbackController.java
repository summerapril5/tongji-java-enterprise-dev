package com.tj_JavaEE.controller;

import com.tj_JavaEE.dto.FeedbackId;
import com.tj_JavaEE.dto.FeedbackInfo;
import com.tj_JavaEE.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @GetMapping("/feedbackInfo")
    public ResponseEntity<List<FeedbackInfo>> feedbackInfoList(){
        System.out.println("feedbackInfo");
        return ResponseEntity.ok(feedbackService.feedbackInfoList());
    }

    @PostMapping("/feedbackProcess")
    public ResponseEntity<Boolean> feedbackProcess(@RequestBody FeedbackId feedbackId) {
        System.out.println("feedbackProcess");
        feedbackService.feedbackProcess(feedbackId.getFeedbackId());
        return ResponseEntity.ok(true);
    }
}

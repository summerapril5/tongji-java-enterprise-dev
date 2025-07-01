package com.tj_JavaEE.controller;

import com.tj_JavaEE.dto.PostComplainInfo;
import com.tj_JavaEE.dto.ReportId;
import com.tj_JavaEE.dto.UserComplainInfo;
import com.tj_JavaEE.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/userComplainInfo")
    public ResponseEntity<List<UserComplainInfo>> userComplainInfoList(){
        System.out.println("userComplainInfo");
        return ResponseEntity.ok(reportService.userComplainInfoList());
    }

    @PostMapping("/userComplainProcess")
    public ResponseEntity<Boolean> userComplainProcess(@RequestBody ReportId reportId) {
        System.out.println("userComplainProcess");
        reportService.userComplainProcess(reportId.getReportId());
        return ResponseEntity.ok(true);
    }

    @GetMapping("/postComplainInfo")
    public ResponseEntity<List<PostComplainInfo>> postComplainInfoList(){
        System.out.println("postComplainInfo");
        return ResponseEntity.ok(reportService.postComplainInfoList());
    }

    @PostMapping("/postComplainProcess")
    public ResponseEntity<Boolean> postComplainProcess(@RequestBody ReportId reportId) {
        System.out.println("postComplainProcess");
        reportService.postComplainProcess(reportId.getReportId());
        return ResponseEntity.ok(true);
    }
}

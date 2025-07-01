package com.tj_JavaEE.service.impl;

import com.tj_JavaEE.dto.PostComplainInfo;
import com.tj_JavaEE.dto.UserComplainInfo;
import com.tj_JavaEE.mapper.ReportMapper;
import com.tj_JavaEE.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceimpl implements ReportService {
    @Autowired
    private ReportMapper reportMapper;

    @Override
    public List<UserComplainInfo> userComplainInfoList() {
        return reportMapper.userComplainInfoList();
    }

    @Override
    public void userComplainProcess(Long reportId) {
        reportMapper.userComplainProcess(reportId);
    }

    @Override
    public List<PostComplainInfo> postComplainInfoList() {
        return reportMapper.postComplainInfoList();
    }

    @Override
    public void postComplainProcess(Long reportId) {
        reportMapper.postComplainProcess(reportId);
    }
}

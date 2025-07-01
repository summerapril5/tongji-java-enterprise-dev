package com.tj_JavaEE.service;

import com.tj_JavaEE.dto.PostComplainInfo;
import com.tj_JavaEE.dto.UserComplainInfo;

import java.util.List;

public interface ReportService {
    List<UserComplainInfo> userComplainInfoList();

    void userComplainProcess(Long reportId);

    List<PostComplainInfo> postComplainInfoList();

    void postComplainProcess(Long reportId);
}

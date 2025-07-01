package com.tj_JavaEE.service;

import com.tj_JavaEE.dto.AuditPostInfo;
import com.tj_JavaEE.dto.Pst;

import java.util.List;

public interface PostService {
    List<AuditPostInfo> auditPostInfoList();

    void postStatusChange(Long postId, String status);

    Pst getPostById(Long postId);

    void createPost(Pst pst);


    List<Pst> search(String keyword);


}

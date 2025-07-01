package com.tj_JavaEE.service.impl;

import com.tj_JavaEE.dto.Pst;
import com.tj_JavaEE.mapper.SearchMapper;
import com.tj_JavaEE.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceimpl implements SearchService {
    @Autowired
    private SearchMapper searchMapper;

    @Override
    public List<Pst> search(String keyword) {
        return searchMapper.search(keyword);
    }

    public String searchUserName(int userId) {
        return searchMapper.searchUserName(userId);
    }

    public String searchUserAvatar(int userId) {
        return searchMapper.searchUserAvatar(userId);
    }
}

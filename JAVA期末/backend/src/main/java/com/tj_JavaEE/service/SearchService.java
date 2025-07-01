package com.tj_JavaEE.service;

import com.tj_JavaEE.dto.Pst;

import java.util.List;

public interface SearchService {

    List<Pst> search(String keyword);
}

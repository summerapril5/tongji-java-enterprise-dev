package com.tj_JavaEE.controller;

import com.tj_JavaEE.entity.Result;
import com.tj_JavaEE.service.AdminService;
import com.tj_JavaEE.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/{title}")
    public Result search(@PathVariable("title") String title) {
        return Result.success(searchService.search(title));
    }
}

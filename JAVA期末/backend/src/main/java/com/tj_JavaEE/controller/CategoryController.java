package com.tj_JavaEE.controller;

import com.tj_JavaEE.dto.CategoryAddInfo;
import com.tj_JavaEE.entity.Result;
import com.tj_JavaEE.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/categoryAdd")
    public ResponseEntity<Boolean> categoryAdd(CategoryAddInfo categoryAddInfo){
        System.out.println(categoryAddInfo.getCategoryName());
        System.out.println(categoryAddInfo.getDescription());
        System.out.println(categoryAddInfo.getImage().isEmpty());
        categoryService.categoryAdd(categoryAddInfo);
        return ResponseEntity.ok(true);
    }

    @GetMapping
    public Result getAllCategory() {
        return Result.success(categoryService.getAllCategories());
    }

    @GetMapping("/{categoryId}")
    public Result getCategoryById(@PathVariable("categoryId") long categoryId) {
        return Result.success(categoryService.getPostsByCategory(categoryId));
    }
}

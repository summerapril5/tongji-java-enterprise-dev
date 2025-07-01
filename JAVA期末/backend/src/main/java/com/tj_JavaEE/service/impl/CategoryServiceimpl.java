package com.tj_JavaEE.service.impl;

import com.tj_JavaEE.dto.Pst;
import com.tj_JavaEE.dto.CategoryAddInfo;
import com.tj_JavaEE.entity.Category;
import com.tj_JavaEE.mapper.CategoryAdminMapper;
import com.tj_JavaEE.mapper.CategoryMapper;
import com.tj_JavaEE.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class CategoryServiceimpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryAdminMapper categoryAdminMapper;

    private static final String baseUrl = "/src/main/resources/static/";

    @Override
    public void categoryAdd(CategoryAddInfo categoryAddInfo) {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + categoryAddInfo.getImage().getOriginalFilename();
        String followUrl = "pictures/category/"+uniqueFileName;
        String filePath = new File("").getAbsolutePath()+baseUrl+followUrl;

        File dest = new File(filePath);
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try {
            categoryAddInfo.getImage().transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Category category=new Category();
        category.setCategoryName(categoryAddInfo.getCategoryName());
        category.setCategoryDescription(categoryAddInfo.getDescription());
        category.setCategoryImageUrl(followUrl);
        categoryAdminMapper.insert(category);
    }

    @Override
    public List<com.tj_JavaEE.dto.Category> getAllCategories(){
        return categoryMapper.getAll();
    }

    @Override
    public List<Pst> getPostsByCategory(long categoryId){
        return categoryMapper.getPostsByCategory(categoryId);
    }


}


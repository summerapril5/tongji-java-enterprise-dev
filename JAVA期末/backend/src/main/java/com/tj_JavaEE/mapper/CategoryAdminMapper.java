package com.tj_JavaEE.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tj_JavaEE.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryAdminMapper extends BaseMapper<Category> {
}
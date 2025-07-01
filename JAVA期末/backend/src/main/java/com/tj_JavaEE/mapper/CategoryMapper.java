package com.tj_JavaEE.mapper;

import com.tj_JavaEE.dto.Category;
import com.tj_JavaEE.dto.Pst;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("select f.category_id as id , f.category_name as name , f.category_description as description , f.category_image_url as imageUrl from category f")
    public List<Category> getAll();

    @Select("select f.post_id as id , f.post_title as title , f.post_content as content , f.author_id as authorId , f.like_count as likes , f.dislike_count as dislikes , t.avatar_url as authorAvatar , t.nickname as authorName  from post f inner join user t  on t.user_id = f.author_id where f.category_id = #{categoryId} ")
    public List<Pst> getPostsByCategory(@Param("categoryId") long categoryId);
}

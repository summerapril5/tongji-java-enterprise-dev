package com.tj_JavaEE.mapper;

import com.tj_JavaEE.dto.Pst;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchMapper {

    @Select("select f.post_id as id , f.post_title as title , f.post_content as content , f.author_id as authorId , f.like_count as likes , f.dislike_count as dislikes , t.avatar_url as authorAvatar , t.nickname as authorName  from post f inner join user t on t.user_id=f.author_id where f.post_title like #{keyword} ")
    List<Pst> search(@Param("keyword") String keyword);

    @Select("select nickname from user where user_id =#{userId}")
    String searchUserName(@Param("userId") int userId);

    @Select("select avatar_url from user where user_id =#{userId}")
    String searchUserAvatar(@Param("userId") int userId);

}

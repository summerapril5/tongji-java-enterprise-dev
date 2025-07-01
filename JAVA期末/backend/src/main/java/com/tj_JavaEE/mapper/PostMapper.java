package com.tj_JavaEE.mapper;

import com.tj_JavaEE.dto.AuditPostInfo;
import com.tj_JavaEE.dto.Pst;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Select("select p.post_id as postId, p.post_title as postTitle, c.category_name as categoryName, p.publish_time as publishTime, p.post_content as postContent, p.author_id as authorId, u.nickname as authorName from post p inner join category c on p.category_id = c.category_id inner join user u on p.author_id = u.user_id where p.status = 'pending'")
    List<AuditPostInfo> auditPostInfoList();

    @Update("UPDATE post SET status = #{status} WHERE post_id = #{postId}")
    void postStatusChange(@Param("postId") Long postId, @Param("status") String status);

    @Select("select f.post_id as id , f.post_title as title , f.post_content as content , f.author_id as authorId , f.like_count as likes , f.dislike_count as dislikes , t.avatar_url as authorAvatar , t.nickname as authorName  from post f inner join user t on t.user_id=f.author_id where f.post_id = #{postId}")
    Pst getPst(@Param("postId") Long postId);


    @Insert("insert into post(post_title,post_content,author_id,publish_time,like_count,dislike_count,category_id) values(#{title},#{content},#{authorId},#{releaseTime},#{likes},#{dislikes},#{categoryId})")
    void createPost(Pst pst);
}

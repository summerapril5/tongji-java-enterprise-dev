package com.tj_JavaEE.mapper;

import com.tj_JavaEE.dto.AuditPostInfo;
import com.tj_JavaEE.dto.CommentInfo;
import com.tj_JavaEE.entity.Post;
import com.tj_JavaEE.entity.User;
import com.tj_JavaEE.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserCenterMapper {

    @Select("SELECT * FROM user WHERE user_id = #{userId}")
    User selectUserById(Integer userId);

    @Select("SELECT post_id AS postId, post_title AS postTitle, category_id AS categoryId, publish_time AS publishTime, post_content AS postContent, author_id AS authorId, like_count AS likeCount, dislike_count AS dislikeCount, status FROM post WHERE author_id = #{authorId}")
    List<Post> selectPostsByAuthorId(@Param("authorId") long authorId);

    @Select("SELECT p.post_id AS postId, p.post_title AS postTitle, c.category_name AS categoryName, " +
            "p.publish_time AS publishTime, p.post_content AS postContent, p.author_id AS authorId, " +
            "u.nickname AS authorName FROM post p " +
            "INNER JOIN category c ON p.category_id = c.category_id " +
            "INNER JOIN user u ON p.author_id = u.user_id " +
            "INNER JOIN user_favorite uf ON p.post_id = uf.favorite_post_id " +
            "WHERE uf.user_id = #{userId}")
    List<AuditPostInfo> selectFavoritePostsByUserId(@Param("userId") long userId);

    @Select("SELECT c.comment_id AS commentId, c.comment_content AS commentContent, c.commenter_id AS commenterId, p.post_id AS postId, p.post_title AS postTitle, c.publish_time AS commentTime " +
            "FROM comment c " +
            "INNER JOIN post p ON c.post_id = p.post_id " +
            "WHERE c.commenter_id = #{commenterId}")
    List<CommentInfo> selectCommentsByCommenterId(@Param("commenterId") long commenterId);
    void updateUser(User user);

    @Select("SELECT c.category_id, c.category_name, c.category_description, c.category_image_url " +
            "FROM user_preference up " +
            "INNER JOIN category c ON up.preferred_category_id = c.category_id " +
            "WHERE up.user_id = #{userId}")
    List<Category> selectPreferredCategoriesByUserId(@Param("userId") long userId);


    @Delete("DELETE FROM post WHERE post_id = #{postId}")
    void deletePost(@Param("postId") Long postId);

    @Delete("DELETE FROM user_favorite WHERE user_id = #{userId} AND favorite_post_id = #{postId}")
    void deleteFavorite(@Param("userId") Long userId, @Param("postId") Long postId);

    @Delete("DELETE FROM comment WHERE comment_id = #{commentId}")
    void deleteComment(@Param("commentId") Long commentId);

    @Delete("DELETE FROM user_follow WHERE user_id = #{userId} AND follow_user_id = #{followUserId}")
    void unfollowUser(@Param("userId") Integer userId, @Param("followUserId") Integer followUserId);
}

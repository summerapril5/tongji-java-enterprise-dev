package com.tj_JavaEE.service;

import com.tj_JavaEE.dto.AuditPostInfo;
import com.tj_JavaEE.dto.CommentInfo;
import com.tj_JavaEE.entity.Post;
import com.tj_JavaEE.entity.User;
import com.tj_JavaEE.entity.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserCenterService {
    User getUserById(Integer userId);
    void updateUser(User user);
    List<Category> getPreferredCategoriesByUserId(Long userId);
    void deletePost(Long postId);

    void deleteFavorite(Long userId, Long postId);
    void deleteComment(Long commentId);
    List<Post> getPostsByAuthorId(@Param("authorId") Long authorId);

    List<AuditPostInfo> getFavoritePostsByUserId(@Param("userId") Long userId);

    List<CommentInfo> getCommentsByCommenterId(Long commenterId);

    void unfollowUser(Integer userId, Integer followUserId);
}

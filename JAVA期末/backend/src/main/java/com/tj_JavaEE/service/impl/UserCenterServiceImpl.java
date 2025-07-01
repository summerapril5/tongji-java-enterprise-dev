package com.tj_JavaEE.service.impl;

import com.tj_JavaEE.dto.AuditPostInfo;
import com.tj_JavaEE.dto.CommentInfo;
import com.tj_JavaEE.entity.Post;
import com.tj_JavaEE.entity.User;
import com.tj_JavaEE.mapper.UserCenterMapper;
import com.tj_JavaEE.service.UserCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tj_JavaEE.entity.Category;
import java.util.List;



@Service
public class UserCenterServiceImpl implements UserCenterService {

    @Autowired
    private UserCenterMapper userCenterMapper;

    @Override
    public User getUserById(Integer userId) {
        return userCenterMapper.selectUserById(userId);
    }

    @Override
    public void updateUser(User user) {
        // 进行业务逻辑处理，如验证用户身份，数据校验
        userCenterMapper.updateUser(user);
    }

    @Override
    public List<Category> getPreferredCategoriesByUserId(Long userId) {
        return userCenterMapper.selectPreferredCategoriesByUserId(userId);
    }

    @Override
    public List<Post> getPostsByAuthorId(Long authorId) {
        return userCenterMapper.selectPostsByAuthorId(authorId);
    }

    @Override
    public List<AuditPostInfo> getFavoritePostsByUserId(Long userId) {
        return userCenterMapper.selectFavoritePostsByUserId(userId);
    }


    @Override
    public List<CommentInfo> getCommentsByCommenterId(Long commenterId) {
        return userCenterMapper.selectCommentsByCommenterId(commenterId);
    }

    @Override
    public void deletePost(Long postId) {
       userCenterMapper.deletePost(postId);
    }

    @Override
    public void deleteFavorite(Long userId, Long postId) {
        userCenterMapper.deleteFavorite(userId, postId);
    }

    @Override
    public void deleteComment(Long commentId) {
        userCenterMapper.deleteComment(commentId);
    }

    @Override
    public void unfollowUser(Integer userId, Integer followUserId) {
        // 可以在这里添加额外的业务逻辑，例如检查是否已经关注等
        userCenterMapper.unfollowUser(userId, followUserId);
    }
}

package com.tj_JavaEE.service.impl;

import com.tj_JavaEE.dto.Cmt;
import com.tj_JavaEE.dto.Commentcontent;
import com.tj_JavaEE.entity.Comment;
import com.tj_JavaEE.mapper.CommentMapper;
import com.tj_JavaEE.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class CommentServiceimpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public void addComment(Commentcontent commentcontent ) {
        Comment comment = new Comment();
        comment.setContent(commentcontent.getContent());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.parse(commentcontent.getCommentTime(), formatter);
        comment.setCommenterId((int) commentcontent.getCommenterId());
        comment.setCommentTime(time);
        comment.setPostId(commentcontent.getPostId());
        comment.setDislikeCount(0);
        comment.setLikeCount(0);
        commentMapper.addComment(comment);
    }

    @Override
    public List<Cmt> getCommentsByPostId(long postId) {
        return commentMapper.getCommentsByPostId(postId);
    }



}

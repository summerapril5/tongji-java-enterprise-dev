package com.tj_JavaEE.service;


import com.tj_JavaEE.dto.Commentcontent;
import com.tj_JavaEE.entity.Comment;
import com.tj_JavaEE.dto.Cmt;

import java.util.List;

public interface CommentService {

    public void addComment(Commentcontent commentcontent);

    public List<Cmt> getCommentsByPostId(long postId);
}

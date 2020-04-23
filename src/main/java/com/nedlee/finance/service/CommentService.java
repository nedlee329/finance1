package com.nedlee.finance.service;

import com.nedlee.finance.po.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentByArticleId(Long ArticleId);

    Comment saveComment(Comment comment);

}

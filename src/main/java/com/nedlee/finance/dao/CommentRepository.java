package com.nedlee.finance.dao;

import com.nedlee.finance.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findByArticleIdAndParentCommentNull(Long articleId, Sort sort);

}

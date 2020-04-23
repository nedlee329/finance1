package com.nedlee.finance.service;

import com.nedlee.finance.po.Article;
import com.nedlee.finance.vo.ArticleQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    Article getArticle(Long id);

    Article getAndConvert(Long id);

    Page<Article> listArticle(Pageable pageable, ArticleQuery article);

    Page<Article> listArticle(Pageable pageable);

    Page<Article> listArticle(Long tagId, Pageable pageable);

    Page<Article> listArticle(String query,Pageable pageable);

    List<Article> listRecommendArticleTop(Integer size);

    Article saveArticle(Article article);

    Article updateArticle(Long id,Article article);

    void deleteArticle(Long id);

}

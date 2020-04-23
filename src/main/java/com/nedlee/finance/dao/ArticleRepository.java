package com.nedlee.finance.dao;

import com.nedlee.finance.po.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,Long>, JpaSpecificationExecutor<Article> {

    @Query("select a from Article a where a.recommend = true")
    List<Article> findTop(Pageable pageable);

    //select * from t_article where title like '%内容%'，需要在query中加入百分号
    @Query("select a from Article a where a.title like ?1 or a.content like ?1")
    Page<Article> findByQuery(String query,Pageable pageable);

    @Modifying
    @Query("update Article a set a.views = a.views+1 where a.id = ?1")
    int updateViews(Long id);

}

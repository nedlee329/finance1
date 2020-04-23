package com.nedlee.finance.service;

import com.nedlee.finance.NotFoundException;
import com.nedlee.finance.dao.ArticleRepository;
import com.nedlee.finance.po.Article;
import com.nedlee.finance.po.Type;
import com.nedlee.finance.util.MarkdownUtils;
import com.nedlee.finance.util.MyBeanUtils;
import com.nedlee.finance.vo.ArticleQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article getArticle(Long id) {
        return articleRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Article getAndConvert(Long id) {
        Article article=articleRepository.findById(id).get();
        if(article == null){
            throw new NotFoundException("该文章不存在");
        }
        Article a = new Article();
        BeanUtils.copyProperties(article,a);
        String content = a.getContent();
        a.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        articleRepository.updateViews(id);
        return a;
    }

    @Override
    public Page<Article> listArticle(Pageable pageable, ArticleQuery article) {

        return articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(article.getTitle()) && article.getTitle() !=null){
                    predicates.add(cb.like(root.<String>get("title"),"%"+article.getTitle()+"%"));
                }
                if(article.getTypeId()!= null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"),article.getTypeId()));
                }
                if(article.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"),article.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Article> listArticle(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Page<Article> listArticle(Long tagId, Pageable pageable) {
        return articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                Join join = root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Article> listArticle(String query, Pageable pageable) {
        return articleRepository.findByQuery(query,pageable);
    }

    @Override
    public List<Article> listRecommendArticleTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable = PageRequest.of(0,size,sort);
        return articleRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Article saveArticle(Article article) {

        if (article.getId() == null) {
            article.setCreateTime(new Date());
            article.setUpdateTime(new Date());
            article.setViews(0);
        } else {
            article.setUpdateTime(new Date());
        }
        return articleRepository.save(article);
    }

    @Transactional
    @Override
    public Article updateArticle(Long id, Article article) {

        Article a = articleRepository.findById(id).get();
        if (a == null){
            throw new NotFoundException("该文章不存在！");
        }
        BeanUtils.copyProperties(article,a, MyBeanUtils.getNullPropertyNames(article));
        a.setUpdateTime(new Date());
        return articleRepository.save(a);
    }

    @Transactional
    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}

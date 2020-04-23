package com.nedlee.finance.service;

import com.nedlee.finance.NotFoundException;
import com.nedlee.finance.dao.ProductRepository;
import com.nedlee.finance.po.Customer;
import com.nedlee.finance.po.Product;
import com.nedlee.finance.vo.ProductQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).get();
    }

    @Override
    public Page<Product> listProduct(Pageable pageable, ProductQuery productQuery) {
        return productRepository.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates =new ArrayList<>();
                if(productQuery.getCustomerId() != null){
                    predicates.add(cb.equal(root.<Customer>get("customer").get("id"),productQuery.getCustomerId()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Product> listProduct(Pageable pageable, Product product) {
        return productRepository.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates =new ArrayList<>();
                if(!"".equals(product.getCustomer().getId()) && product.getCustomer() != null){
                    predicates.add(cb.equal(root.<Customer>get("customer").get("id"),product.getCustomer().getId()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Product> listProduct(Long customerId, Pageable pageable) {
        return productRepository.findAll(new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("customer");
                return criteriaBuilder.equal(join.get("id"),customerId);
            }
        },pageable);
    }

    @Override
    public Page<Product> listProduct(Date startTime, Date endTime, Pageable pageable) {
        return productRepository.findByJyTimeBetween(startTime,endTime,pageable);
    }

    @Override
    public Product saveProduct(Product product) {
        product.setJyTime(new Date());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product p = productRepository.findById(id).get();
        if (p == null) {
            throw new NotFoundException("该投资产品不存在！");
        }
        BeanUtils.copyProperties(product,p);
        return productRepository.save(p);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> listProduct(Pageable pageable, Date startTime, Date endTime) {
        return null;
    }
}

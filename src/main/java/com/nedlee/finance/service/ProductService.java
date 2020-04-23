package com.nedlee.finance.service;

import com.nedlee.finance.po.Product;
import com.nedlee.finance.vo.ProductQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface ProductService {

    Product getProduct(Long id);

    Page<Product> listProduct(Pageable pageable, ProductQuery productQuery);

    Page<Product> listProduct(Pageable pageable, Product product);

    Page<Product> listProduct(Long CustomerId,Pageable pageable);

    Page<Product> listProduct(Date startTime,Date endTime,Pageable pageable);

    Product saveProduct(Product product);

    Product updateProduct(Long id,Product product);

    void deleteProduct(Long id);

    Page<Product> listProduct(Pageable pageable, Date startTime,Date endTime);

}

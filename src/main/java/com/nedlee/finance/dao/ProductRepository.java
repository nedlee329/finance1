package com.nedlee.finance.dao;

import com.nedlee.finance.po.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {

    @Query("select a from Product a where a.jyTime between ?1 and ?2")
    Page<Product> findByJyTimeBetween(Date startTime, Date endTime, Pageable pageable);

}

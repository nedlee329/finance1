package com.nedlee.finance.dao;

import com.nedlee.finance.po.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {

    Customer findByUsernameAndPassword(String username, String password);

}

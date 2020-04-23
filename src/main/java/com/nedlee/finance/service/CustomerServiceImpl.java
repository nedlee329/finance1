package com.nedlee.finance.service;

import com.nedlee.finance.dao.CustomerRepository;
import com.nedlee.finance.po.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer checkCustomer(String username, String password) {

        Customer customer = customerRepository.findByUsernameAndPassword(username,password);
        return customer;
    }
}

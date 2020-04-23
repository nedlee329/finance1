package com.nedlee.finance.service;

import com.nedlee.finance.po.Customer;

public interface CustomerService {

    Customer checkCustomer(String username, String password);

}

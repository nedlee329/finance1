package com.nedlee.finance.service;

import com.nedlee.finance.po.User;

public interface UserService {

    User checkUser(String username,String password);

}

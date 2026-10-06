package com.stock_trade.Service.Interface;

import com.stock_trade.Entity.User;

public interface UserService {
    User fingUserById(Integer id);

    User login(String username, String password);
}

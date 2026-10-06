package com.stock_trade.Service.Interface;

import com.stock_trade.Entity.Account;

public interface AccountService {
    Account findByUserId(Long userId);
}

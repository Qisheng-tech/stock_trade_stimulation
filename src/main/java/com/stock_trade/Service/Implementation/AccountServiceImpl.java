package com.stock_trade.Service.Implementation;

import com.stock_trade.Entity.Account;
import com.stock_trade.Mapper.AccountMapper;
import com.stock_trade.Service.Interface.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account findByUserId(Long userId) {
        return accountMapper.findByUserId(userId);
    }
}

package com.stock_trade.Controller;

import com.stock_trade.Entity.Account;
import com.stock_trade.Service.Interface.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/{userId}")
    public Account findByUserId(@PathVariable Long userId) {
        return accountService.findByUserId(userId);
    }
}

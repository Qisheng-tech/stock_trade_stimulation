package com.stock_trade.Controller;

import com.stock_trade.Entity.User;
import com.stock_trade.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {
    //我要使用 UserService 这个服务能力，你帮我找到它的实现类并注入进来。
    //@Service会告诉系统谁能实现
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User fingUserById(@PathVariable Integer id){
        return userService.fingUserById(id);
    }
}

package com.stock_trade.Controller;

import com.stock_trade.Entity.LoginRequest;
import com.stock_trade.Entity.User;
import com.stock_trade.Service.Interface.UserService;
import com.stock_trade.utils.JwyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/Auth")
public class AuthController {
    @Autowired
    private UserService userService;
    private JwyUtil JwtUtil;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {

//        return userService.login(
//                loginRequest.getUsername(),
//                loginRequest.getPassword()
        //加入JWT
        User user = userService.login(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        );

        if (user == null) {
            return "用户名或密码错误";
        }

        return JwtUtil.generateToken(
                user.getId(),
                user.getUsername()
        );

    }
}

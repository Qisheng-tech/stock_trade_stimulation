package com.stock_trade.Service.Implementation;

import com.stock_trade.Entity.User;
import com.stock_trade.Mapper.UserMapper;
import com.stock_trade.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    //获取userMapper对象，在类内直接调用
    @Autowired
    private UserMapper userMapper;

    public User fingUserById(Integer id){
        return userMapper.selectById(id);
    }

    @Override
    public User login(String username, String password) {

        // 1. 根据用户名查询用户
        User user = userMapper.selectByName(username);

        // 2. 用户不存在
        if (user == null) {
            return null;
        }

        // 3. 验证密码
        if (!user.getPassword().equals(password)) {
            return null;
        }
        System.out.println("登录成功");
        // 4. 用户名和密码都正确
        return user;
    }
}

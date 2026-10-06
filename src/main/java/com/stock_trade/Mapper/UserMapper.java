package com.stock_trade.Mapper;

import com.stock_trade.Entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectById(Integer id);
    User selectByName(String name);
}

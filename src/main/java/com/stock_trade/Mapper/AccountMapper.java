package com.stock_trade.Mapper;

import com.stock_trade.Entity.Account;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface AccountMapper {
    @Select("SELECT id, user_id, balance FROM account WHERE user_id = #{userId}")
    Account findByUserId(Long userId);


    //FOR UPDATE处理并发问题，锁住用户账户记录
    //FOR UPDATE 就是数据库层面的行锁
    @Select("""
            SELECT id, user_id, balance
            FROM account
            WHERE user_id = #{userId}
            FOR UPDATE
            """)
    Account findByUserIdForUpdate(Long userId);

    @Update("""
            UPDATE account
            SET balance = balance - #{amount}
            WHERE user_id = #{userId}
            """)
    int decreaseBalance(Long userId, BigDecimal amount);

    @Update("""
            UPDATE account
            SET balance = balance + #{amount}
            WHERE user_id = #{userId}
            """)
    int increaseBalance(Long userId, BigDecimal amount);
}

package com.stock_trade.Mapper;

import com.stock_trade.Entity.TradeOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface TradeOrderMapper {
    @Insert("""
            INSERT INTO trade_order
            (user_id, stock_id, order_type, side,
             quantity, price, status)
            VALUES
            (#{userId}, #{stockId}, #{orderType}, #{side},
             #{quantity}, #{price}, #{status})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(TradeOrder tradeOrder);
}

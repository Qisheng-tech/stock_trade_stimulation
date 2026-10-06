package com.stock_trade.Mapper;

import com.stock_trade.Entity.TradeRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TradeRecordMapper {
    @Insert("""
            INSERT INTO trade_record
            (order_id, user_id, stock_id, side,
             quantity, price, amount)
            VALUES
            (#{orderId}, #{userId}, #{stockId}, #{side},
             #{quantity}, #{price}, #{amount})
            """)
    int insert(TradeRecord tradeRecord);
}

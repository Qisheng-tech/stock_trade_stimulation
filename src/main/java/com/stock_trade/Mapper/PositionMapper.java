package com.stock_trade.Mapper;

import com.stock_trade.Entity.Position;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PositionMapper {
    @Select("""
            SELECT id, user_id, stock_id, quantity,
                   available_quantity, average_cost
            FROM position
            WHERE user_id = #{userId}
            """)
    List<Position> findByUserId(Long userId);

    @Select("""
            SELECT id, user_id, stock_id, quantity,
                   available_quantity, average_cost
            FROM position
            WHERE user_id = #{userId}
              AND stock_id = #{stockId}
            """)
    Position findByUserIdAndStockId(Long userId, Long stockId);

    @Select("""
            SELECT id, user_id, stock_id, quantity,
                   available_quantity, average_cost
            FROM position
            WHERE user_id = #{userId}
              AND stock_id = #{stockId}
            FOR UPDATE
            """)
    Position findByUserIdAndStockIdForUpdate(Long userId, Long stockId);

    @Insert("""
            INSERT INTO position
            (user_id, stock_id, quantity, available_quantity, average_cost)
            VALUES
            (#{userId}, #{stockId}, #{quantity},
             #{availableQuantity}, #{averageCost})
            """)
    int insert(Position position);

    @Update("""
            UPDATE position
            SET quantity = #{quantity},
                available_quantity = #{availableQuantity},
                average_cost = #{averageCost}
            WHERE id = #{id}
            """)
    int update(Position position);
}

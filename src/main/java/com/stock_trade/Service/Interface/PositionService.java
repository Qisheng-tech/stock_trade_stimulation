package com.stock_trade.Service.Interface;

import com.stock_trade.Entity.Position;

import java.util.List;

public interface PositionService {
    List<Position> findByUserId(Long userId);

    Position findByUserIdAndStockId(Long userId, Long stockId);
}

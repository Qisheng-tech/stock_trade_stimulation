package com.stock_trade.Service.Implementation;

import com.stock_trade.Entity.Position;
import com.stock_trade.Mapper.PositionMapper;
import com.stock_trade.Service.Interface.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionMapper positionMapper;

    @Override
    public List<Position> findByUserId(Long userId) {
        return positionMapper.findByUserId(userId);
    }

    @Override
    public Position findByUserIdAndStockId(Long userId, Long stockId) {
        return positionMapper.findByUserIdAndStockId(userId, stockId);
    }
}

package com.stock_trade.Controller;

import com.stock_trade.Entity.Position;
import com.stock_trade.Service.Interface.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/position")
public class PositionController {
    @Autowired
    private PositionService positionService;

    @GetMapping("/{userId}")
    public List<Position> findByUserId(@PathVariable Long userId) {
        return positionService.findByUserId(userId);
    }

    @GetMapping("/{userId}/{stockId}")
    public Position findByUserIdAndStockId(
            @PathVariable Long userId,
            @PathVariable Long stockId) {

        return positionService.findByUserIdAndStockId(userId, stockId);
    }
}

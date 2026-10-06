package com.stock_trade.Controller;


import com.stock_trade.Service.Interface.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/stock")
public class StockPriceController {

    @Autowired
    private StockPriceService stockPriceService;

    @GetMapping("/{stockId}/price")
    public BigDecimal getPrice(@PathVariable Long stockId) {
        return stockPriceService.getPrice(stockId);
    }
}
package com.stock_trade.Controller;

import com.stock_trade.Entity.Stock;
import com.stock_trade.Service.Interface.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping
    public List<Stock> findAll() {
        return stockService.findAll();
    }

    @GetMapping("/{id}")
    public Stock findById(@PathVariable Long id) {
        return stockService.findById(id);
    }
}

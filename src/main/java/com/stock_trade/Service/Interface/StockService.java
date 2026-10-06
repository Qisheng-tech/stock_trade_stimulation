package com.stock_trade.Service.Interface;

import com.stock_trade.Entity.Stock;

import java.util.List;

public interface StockService {
    List<Stock> findAll();

    Stock findById(Long id);
}

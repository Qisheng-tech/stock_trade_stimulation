package com.stock_trade.Service.Implementation;

import com.stock_trade.Entity.Stock;
import com.stock_trade.Mapper.StockMapper;
import com.stock_trade.Service.Interface.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockMapper stockMapper;

    @Override
    public List<Stock> findAll() {
        return stockMapper.findAll();
    }

    @Override
    public Stock findById(Long id) {
        return stockMapper.findById(id);
    }
}

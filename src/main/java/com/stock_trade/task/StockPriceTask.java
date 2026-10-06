package com.stock_trade.task;

import com.stock_trade.Service.Interface.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StockPriceTask {

    @Autowired
    private StockPriceService stockPriceService;

    // @Scheduled令  每秒更新一次股票价格

    @Scheduled(fixedRate = 1000)
    public void updateStockPrices() {

        stockPriceService.updatePrice(1L);
        stockPriceService.updatePrice(2L);
        stockPriceService.updatePrice(3L);
    }
}
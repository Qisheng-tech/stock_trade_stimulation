package com.stock_trade.task;


import com.stock_trade.Service.Interface.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//CommandLineRunner会让boot自动调用
@Component
public class StockPriceInitializer implements CommandLineRunner {

    @Autowired
    private StockPriceService stockPriceService;

    @Override
    public void run(String... args) {

        stockPriceService.setPrice(
                1L,
                new BigDecimal("150.00")
        );

        stockPriceService.setPrice(
                2L,
                new BigDecimal("280.00")
        );

        stockPriceService.setPrice(
                3L,
                new BigDecimal("500.00")
        );
    }
}
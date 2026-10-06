package com.stock_trade.Service.Interface;

import java.math.BigDecimal;

public interface StockPriceService {
    // 设置股票当前价格
    void setPrice(Long stockId, BigDecimal price);

    // 获取股票当前价格
    BigDecimal getPrice(Long stockId);

    // 模拟更新股票价格
    void updatePrice(Long stockId);
}

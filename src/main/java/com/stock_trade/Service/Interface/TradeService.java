package com.stock_trade.Service.Interface;

import com.stock_trade.DtO.TradeRequest;

public interface TradeService {
    void buy(TradeRequest request);

    void sell(TradeRequest request);
}

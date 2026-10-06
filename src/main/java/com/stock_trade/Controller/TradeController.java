package com.stock_trade.Controller;

import com.stock_trade.DtO.TradeRequest;
import com.stock_trade.Service.Interface.TradeService;
import com.stock_trade.common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trade")
public class TradeController {
    @Autowired
    private TradeService tradeService;

    @PostMapping("/buy")
    public Result<Object> buy(@RequestBody TradeRequest request) {

        tradeService.buy(request);

        return Result.success(
                "买入成功",
                null
        );
    }

    @PostMapping("/sell")
    public Result<Object> sell(@RequestBody TradeRequest request) {
        tradeService.sell(request);
        return Result.success(
                "卖出成功",
                null
        );
    }
}

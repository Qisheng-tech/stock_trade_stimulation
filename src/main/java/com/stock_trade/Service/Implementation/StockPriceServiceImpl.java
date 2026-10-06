package com.stock_trade.Service.Implementation;

import com.stock_trade.Service.Interface.StockPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@Service
public class StockPriceServiceImpl implements StockPriceService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final Random random = new Random();

    /**
     * Redis Key 前缀
     */
    private static final String PRICE_KEY_PREFIX = "stock:price:";

    /**
     * 设置股票当前价格
     */
    @Override
    public void setPrice(Long stockId, BigDecimal price) {

        String key = PRICE_KEY_PREFIX + stockId;

        redisTemplate.opsForValue().set(key, price.toPlainString());
    }

    /**
     * 获取股票当前价格
     */
    @Override
    public BigDecimal getPrice(Long stockId) {

        String key = PRICE_KEY_PREFIX + stockId;

        Object value = redisTemplate.opsForValue().get(key);

        if (value == null) {
            return null;
        }

        return new BigDecimal(value.toString());
    }

    /**
     * 模拟股票价格变化
     */
    @Override
    public void updatePrice(Long stockId) {

        BigDecimal currentPrice = getPrice(stockId);

        // 如果 Redis 中没有价格，就不更新
        if (currentPrice == null) {
            return;
        }

        /*
         * 随机产生 -0.5% ~ +0.5% 的涨跌幅
         */
        double changeRate = (random.nextDouble() - 0.5) * 0.01;

        BigDecimal changeRateDecimal =
                BigDecimal.valueOf(changeRate);

        BigDecimal newPrice = currentPrice
                .multiply(BigDecimal.ONE.add(changeRateDecimal))
                .setScale(2, RoundingMode.HALF_UP);

        // 防止价格变成 0 或负数
        if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            newPrice = BigDecimal.valueOf(0.01);
        }

        setPrice(stockId, newPrice);
    }
}

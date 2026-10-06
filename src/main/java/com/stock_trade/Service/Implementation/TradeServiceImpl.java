package com.stock_trade.Service.Implementation;

import com.stock_trade.DtO.TradeRequest;
import com.stock_trade.Entity.Account;
import com.stock_trade.Entity.Position;
import com.stock_trade.Entity.TradeOrder;
import com.stock_trade.Entity.TradeRecord;
import com.stock_trade.Mapper.*;
import com.stock_trade.Service.Interface.StockPriceService;
import com.stock_trade.Service.Interface.TradeService;
import com.stock_trade.common.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockPriceService stockPriceService;

    @Autowired
    private TradeOrderMapper tradeOrderMapper;

    @Autowired
    private TradeRecordMapper tradeRecordMapper;

    @Override
    @Transactional
    //开启事务
    public void buy(TradeRequest request) {
        //Account、Stock、Redis、Position、TradeOrder、TradeRecord 串起来的业务协调者
        // 1. 检查购买数量
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new BusinessException(
                    4002,
                    "购买数量必须大于0"
            );
        }

        // 2. 检查股票是否存在
        if (stockMapper.findById(request.getStockId()) == null) {
            throw new BusinessException(
                    4003,
                    "股票不存在"
            );
        }

        // 3. 从 Redis 获取当前股票价格
        BigDecimal price =
                stockPriceService.getPrice(request.getStockId());

        if (price == null) {
            throw new BusinessException(
                    4004,
                    "当前股票没有实时价格"
            );
        }

        // 4. 计算购买总金额
        BigDecimal amount = price.multiply(
                BigDecimal.valueOf(request.getQuantity())
        );

        // 5. 查询并锁定账户
        //FOR UPDATE 就是数据库层面的行锁
        Account account =
                accountMapper.findByUserIdForUpdate(request.getUserId());

        if (account == null) {
            throw new BusinessException(
                    4005,
                    "账户不存在"
            );
        }

        // 6. 检查余额
        if (account.getBalance().compareTo(amount) < 0) {
            throw new BusinessException(
                    4001,
                    "账户余额不足"
            );
        }

        // 7. 扣除账户余额
        accountMapper.decreaseBalance(
                request.getUserId(),
                amount
        );

        // 8. 查询并锁定持仓
        Position position =
                positionMapper.findByUserIdAndStockIdForUpdate(
                        request.getUserId(),
                        request.getStockId()
                );

        if (position == null) {

            // 9. 第一次购买这只股票
            Position newPosition = new Position();

            newPosition.setUserId(request.getUserId());
            newPosition.setStockId(request.getStockId());
            newPosition.setQuantity(request.getQuantity());
            newPosition.setAvailableQuantity(request.getQuantity());
            newPosition.setAverageCost(price);

            //表中插入数据
            positionMapper.insert(newPosition);

        } else {

            // 10. 原来的持仓数量
            int oldQuantity = position.getQuantity();

            // 11. 原来的平均成本
            BigDecimal oldAverageCost =
                    position.getAverageCost();

            // 12. 新的数量
            int newQuantity =
                    oldQuantity + request.getQuantity();

            // 13. 重新计算平均成本
            BigDecimal oldTotalCost =
                    oldAverageCost.multiply(
                            BigDecimal.valueOf(oldQuantity)
                    );

            BigDecimal newTotalCost =
                    price.multiply(
                            BigDecimal.valueOf(request.getQuantity())
                    );

            BigDecimal newAverageCost =
                    oldTotalCost
                            .add(newTotalCost)
                            .divide(
                                    BigDecimal.valueOf(newQuantity),
                                    4,
                                    java.math.RoundingMode.HALF_UP
                            );

            // 14. 更新 Position
            position.setQuantity(newQuantity);

            position.setAvailableQuantity(
                    position.getAvailableQuantity()
                            + request.getQuantity()
            );

            position.setAverageCost(newAverageCost);

            positionMapper.update(position);
        }

        // 15. 创建订单
        TradeOrder order = new TradeOrder();

        order.setUserId(request.getUserId());
        order.setStockId(request.getStockId());

        // 市价单
        order.setOrderType("MARKET");

        // 买入
        order.setSide("BUY");

        order.setQuantity(request.getQuantity());

        // 实际成交价格就是 Redis 当前价格
        order.setPrice(price);

        // 模拟系统中直接成交
        order.setStatus("FILLED");

        tradeOrderMapper.insert(order);

        // 16. 创建成交记录
        TradeRecord record = new TradeRecord();

        record.setOrderId(order.getId());
        record.setUserId(request.getUserId());
        record.setStockId(request.getStockId());
        record.setSide("BUY");
        record.setQuantity(request.getQuantity());
        record.setPrice(price);
        record.setAmount(amount);

        tradeRecordMapper.insert(record);
    }

    //卖出操作
    @Override
    @Transactional
    public void sell(TradeRequest request) {

        // 1. 检查卖出数量
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new BusinessException(
                    4002,
                    "购买数量必须大于0"
            );
        }

        // 2. 检查股票是否存在
        if (stockMapper.findById(request.getStockId()) == null) {
            throw new BusinessException(
                    4003,
                    "股票不存在"
            );
        }

        // 3. 获取当前股票价格
        BigDecimal price =
                stockPriceService.getPrice(request.getStockId());

        if (price == null) {
            throw new BusinessException(
                    4004,
                    "当前股票没有实时价格"
            );
        }

        // 4. 计算卖出金额
        BigDecimal amount = price.multiply(
                BigDecimal.valueOf(request.getQuantity())
        );

        // 5. 查询并锁定持仓
        Position position =
                positionMapper.findByUserIdAndStockIdForUpdate(
                        request.getUserId(),
                        request.getStockId()
                );

        // 6. 检查是否持有该股票
        if (position == null) {
            throw new BusinessException(
                    4006,
                    "没有该股票的持仓"
            );
        }

        // 7. 检查可卖数量
        if (position.getAvailableQuantity()
                < request.getQuantity()) {
            throw new BusinessException(
                    4007,
                    "可卖股票数量不足"
            );
        }

        // 8. 减少持仓数量
        position.setQuantity(
                position.getQuantity()
                        - request.getQuantity()
        );

        // 9. 减少可用数量
        position.setAvailableQuantity(
                position.getAvailableQuantity()
                        - request.getQuantity()
        );

        // 10. 更新持仓
        positionMapper.update(position);

        // 11. 查询并锁定账户
        Account account =
                accountMapper.findByUserIdForUpdate(
                        request.getUserId()
                );

        if (account == null) {
            throw new BusinessException(
                    4005,
                    "账户不存在"
            );
        }

        // 12. 增加账户余额
        accountMapper.increaseBalance(
                request.getUserId(),
                amount
        );

        // 13. 创建卖出订单
        TradeOrder order = new TradeOrder();

        order.setUserId(request.getUserId());
        order.setStockId(request.getStockId());
        order.setOrderType("MARKET");
        order.setSide("SELL");
        order.setQuantity(request.getQuantity());
        order.setPrice(price);
        order.setStatus("FILLED");

        tradeOrderMapper.insert(order);

        // 14. 创建成交记录
        TradeRecord record = new TradeRecord();

        record.setOrderId(order.getId());
        record.setUserId(request.getUserId());
        record.setStockId(request.getStockId());
        record.setSide("SELL");
        record.setQuantity(request.getQuantity());
        record.setPrice(price);
        record.setAmount(amount);

        tradeRecordMapper.insert(record);
    }

}

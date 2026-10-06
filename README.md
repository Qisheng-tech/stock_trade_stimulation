简介
该项目是一个简易的股票模拟系统：基于SpringBoot，可用于模拟用户账户，股票行情，买入卖出，持仓管理等业务。这是一个后端项目，并不包含前端的代码，开发者主要使用PostMan工具发送请求，并获得回复。
可以重点关注AuthController,TradeController下的逻辑，是此项目核心部分
该项目并非完整，仍有许多细节值得更新升级，欢迎提问与纠错。

技术栈：
Java，SpringBoot， SpringMVC，Mybatis， MySQL，Redis，

目前主要实现的功能：
·  用户账户管理 
·  股票信息查询 
·  实时股票价格模拟 
·  股票买入 
·  股票卖出 
·  持仓管理 
·  交易记录 
·  账户余额管理 
·  Redis 实时行情缓存 
·  事务控制 
·  全局异常处理

系统架构：
整体采用经典三层架构

Controller(接受访问与要求/返回JSON)
StockController：股票信息查询
AccountController：账户信息查询
PositionController：用户持仓查询
StockPriceController：实时股票价格查询
TradeController：股票买入与卖出

Service (核心业务逻辑与事务控制)
这里仅介绍股票的买入/卖出操作，以买入为例。一些简单的查询操作先行省略
用户提交买入请求 
↓ 
检查购买数量是否合法 
↓
检查股票是否存在 
↓ 
获取 Redis 中的当前股票价格 
↓ 
计算交易金额 
↓ 
查询并锁定用户账户 
↓ 
检查账户余额 
↓ 
扣除账户余额 
↓ 
查询并锁定用户持仓 
↓ 
创建 / 更新持仓 
↓ 
创建交易订单 
↓ 
创建交易记录 
↓ 
事务提交
Mapper  (数据库访问与 SQL 操作)
对于交易业务中的账户和持仓查询，还使用了 FOR UPDATE：
SELECT ...
FROM account
WHERE user_id = #{userId}
FOR UPDATE
通过数据库行级锁，在交易事务执行期间锁定对应账户数据，降低并发交易情况下出现数据不一致的风险。


数据库设计

项目实现细节：
全局异常处理：
为了避免在每个 Controller 中重复编写异常处理逻辑，项目设计了统一的全局异常处理机制。通过 BusinessException 表示业务层面的可预期异常，例如账户余额不足、股票不存在、持仓不足、交易数量不合法等；通过 GlobalExceptionHandler 对 Controller 层抛出的异常进行统一捕获，并转换为统一的 JSON 响应结构。

事务与并发控制：
事务控制
股票交易涉及多个数据库操作，例如账户余额变更、持仓更新、交易订单创建以及交易记录创建。如果其中某一步执行失败，而之前的操作已经成功提交，就可能造成账户资金与持仓数据不一致。
因此，项目在交易 Service 层使用 Spring 的 @Transactional 对整个买入和卖出流程进行事务控制。

数据库行级锁
除了事务控制外，项目在涉及账户和持仓的关键查询中使用 SELECT ... FOR UPDATE 获取数据库行级锁。
例如：
SELECT id, user_id, balance
FROM account
WHERE user_id = #{userId}
FOR UPDATE
以及：
SELECT id, user_id, stock_id, quantity,
       available_quantity, average_cost
FROM position
WHERE user_id = #{userId}
  AND stock_id = #{stockId}
FOR UPDATE
在事务执行期间，当前用户对应的账户和持仓数据会被锁定，其他并发事务在修改相同数据时需要等待当前事务完成。




API接口说明

项目演示

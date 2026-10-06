package com.stock_trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//开启定时任务功能
@EnableScheduling
@SpringBootApplication
public class StockTradingSimulationApplication {

	public static void main(String[] args) {

		SpringApplication.run(StockTradingSimulationApplication.class, args);
	}

}

package com.stock_trade.Mapper;

import com.stock_trade.Entity.Stock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockMapper {

    @Select("SELECT * FROM stock")
    List<Stock> findAll();

    @Select("SELECT * FROM stock WHERE id = #{id}")
    Stock findById(Long id);

    @Select("SELECT * FROM stock WHERE name LIKE CONCAT('%', #{name}, '%')")
    Stock findByName(String name);
}


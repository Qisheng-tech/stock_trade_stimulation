package com.stock_trade.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//springframework的全局异常处理
//统一接住 Controller 层向外抛出的异常，把原本杂乱的异常转换成统一、漂亮的 HTTP/JSON 响应
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)  //()中识别异常类型，转换成统一格式。目的：区分多种异常
    public Result<Void> handleBusinessException(
            BusinessException e) {

        return Result.error(
                e.getCode(),
                e.getMessage()
        );
    }

    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e) {

        e.printStackTrace();

        return Result.error(
                5000,
                "服务器内部错误"
        );
    }
}
package com.example.handler;

import com.example.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // 指定出现什么异常 执行什么方法
    @ExceptionHandler({ArithmeticException.class})
    @ResponseBody
    public ResponseResult error(Exception e){
        log.error(e.getMessage());
        e.printStackTrace();
        return ResponseResult.fail("ArithmeticException异常处理");
    }
}

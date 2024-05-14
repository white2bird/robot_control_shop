package com.it.interceptor;

import com.it.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 
 * @date 2024/4/15 11:24
 */
@RestControllerAdvice
public class ExceptionAdviceController {

    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return Result.ok(null, 500, "exception occur");
    }
}

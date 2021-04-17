package com.curry.servicebase.exceptionhandler;

import com.curry.commonutils.ExceptionUtil;
import com.curry.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author curry30
 * @create 2021-03-29 10:23
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //全局异常处理
    //指定什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody//为了返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理......");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//为了返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException处理......");
    }

    //自定义异常处理!!!!!!!!!!!!!!!!!!
    @ExceptionHandler(KuliException.class)
    @ResponseBody//为了返回数据
    public R error(KuliException e){
        log.error(ExceptionUtil.getMessage(e));
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}

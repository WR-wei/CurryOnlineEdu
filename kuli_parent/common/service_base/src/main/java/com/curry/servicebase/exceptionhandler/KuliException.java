package com.curry.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author curry30
 * @create 2021-03-29 11:14
 */
@Data
@AllArgsConstructor
public class KuliException extends RuntimeException {
    private Integer code;   //异常状态码
    private String msg;     //异常信息

}

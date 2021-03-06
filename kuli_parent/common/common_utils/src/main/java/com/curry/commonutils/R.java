package com.curry.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author curry30
 * @create 2021-03-28 13:50
 */
//统一返回结果的类(单例设计模式,链式编程)
@Data
public class R {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();

    //私有构造方法
    private R(){}

    //成功的静态方法
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(resultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    //失败的静态方法
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(resultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}

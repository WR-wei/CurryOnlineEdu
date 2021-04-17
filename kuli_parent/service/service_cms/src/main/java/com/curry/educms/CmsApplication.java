package com.curry.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author curry30
 * @create 2021-04-11 12:00
 */
@SpringBootApplication
@ComponentScan({"com.curry"}) //指定扫描位置
@MapperScan("com.curry.educms.mapper")
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}

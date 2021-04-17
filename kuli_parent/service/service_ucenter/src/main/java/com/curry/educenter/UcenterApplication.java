package com.curry.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author curry30
 * @create 2021-04-12 16:11
 */
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.curry"})
@SpringBootApplication//取消数据源自动配置
@MapperScan("com.curry.educenter.mapper")
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}

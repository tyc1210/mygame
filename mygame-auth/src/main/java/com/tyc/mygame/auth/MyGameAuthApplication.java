package com.tyc.mygame.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-10 10:14:11
 */
@SpringBootApplication
@EnableFeignClients
@MapperScan(basePackages = {"com.tyc.mygame.auth.dao"})
public class MyGameAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyGameAuthApplication.class,args);
    }
}

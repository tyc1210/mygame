package com.tyc.mygame.ucenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 类描述
 *
 * @author tyc
 * @version 1.0
 * @date 2023-04-10 15:03:47
 */
@SpringBootApplication
@EnableFeignClients
@MapperScan(basePackages = {"com.tyc.mygame.ucenter.dao"})
public class UcenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UcenterApplication.class,args);
    }
}

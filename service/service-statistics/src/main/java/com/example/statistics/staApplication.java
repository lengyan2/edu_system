package com.example.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.example.statistics.mapper")
@EnableFeignClients
@EnableSwagger2
@EnableScheduling
public class staApplication {
    public static void main(String[] args) {
        SpringApplication.run(staApplication.class,args);
    }
}

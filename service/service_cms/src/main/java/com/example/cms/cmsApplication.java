package com.example.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = {"com.example.cms"})
@MapperScan("com.example.cms.mapper")
@EnableCaching
public class cmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(cmsApplication.class,args);
    }
}

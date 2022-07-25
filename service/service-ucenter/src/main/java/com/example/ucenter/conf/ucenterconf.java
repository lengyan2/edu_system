package com.example.ucenter.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.ucenter.mapper")
@ComponentScan("com.example")
public class ucenterconf {


}

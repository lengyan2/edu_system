package com.example.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.vod.utils.VodProperties;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableSwagger2
@EnableConfigurationProperties(VodProperties.class)
public class VodApplicationss {
    public static void main(String[] args) {
        SpringApplication.run(VodApplicationss.class,args);
    }
}

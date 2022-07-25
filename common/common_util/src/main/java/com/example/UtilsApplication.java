package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.example.utils.tencentSms;
@SpringBootApplication
@EnableConfigurationProperties(value = tencentSms.class)
public class UtilsApplication {


}

package com.example.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "tencent")
public class tencentSms {
    private String secretId;
    private String secretKey;
}

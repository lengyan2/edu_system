package com.example.oss.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class ConstantProperties implements InitializingBean {

//    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;

//    @Value("${aliyun.oss.file.accessKeyId}")
    private String accessKeyId;

//    @Value("${aliyun.oss.file.accessKeySecret}")
    private String accessKeySecret;

//    @Value(("${aliyun.oss.file.bucketname}"))
    private String bucketname;

    // 定义公开静态常量
    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("xxxxxxxxxxxxxxx");
        END_POINT = endpoint;
        ACCESS_KEY_ID = accessKeyId;
        ACCESS_KEY_SECRET = accessKeySecret;
        BUCKET_NAME = bucketname;
    }
}

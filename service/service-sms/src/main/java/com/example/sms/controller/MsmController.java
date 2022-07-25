package com.example.sms.controller;

import com.alibaba.nacos.client.naming.utils.RandomUtils;
import com.example.GetRandom;
import com.example.ResponseResult;
import com.example.sms.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
@RequestMapping("api/msm")
public class MsmController {

    @Autowired
    MsmService msmService;

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    // 发送短信 通过电话好吗
    @GetMapping("send/{phone}")
    public ResponseResult sendMsm(@PathVariable("phone") String phone){
        System.out.println(phone);
        Object code =  redisTemplate.opsForValue().get(phone) ;
        System.out.println(code);
        if (code==null){
            code = "";
        }else {
            return  ResponseResult.success();
        }
        code = GetRandom.getFourRandom();
        boolean send = msmService.send(phone, code.toString());
        if (send){
            redisTemplate.opsForValue().set(phone,code.toString(),5, TimeUnit.MINUTES);
            return ResponseResult.success();
        }
        return ResponseResult.fail("发送短信失败");
    }

}

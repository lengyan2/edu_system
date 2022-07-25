package com.example.statistics.client;

import com.example.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface ucenteclient {

    @GetMapping("/ucenter/member/countRegister/{day}")
    public ResponseResult countRegister(@PathVariable("day") String day);
}

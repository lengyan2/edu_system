package com.example.order.clinet;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-ucenter")
public interface ucenterClient {

    @GetMapping("/ucenter/member/getUserInfoOrder/{memberId}")
    public com.example.Member getUserInfoOreder(@PathVariable("memberId")String memberId);
}

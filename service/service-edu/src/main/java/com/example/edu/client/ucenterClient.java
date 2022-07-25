package com.example.edu.client;

import com.example.Member;
import com.example.ResponseResult;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@FeignClient("service-ucenter")
public interface ucenterClient {
    @GetMapping("/ucenter/member/getCommentMember")
    Member getCommentMember(@RequestHeader("token") String token);
}

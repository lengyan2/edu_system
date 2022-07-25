package com.example.edu.controller;

import com.example.ResponseResult;
import com.example.edu.entity.User;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@RestController
@RequestMapping("/eduservice/user")
@CrossOrigin
public class LoginController {
    @PostMapping("login")
    public ResponseResult login(){
        HashMap<String, Object> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("token","admin");
        stringStringHashMap.put("status",20000);
        return ResponseResult.success(stringStringHashMap);
    }


    @GetMapping("info")
    public ResponseResult info(){
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("roles","[admin]");
        stringStringHashMap.put("name","admin");
        stringStringHashMap.put("avatar","hhhts");
        return ResponseResult.success(stringStringHashMap);
    }
}

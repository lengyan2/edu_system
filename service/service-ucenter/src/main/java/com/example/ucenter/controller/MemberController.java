package com.example.ucenter.controller;


import com.example.JwtUtils;
import com.example.ResponseResult;
import com.example.ucenter.entity.Member;
import com.example.ucenter.entity.vo.memberVo;
import com.example.ucenter.entity.vo.registerVo;
import com.example.ucenter.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-19
 */
@RestController
@RequestMapping("/ucenter/member")
@CrossOrigin
public class MemberController {
    @Autowired
    MemberService memberService;

    // 登录
    @PostMapping("login")
    public ResponseResult login(@RequestBody memberVo memberVo){
    // 调用service方法实现登录

        // 返回token值 jwt
        String token = memberService.login(memberVo);
        System.out.println("token="+token);
        Map<String,String> map = new HashMap<>();
        map.put("token",token);
        return ResponseResult.success(map);
    }

    // 注册
        @PostMapping("register")
    public ResponseResult register(@RequestBody registerVo registerVo){
        memberService.register(registerVo);
        return ResponseResult.success();
        }

        // 根据token获取用户信息
    @GetMapping("getMemberInfo")
    public ResponseResult getMemmberInfo(HttpServletRequest request){
        // 调用jwt类对象 根据request返回id信息
        String id = JwtUtils.getMemberIdByJwtToken(request);
        // 根据用户id查询 用户信息
        Member byId = memberService.getById(id);
        System.out.println(byId);
        HashMap<String, Member> stringMemberHashMap = new HashMap<>();
        stringMemberHashMap.put("userInfo",byId);
        return ResponseResult.success(stringMemberHashMap);
    }

    @GetMapping(value = "getCommentMember")
    public Member getCommentMember(@RequestHeader("token")String token){
        String id = JwtUtils.getMemberIdByJwtToken(token);
        Member member = memberService.getById(id);
        return member;
    }

    //根据用户id查询用户信息
    @GetMapping("getUserInfoOrder/{memberId}")
    public com.example.Member getUserInfoOreder(@PathVariable("memberId")String memberId){
        Member  byId = memberService.getById(memberId);
        //
        com.example.Member member = new com.example.Member();
        BeanUtils.copyProperties(byId,member);
        return member;
    }

    // 查询某一天的注册人数
    @GetMapping("countRegister/{day}")
    public ResponseResult countRegister(@PathVariable("day") String day){
      Integer count =  memberService.countRegisterDay(day);
      return ResponseResult.success(count);
    }

}


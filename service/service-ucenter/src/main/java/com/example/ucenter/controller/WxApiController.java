package com.example.ucenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.JwtUtils;
import com.example.ucenter.entity.Member;
import com.example.ucenter.service.MemberService;
import com.example.ucenter.utils.COnstantWxUtils;
import com.example.ucenter.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@Controller
@RequestMapping("ucenter/wx")
@CrossOrigin
public class WxApiController {
    @Autowired
    MemberService memberService;
    // 生成微信二维码
    @GetMapping("login")
    public String getWxCode(){
        //  请求微信的地址
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
        "?appid=%s" +
        "&redirect_uri=%s" +
        "&response_type=code" +
        "&scope=snsapi_login" +
        "&state=%s" +
        "#wechat_redirect";
        String redirectUrl = COnstantWxUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException();
        }
        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("state = " + state);
        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟
        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                COnstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                state);
        return "redirect:"+qrcodeUrl;
    }
    @GetMapping("callback")
    public String callback(String code ,String state){
        try {

            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";

            // 拼接参数
            String accessTokenUrl = String.format(baseAccessTokenUrl, COnstantWxUtils.WX_OPEN_APP_ID, COnstantWxUtils.WX_OPEN_APP_SECRET, code);
            // 获取acesstoken 和openid；
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            // 转换为map集合
            Gson gson = new Gson();
            HashMap hashMap = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token =(String) hashMap.get("access_token");
            String openid = (String) hashMap.get("openid");

            // 访问微信的资源服务器，获取用户信息

            // 扫码人信息添加到数据库 中 openid nickname iamgeurl
             // 判断是否存在微信人信息 存在不添加
         Member member =  memberService.getOpenIdMember(openid);
         if (member == null){
             String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                     "?access_token=%s" +
                     "&openid=%s";
             String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
             String userInfo = HttpClientUtils.get(userInfoUrl);
             // 获取返回的userInfo的信息
             HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
             String  nickname = (String) userInfoMap.get("nickname");
             String headimgurl = (String) userInfoMap.get("headimgurl");
             member = new Member();
             member.setOpenid(openid);
             member.setNickname(nickname);
             member.setAvatar(headimgurl);
             memberService.save(member);
             System.out.println(member);
         }
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            return "redirect:http://localhost:3000?token="+jwtToken;

        }catch (Exception e){
            e.printStackTrace();
        }

        return "redirect:http://localhost:3000";
    }
}

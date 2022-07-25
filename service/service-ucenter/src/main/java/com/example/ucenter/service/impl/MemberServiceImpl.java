package com.example.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.JwtUtils;
import com.example.MD5;
import com.example.ucenter.entity.Member;
import com.example.ucenter.entity.vo.memberVo;
import com.example.ucenter.entity.vo.registerVo;
import com.example.ucenter.mapper.MemberMapper;
import com.example.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-19
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    @Override
    public String login(memberVo memberVo) {
        String mobile = memberVo.getMobile();
        String password = memberVo.getPassword();
        if (!StringUtils.hasText(mobile) || !StringUtils.hasText(password)){
            throw   new RuntimeException("密码和手机号不能为空");
        }
        //判断手机号是否正确
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mobile",mobile);
        Member one = this.getOne(memberQueryWrapper);
        if (one==null){
            throw  new RuntimeException("登录失败 手机号不存在");
        }
        //
        if (!MD5.encrypt(password).equals(one.getPassword())){
            throw new RuntimeException("密码错误");
        }
        // 判断用户是否禁用
        if (one.getIsDisabled()){
            throw new RuntimeException("账号被禁用");
        }
        String jwtToken = JwtUtils.getJwtToken(one.getId(), one.getNickname());
        return jwtToken;
    }

    @Override
    public void register(registerVo registerVo) {
        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        // 判断这些字段是否为空
        if (!StringUtils.hasText(nickname)||!StringUtils.hasText(mobile)||!StringUtils.hasText(password)||!StringUtils.hasText(code)){
            throw new RuntimeException("字段不能为空 ");
        }
        //判断手机号是否注册
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("mobile",mobile);
        Member one = this.getOne(memberQueryWrapper);
        if (one==null){
            // 判断手机号与验证码是否正确
            String sendcode =  this.redisTemplate.opsForValue().get(mobile);
            if (!sendcode.equals(code)){
                throw new RuntimeException("验证码错误");
            }
            Member member = new Member();
            member.setMobile(mobile);
            member.setNickname(nickname);
            member.setPassword(MD5.encrypt(password));
            member.setIsDisabled(false);
            member.setAvatar("\n" +
                    "https://edu-10-test.oss-cn-guangzhou.aliyuncs.com/2022/07/16/e4863ccec1dc47a1bc7d36b945f664d9file.png");
            this.save(member);
        }
        else throw new RuntimeException("用户已注册");
    }

    @Override
    public Member getOpenIdMember(String openid) {
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("openid",openid);
        Member one = this.getOne(memberQueryWrapper);
        return one;
    }

    @Override
    public Integer countRegisterDay(String day) {
      Integer count =   baseMapper.countRegisterDay(day);
      return count;
    }
}

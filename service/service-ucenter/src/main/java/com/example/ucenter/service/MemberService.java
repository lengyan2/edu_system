package com.example.ucenter.service;

import com.example.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ucenter.entity.vo.memberVo;
import com.example.ucenter.entity.vo.registerVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-19
 */
public interface MemberService extends IService<Member> {

    String login(memberVo memberVo);

    void register(registerVo registerVo);

    Member getOpenIdMember(String openid);

    Integer countRegisterDay(String day);
}

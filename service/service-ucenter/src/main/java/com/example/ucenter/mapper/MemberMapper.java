package com.example.ucenter.mapper;

import com.example.ucenter.entity.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-19
 */
public interface MemberMapper extends BaseMapper<Member> {

    Integer countRegisterDay(String day);
}

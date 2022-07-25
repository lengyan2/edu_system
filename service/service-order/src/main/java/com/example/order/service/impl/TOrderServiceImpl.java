package com.example.order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.example.Member;
import com.example.courseOrder;
import com.example.order.clinet.courseClient;
import com.example.order.clinet.ucenterClient;
import com.example.order.entity.TOrder;
import com.example.order.mapper.TOrderMapper;
import com.example.order.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-21
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {
    @Autowired
    courseClient courseClient;

    @Autowired
    ucenterClient ucenterClient;

    @Override
    public String createOrders(String courseId, String id) {
        // 1。生成订单 远程调用根据用户id获取用户信息

        // 远程调用根据课程id获取课程信息
        courseOrder courseById = courseClient.getOrderCourseById(courseId);
        Member userInfoOreder = ucenterClient.getUserInfoOreder(id);
        TOrder tOrder = new TOrder();
        tOrder.setOrderNo(IdWorker.getIdStr()); //订单号
        tOrder.setMemberId(userInfoOreder.getId());
        tOrder.setMobile(userInfoOreder.getMobile());
        tOrder.setNickname(userInfoOreder.getNickname());
        tOrder.setStatus(0);
        tOrder.setPayType(1);
        tOrder.setCourseTitle(courseById.getTitle());
        tOrder.setCourseId(courseById.getId());
        tOrder.setCourseCover(courseById.getCover());
        tOrder.setTeacherName("test");
        this.save(tOrder);

        return tOrder.getOrderNo();
    }
}

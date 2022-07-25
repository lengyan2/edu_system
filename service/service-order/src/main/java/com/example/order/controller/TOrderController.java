package com.example.order.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.JwtUtils;
import com.example.ResponseResult;
import com.example.order.entity.TOrder;
import com.example.order.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-21
 */
@RestController
@CrossOrigin
@RequestMapping("/order/t-order")
public class TOrderController {
    @Autowired
    @Qualifier("TOrderServiceImpl")
    TOrderService tOrderService;

    // 生成订单
    @PostMapping("createOrder/{courseId}")
    public ResponseResult createOrder(@PathVariable("courseId")String courseId,@RequestHeader("token") String token){
        String id = JwtUtils.getMemberIdByJwtToken(token);
        // 创建订单 返回订单号
      String orderNo   = tOrderService.createOrders(courseId,id);
        HashMap<String, String> map = new HashMap<>();
        map.put("orderNo",orderNo);
        return ResponseResult.success(map);
    }
    // 根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public ResponseResult getOrderInfo(@PathVariable("orderId") String orderId){
        TOrder orderInfo = tOrderService.getOne(new QueryWrapper<TOrder>().eq("order_no", orderId));
        Map<String,TOrder> map = new HashMap<>();
        map.put("orderInfo",orderInfo);
        return ResponseResult.success(map);
    }

    // 根据课程id 和用户id查询课程的购买状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId,@PathVariable("memberId") String memberId){
        QueryWrapper<TOrder> tOrderQueryWrapper = new QueryWrapper<>();
        tOrderQueryWrapper.eq("course_id",courseId);
        tOrderQueryWrapper.eq("member_id",memberId);
        tOrderQueryWrapper.eq("status",1);
        int count = tOrderService.count(tOrderQueryWrapper);
        if (count>0){
            return true;
        }
        else return false;


    }

}


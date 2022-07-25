package com.example.order.controller;


import com.example.ResponseResult;
import com.example.order.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-21
 */
@RestController
@RequestMapping("/order/t-pay-log")
@CrossOrigin
public class TPayLogController {
    @Autowired
    @Qualifier("TPayLogServiceImpl")
    TPayLogService tPayLogService;


    // 生成微信支付二维码
    @GetMapping("createNative/{orderNo}")
    public ResponseResult createNative(@PathVariable("orderNo") String orderNo){
       // 返回二维码地址和其他信息
       Map<String,Object> map =  tPayLogService.createNative(orderNo);
        System.out.println("生成二维码集合"+map);
        return ResponseResult.success(map);
    }
    // 查询支付状态
    @GetMapping("queryPayStatus/{orderNo}")
    public ResponseResult queryPayStatus(@PathVariable("orderNo") String orderNo){
        Map<String,String> map =  tPayLogService.queryPayStatus(orderNo);
        if (map==null){
            ResponseResult.fail("支付出错");
        }
        if (map.get("trade_state").equals("SUCCESS")){
            // 添加记录到支付表 更新订单状态
            tPayLogService.updateOrderStatus(map);

            return ResponseResult.success("支付成功");
        }
        return ResponseResult.success("支付中");
    }

}


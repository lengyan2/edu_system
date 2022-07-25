package com.example.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.order.entity.TOrder;
import com.example.order.entity.TPayLog;
import com.example.order.mapper.TPayLogMapper;
import com.example.order.service.TOrderService;
import com.example.order.service.TPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order.utils.HttpClient;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-21
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Autowired
    @Qualifier("TOrderServiceImpl")
    TOrderService tOrderService;
    @Override
    public Map<String, Object> createNative(String orderNo) {
        try {
            //1 根据订单号查询订单信息
            QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no",orderNo);
            TOrder order = tOrderService.getOne(wrapper);

            //2 使用map设置生成二维码需要参数
            Map<String,String> m = new HashMap();
            m.put("appid","wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle()); //课程标题
            m.put("out_trade_no", orderNo); //订单号
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
            m.put("trade_type", "NATIVE");

            //3 发送httpclient请求，传递参数xml格式，微信支付提供的固定的地址
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            //执行post请求发送
            client.post();

            //4 得到发送请求返回结果
            //返回内容，是使用xml格式返回
            String xml = client.getContent();

            //把xml格式转换map集合，把map集合返回
            Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);

            //最终返回数据 的封装
            Map<String,Object> map = new HashMap();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
            map.put("code_url", resultMap.get("code_url"));        //二维码地址

            return map;
        }catch(Exception e) {
            throw new  RuntimeException();
        }
    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        // 查询微信支付的状态
        try {
            //1、封装参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2 发送httpclient
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            //3 得到请求返回内容
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //6、转成Map再返回
            return resultMap;
        }catch(Exception e) {
            return null;
        }
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        System.out.println(map);
        String orderNo = map.get("out_trade_no");
        QueryWrapper<TOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_no",orderNo);
        // 更新订单表里的订单的状态 1 为已支付
        TOrder order = tOrderService.getOne(queryWrapper);
        if (order.getStatus().intValue() == 1){return;}
        order.setStatus(1);
        tOrderService.updateById(order);
        // 向支付表中添加支付记录】
        TPayLog tPayLog = new TPayLog();
        tPayLog.setOrderNo(orderNo); //订单号
        tPayLog.setPayTime(new Date()); // 支付时间
        tPayLog.setPayType(1); //支付类型
        tPayLog.setTotalFee(order.getTotalFee()); //支付金额
        tPayLog.setTradeState(map.get("trade_state")); // 支付状态
        tPayLog.setAttr(JSONObject.toJSONString(map)); //支付的属性值
        this.save(tPayLog);

    }
}

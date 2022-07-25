package com.example.order.service;

import com.example.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-21
 */
public interface TOrderService extends IService<TOrder> {

    String createOrders(String courseId, String id);
}

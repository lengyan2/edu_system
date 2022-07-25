package com.example.statistics.service;

import com.example.statistics.entity.Daily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-24
 */
public interface DailyService extends IService<Daily> {

    Integer registerCount(String day);

    Map<String, Object> getShowData(String type, String begin, String end);
}

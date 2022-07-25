package com.example.statistics.controller;


import com.example.ResponseResult;
import com.example.statistics.service.DailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-24
 */
@RestController
@RequestMapping("/statistics/daily")
@CrossOrigin
public class DailyController {

    @Autowired
    DailyService dailyService;
    // 统计某一天的注册人数
    @PostMapping("registerCount/{day}")
    public ResponseResult registerCount(@PathVariable("day") String day){
        Integer count =  dailyService.registerCount(day);
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("countRegister",count);
        return ResponseResult.success(hashMap);
    }


    // 图标显示 日期json  数量json
    @GetMapping("showData/{type}/{begin}/{end}")
    public ResponseResult showData(@PathVariable("type") String type,@PathVariable("begin") String begin,
                                   @PathVariable("end") String end){
     Map<String,Object> map =   dailyService.getShowData(type,begin,end);

        return  ResponseResult.success(map);
    }
}


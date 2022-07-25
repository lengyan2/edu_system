package com.example.statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.ResponseResult;
import com.example.statistics.client.ucenteclient;
import com.example.statistics.entity.Daily;
import com.example.statistics.mapper.DailyMapper;
import com.example.statistics.service.DailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-24
 */
@Service
public class DailyServiceImpl extends ServiceImpl<DailyMapper, Daily> implements DailyService {

    @Autowired
    ucenteclient ucenteclient;

    @Override
    public Integer registerCount(String day) {
        //添加记录之前删除表中相同的数据
        QueryWrapper<Daily> dailyQueryWrapper = new QueryWrapper<>();
        dailyQueryWrapper.eq("date_calculated",day);
        baseMapper.delete(dailyQueryWrapper);
        ResponseResult responseResult = ucenteclient.countRegister(day);
        Integer countRegister = (Integer) responseResult.getData();
        Daily daily = new Daily();
        daily.setRegisterNum(countRegister);
        daily.setDateCalculated(day); //    统计日期
        daily.setVideoViewNum(RandomUtils.nextInt(100,200));
        daily.setLoginNum(RandomUtils.nextInt(100,200));
        daily.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(daily);
        return countRegister;
    }

    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        QueryWrapper<Daily> dailyQueryWrapper = new QueryWrapper<>();
        dailyQueryWrapper.between("date_calculated",begin,end);
        dailyQueryWrapper.select("date_calculated",type);
        List<Daily> dailies = baseMapper.selectList(dailyQueryWrapper);
        List<String> dayList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();
        dailies.forEach(daily -> {
            dayList.add(daily.getDateCalculated());
            //  数量
            switch (type){
                case "login_num":numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num": numDataList.add(daily.getCourseNum());
                    break;
                default:break;
            }
        });
        Map<String,Object> map = new HashMap<>();
        map.put("date_calculatedList",dayList);
        map.put("numDataList",numDataList);
        return map;
    }
}

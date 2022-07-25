package com.example.statistics.schedule;

import com.example.statistics.service.DailyService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ScheduleTask {

    @Autowired
    DailyService dailyService   ;
    // 每隔五秒执行一次
//    @Scheduled(cron = "*/5 * * * * ?")
//    public  void task1(){
//        System.out.println("task1允许了======");
//    }

    //每天一点执行
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2(){
        //当前日期的前一天
        LocalDate date = LocalDate.now().plusDays(-1);
        dailyService.registerCount(String.valueOf(date));
    }

    public static void main(String[] args) {
        LocalDate date = LocalDate.now().plusDays(-1);
        System.out.println(String.valueOf(date));
    }
}

package com.example.order.clinet;

import com.example.courseOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-edu")
public interface courseClient {

    @GetMapping("/eduservice/courseFront/getOrderCourseById/{courseId}")
     courseOrder getOrderCourseById(@PathVariable("courseId") String courseId);
}

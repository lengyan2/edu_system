package com.example.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.ResponseResult;
import com.example.edu.entity.Course;
import com.example.edu.entity.Teacher;
import com.example.edu.service.CourseService;
import com.example.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("eduservice/indexfront")
public class indexFrontController {
    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;

    @GetMapping("index")
    public ResponseResult index(){
        // 查询前八热门课程
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.orderByDesc("id").last("limit 8");
        List<Course> courseList = courseService.list(courseQueryWrapper);
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.orderByDesc("id").last("limit 4");
        List<Teacher> teacherList = teacherService.list(teacherQueryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("courseList",courseList);
        map.put("teacherList",teacherList);
        return ResponseResult.success(map);
    }

}

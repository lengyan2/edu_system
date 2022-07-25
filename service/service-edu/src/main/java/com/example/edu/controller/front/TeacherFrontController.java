package com.example.edu.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ResponseResult;
import com.example.edu.entity.Teacher;
import com.example.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    TeacherService teacherService;

    // 分页查询讲师
    @GetMapping("getTeacherFrontList")
    public ResponseResult getTeacherFrontList(@RequestParam("page") Integer page,@RequestParam("limit") Integer limit){
        Page<Teacher> teacherPage = new Page<>(page,limit);
        Map<String,Object> map =  teacherService.getTeacherList(teacherPage);
        // 返回分页的所有数据
        return  ResponseResult.success(map);
    }

    // 通过 id查询特定讲师的信息
    @GetMapping("{id}")
    public ResponseResult getTeacherById(@PathVariable("id") String id){
        Map<String, Object> teacherInfoAndCourse = teacherService.getTeacherInfoAndCourse(id);

        return ResponseResult.success(teacherInfoAndCourse);

    }
}

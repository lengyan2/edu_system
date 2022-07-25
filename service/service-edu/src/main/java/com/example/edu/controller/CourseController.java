package com.example.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ResponseResult;
import com.example.edu.client.VodClient;
import com.example.edu.entity.Course;
import com.example.edu.entity.vo.CoursePublishVo;
import com.example.edu.entity.vo.courseInfoVo;
import com.example.edu.entity.vo.courseVo;
import com.example.edu.service.CourseService;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-15
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class CourseController {

    @Autowired
    CourseService courseService;


    @PostMapping("addCourseInfo")
    public ResponseResult addCourseInfo(@RequestBody courseInfoVo courseInfoVo){
        // 返回添加课程的id值
        String id = courseService.saveCourseInfo(courseInfoVo);
        HashMap<String, String> map = new HashMap<>();
        map.put("courseId",id);
        return ResponseResult.success(map);
    }

     // 根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public ResponseResult getCourseById(@PathVariable("courseId") String  courseId){
        courseInfoVo course = courseService.getCourseInfo(courseId);
        return ResponseResult.success(course);
    }

    //  修改课程基本信息
    @PostMapping("updateCourseInfo")
    public ResponseResult updateCouserInfo(@RequestBody courseInfoVo courseInfoVo){
        courseService.updateCouserInfo(courseInfoVo);
        return ResponseResult.success();
    }
    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public ResponseResult getPublishCourseInfo(@PathVariable("id") String id){
     CoursePublishVo coursePublishVo =  courseService.publishCourseInfo(id);
     return ResponseResult.success(coursePublishVo);
    }
    // 课程最终发布
    @PostMapping("publishCourse/{courseId}")
    public ResponseResult publishCoures(@PathVariable("courseId") String courseId){
        Course course = new Course();
        course.setId(courseId);
        course.setStatus("Normal");
        courseService.updateById(course);
        return ResponseResult.success("发布成功");
    }

    //带分页查询的获取课程列表
    @PostMapping("/CourseList")
    public ResponseResult getCourseList(@RequestParam("page") Integer page,
                                        @RequestParam("limit") Integer limit,
                                        @RequestBody(required = false) courseVo courseVo){
        Page<Course> coursePage = new Page<>(page,limit);
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        if (courseVo!=null){
        if (courseVo.getTitle()!=null&&courseVo.getTitle().length()>0){
            courseQueryWrapper.eq("title",courseVo.getTitle());
        }
        if (courseVo.getStatus()!=null&&courseVo.getStatus().length()>0){
            courseQueryWrapper.eq("status",courseVo.getStatus());
        }}
        IPage<Course> page1 = courseService.page(coursePage, courseQueryWrapper);
        long total = page1.getTotal();
        List<Course> courseList = page1.getRecords();
        HashMap<String, Object> map = new HashMap<>();
        map.put("total",total);
        map.put("courseList",courseList);
        return ResponseResult.success(map);
    }

    // 根据课程id删除课程
    @DeleteMapping("{courseId}")
    public ResponseResult deleteCourse(@PathVariable("courseId") String courseId){

        courseService.deleteCourse(courseId);
        return ResponseResult.success();
    }

}


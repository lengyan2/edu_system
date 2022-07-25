package com.example.edu.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.JwtUtils;
import com.example.ResponseResult;
import com.example.courseOrder;
import com.example.edu.client.orderclient;
import com.example.edu.entity.Course;
import com.example.edu.entity.chapter.ChapterVo;
import com.example.edu.entity.vo.CoursewebVo;
import com.example.edu.entity.vo.courseFrontVo;
import com.example.edu.service.ChapterService;
import com.example.edu.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("eduservice/courseFront")
public class CourseFrontController {

    @Autowired
    CourseService courseService;

    @Autowired
    ChapterService chapterService;

    @Autowired
    orderclient orderclient;
     //  带分页的条件查询课程
    @PostMapping("getCourse")
    public ResponseResult getCourse(@RequestParam(value = "page",required = false) Integer page, @RequestParam(value = "limit",required = false) Integer limit , @RequestBody(required = false) courseFrontVo courseFrontVo){
        Page<Course> coursePage = new Page<>(page, limit);
      Map<String,Object> map =  courseService.getCourseFrontList(coursePage,courseFrontVo);
        return ResponseResult.success(map);
    }

    //根据课程id
    @GetMapping("{courseId}")
    public ResponseResult getCourseById(@PathVariable("courseId") String courseId,HttpServletRequest request){
         CoursewebVo coursewebVo =  courseService.getBaseCourseInfo(courseId);
            // 根据课程id查章节和小节
        List<ChapterVo> chapterVideoByCourseId = chapterService.getChapterVideoByCourseId(courseId);
        // 根据课程id和用户id查询课程是否已购买
        System.out.println(request.getHeader("token "));
        String memberid = JwtUtils.getMemberIdByJwtToken(request);
//        System.out.println("memberId"+memberid);
        boolean buyCourse = orderclient.isBuyCourse(courseId, memberid);
        Map<String,Object> map = new HashMap<>();
        map.put("coursewebVo",coursewebVo);
        map.put("chapterVideoByCourseId",chapterVideoByCourseId);
        map.put("isBuy",buyCourse);
        return ResponseResult.success(map);
    }
    // 根据课程ID 查询课程信息
    @GetMapping("getOrderCourseById/{courseId}")
    public courseOrder getOrderCourseById(@PathVariable("courseId") String courseId){
        Course course = courseService.getById(courseId);
        courseOrder courseOrder = new courseOrder();
        BeanUtils.copyProperties(course,courseOrder);
        return courseOrder;

    }
}

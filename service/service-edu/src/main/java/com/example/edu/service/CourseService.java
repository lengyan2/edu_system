package com.example.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.edu.entity.vo.CoursePublishVo;
import com.example.edu.entity.vo.CoursewebVo;
import com.example.edu.entity.vo.courseFrontVo;
import com.example.edu.entity.vo.courseInfoVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-15
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(courseInfoVo courseInfoVo);

    void updateCouserInfo(courseInfoVo courseInfoVo);
    courseInfoVo getCourseInfo(String couserId);

    CoursePublishVo publishCourseInfo(String id);

    void deleteCourse(String courseId);

    Map<String, Object> getCourseFrontList(Page<Course> coursePage, courseFrontVo courseFrontVo);

    CoursewebVo getBaseCourseInfo(String courseId);
}

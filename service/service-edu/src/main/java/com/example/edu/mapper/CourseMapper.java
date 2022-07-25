package com.example.edu.mapper;

import com.example.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.edu.entity.vo.CoursePublishVo;
import com.example.edu.entity.vo.CoursewebVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-15
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    // 根据课程id返回课程基本信息 CoursePublishVo
    public CoursePublishVo getPublishCourseInfo(String CourserId);

    public CoursewebVo getCourseWebInfo(String courseId);

    //根据课程id返回课程信息 前端显示
    
}

package com.example.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.edu.entity.Course;
import com.example.edu.entity.CourseDescription;
import com.example.edu.entity.Video;
import com.example.edu.entity.vo.CoursePublishVo;
import com.example.edu.entity.vo.CoursewebVo;
import com.example.edu.entity.vo.courseFrontVo;
import com.example.edu.entity.vo.courseInfoVo;
import com.example.edu.mapper.CourseMapper;
import com.example.edu.service.ChapterService;
import com.example.edu.service.CourseDescriptionService;
import com.example.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-15
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    CourseDescriptionService courseDescriptionService;

    @Autowired
    ChapterService chapterService;

    @Autowired
    VideoService videoService;
    // 添加课程信息
    @Override
    public String saveCourseInfo(courseInfoVo courseInfoVo) {
        //  课程表传入数据  courseInfoVo 转换为Course
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo,course);
        this.save(course);
        System.out.println(course.getId());
        // 描述传入课程简介 - 获取添加之后的课程id
        String id = course.getId();
        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfoVo,courseDescription);
        courseDescription.setId(id);
        courseDescriptionService.save(courseDescription);
        return id;
    }

    @Override
    public void updateCouserInfo(courseInfoVo courseInfoVo) {
        Course course = new Course();
        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfoVo,course);
        BeanUtils.copyProperties(courseInfoVo,courseDescription);
        this.updateById(course);
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public courseInfoVo getCourseInfo(String couserId) {
        Course course = this.getById(couserId);
        CourseDescription courseDescription = courseDescriptionService.getById(couserId);
        courseInfoVo courseInfoVo = new courseInfoVo();
        BeanUtils.copyProperties(course,courseInfoVo);
        BeanUtils.copyProperties(courseDescription,courseInfoVo);
        System.out.println(courseInfoVo);
        return courseInfoVo;
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    public void deleteCourse(String courseId) {

        // 删除小节
        videoService.deleteVideoByCourseId(courseId);
        // 删除章节
        chapterService.deleteChapterByCourseId(courseId);
        // 删除描述
        courseDescriptionService.removeById(courseId);
        // 删除课程
        this.removeById(courseId);

    }

    @Override
    public Map<String, Object> getCourseFrontList(Page<Course> coursePage, courseFrontVo courseFrontVo) {
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        if (courseFrontVo!=null){
            if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
                courseQueryWrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
            }
            if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
                courseQueryWrapper.eq("subject_id",courseFrontVo.getSubjectId());
            }
            if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
                courseQueryWrapper.orderByDesc("buy_count");
            }
            if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
                courseQueryWrapper.orderByDesc("gmt_create");
            }
            if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
                courseQueryWrapper.orderByDesc("price");
            }
        }
        this.page(coursePage,courseQueryWrapper);
        Map<String,Object> map= new HashMap<>();
        long total = coursePage.getTotal();
        long current = coursePage.getCurrent();
        long size = coursePage.getSize();
        long pages = coursePage.getPages();
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();
        map.put("items",coursePage.getRecords());
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }

    @Override
    public CoursewebVo getBaseCourseInfo(String courseId) {
        CoursewebVo courseWebInfo = baseMapper.getCourseWebInfo(courseId);
        return courseWebInfo;
    }

}

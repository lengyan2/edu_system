package com.example.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.edu.entity.Course;
import com.example.edu.entity.Teacher;
import com.example.edu.mapper.TeacherMapper;
import com.example.edu.service.CourseService;
import com.example.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-13
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Autowired
    CourseService courseService;

    @Override
    public Map<String, Object> getTeacherList(Page<Teacher> teacherPage) {
        this.page(teacherPage, new QueryWrapper<Teacher>().orderByDesc("id"));
        // 分页的数据返回
        Map<String,Object> map = new HashMap<>();
        List<Teacher> teacherList = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        long current = teacherPage.getCurrent();
        long size = teacherPage.getSize();
        long pages = teacherPage.getPages();
        boolean hasNext = teacherPage.hasNext();
        boolean hasPrevious = teacherPage.hasPrevious();
        map.put("items",teacherList);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }

    @Override
    public Map<String,Object> getTeacherInfoAndCourse(String id) {
        Teacher teacher = this.getById(id);
        List<Course> courseList = courseService.list(new QueryWrapper<Course>().eq("teacher_id", id));
        Map<String,Object> map = new HashMap<>();
        map.put("teacher",teacher);
        map.put("courseList",courseList);
        return map;
    }
}

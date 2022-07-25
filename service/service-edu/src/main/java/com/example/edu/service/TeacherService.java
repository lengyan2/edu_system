package com.example.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-13
 */
public interface TeacherService extends IService<Teacher> {

    Map<String, Object> getTeacherList(Page<Teacher> teacherPage);

    Map<String,Object> getTeacherInfoAndCourse(String id);
}

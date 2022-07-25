package com.example.edu.service;

import com.example.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.edu.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-14
 */
public interface SubjectService extends IService<Subject> {

    void saveSubject(MultipartFile file);

    List<OneSubject> getAllSubject();
}

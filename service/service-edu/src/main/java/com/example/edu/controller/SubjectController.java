package com.example.edu.controller;


import com.example.ResponseResult;
import com.example.edu.entity.subject.OneSubject;
import com.example.edu.service.SubjectService;
import com.example.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-14
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class SubjectController {


    @Autowired
    SubjectService service;

    @Autowired
     OssService ossService;
     @PostMapping("addSubject")
     @CrossOrigin
    // 获取上传的文件 ，把内容读取出来
    public ResponseResult addSubject(MultipartFile file){
         service.saveSubject(file);
         String s = ossService.uploadSubjectExcel(file);
         return ResponseResult.success(s);
     }


     @GetMapping("getAllSubject")
    private ResponseResult getAllSubject(){
         List<OneSubject> subject = service.getAllSubject();
         return ResponseResult.success(subject);
     }
}


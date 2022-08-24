package com.example.oss.controller;
import com.example.ResponseResult;

import com.example.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    OssService ossService;

    // 上传头衔
    @PostMapping("")
    @CrossOrigin
    public ResponseResult uploadOssFile(MultipartFile file){
        String url = ossService.uploadFileAvatar(file);
        // 返回上传到oss的路径
        return ResponseResult.success(url);
    }

    @PostMapping("addSubject")
    public ResponseResult uploadSubjectOss(MultipartFile file){
        String s = ossService.uploadSubjectExcel(file);
        return ResponseResult.success(s);
    }
}

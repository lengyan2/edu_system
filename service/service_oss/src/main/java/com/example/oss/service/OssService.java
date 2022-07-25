package com.example.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {

    // 上传文件
    public String  uploadFileAvatar(MultipartFile file);

    // 上传subject文件到oss
    public String uploadSubjectExcel(MultipartFile file);
}

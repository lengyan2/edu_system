package com.example.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {

    public String uploadVideo(MultipartFile file);

    void  deleteVideo(String videoId);

    void  bathDeleteVideo(List videoList);

    String getPlayAuthByVideoId(String id);
}

package com.example.vod.controller;

import com.example.ResponseResult;
import com.example.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/eduvod/videooss")
public class VodController {
    @Autowired
    VodService vodService;
    @PostMapping("")
    public ResponseResult uploadVideo(MultipartFile file){
        String s = vodService.uploadVideo(file);
        return ResponseResult.success(s);
    }

    @DeleteMapping("deleteVideo/{videoSourceId}")
    public ResponseResult deleteVideo(@PathVariable("videoSourceId") String videoSourceId){
        vodService.deleteVideo(videoSourceId);
        return ResponseResult.success();
    }

    @DeleteMapping("batchDeleteVideo")
    public ResponseResult batchDelete(@RequestParam("videoList")List videoList){
        vodService.bathDeleteVideo(videoList);
        return ResponseResult.success();
    }
    // 根据视频id 获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public ResponseResult getPlayAuth(@PathVariable("id") String id){
      String auth =   vodService.getPlayAuthByVideoId(id);
        HashMap<String, String> map = new HashMap<>();
        map.put("playAuth",auth);
        return ResponseResult.success(map);
    }
}

package com.example.edu.controller;


import com.example.ResponseResult;
import com.example.edu.client.VodClient;
import com.example.edu.entity.Video;
import com.example.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-15
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class VideoController {
    @Autowired
    VideoService videoService;
    @Autowired
    VodClient vodClient;

    // 添加小节
    @PostMapping("addVideo")
    public ResponseResult addVideo(@RequestBody Video video){
        videoService.save(video);
     return  ResponseResult.success("添加成功");
    }
    // 删除小节
    @DeleteMapping("{videoId}")
    public ResponseResult deleteVideo(@PathVariable("videoId") String videoId) throws Exception {
        // 根据小节id获取视频id 在调用方法实现视频删除
        Video video = videoService.getById(videoId);
        String videoSourceId = video.getVideoSourceId();
        if (videoSourceId!=null &&!videoSourceId.isEmpty()){
            ResponseResult responseResult = vodClient.deleteVideo(videoSourceId);
            if (responseResult.getStatus() == "500"){
                System.out.println("2222222");
                throw new RuntimeException("error,熔断器 ");
            }
        }
        videoService.removeById(videoId);
        return ResponseResult.success();
    }

    // 修改小节
    @PostMapping("updateVideo")
    public ResponseResult updateVideo(@RequestBody Video video){
        boolean b = videoService.updateById(video);
        return ResponseResult.success("修改成功");
    }

    // 获取小节信息 通过videoId
    @GetMapping("{videoId}")
    public  ResponseResult getVideoById(@PathVariable("videoId") String videoId){
        Video byId = videoService.getById(videoId);
        return ResponseResult.success(byId);
    }
    // 通过videoSourceid删除video视频
//    @DeleteMapping("deleteVidelo/{videoSourceId}")
//    public ResponseResult deleteVideoByVideoSourceId(@PathVariable("videoSourceId") String videoSourceId){
//        videoService.deleteVideoByVideoCourseId(videoSourceId);
//        return  ResponseResult.success();
//    }
}


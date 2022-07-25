package com.example.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.edu.client.VodClient;
import com.example.edu.entity.Video;
import com.example.edu.mapper.VideoMapper;
import com.example.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import com.example.vod.service.VodService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-15
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

//    @Autowired
//    VodService vodService;
    @Autowired
    VodClient vodClient;
    @Override
    public void deleteVideoByCourseId(String courseId) {
        System.out.println(courseId);
        // 根据课程id查询所有的视video
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id",courseId);
        List<Video> list = this.list(videoQueryWrapper);
        System.out.println(list);
        List<String> videoIds = new ArrayList<>();
        list.forEach(videoid ->{
            if (!videoid.getVideoSourceId().isEmpty()){
                videoIds.add(videoid.getVideoSourceId());
            }
        });
        if (videoIds.size()>0){
        vodClient.batchDelete(videoIds);
        }
        QueryWrapper<Video> videoQueryWrapper1 = new QueryWrapper<>();
        videoQueryWrapper1.eq("course_id",courseId);
        this.remove(videoQueryWrapper1);
    }

    @Override
    public void deleteVideoByVideoCourseId(String videoSourceId) {
//        vodService.deleteVideo(videoSourceId);
    }
}

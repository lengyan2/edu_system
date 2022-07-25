package com.example.edu.service;

import com.example.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-15
 */
public interface VideoService extends IService<Video> {

    void deleteVideoByCourseId(String courseId);

    void deleteVideoByVideoCourseId(String videoSourceId);
}

package com.example.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.edu.entity.Chapter;
import com.example.edu.entity.Video;
import com.example.edu.entity.chapter.ChapterVo;
import com.example.edu.entity.chapter.VideoChapter;
import com.example.edu.mapper.ChapterMapper;
import com.example.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-15
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {
    @Autowired
    VideoService videoService;
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courserId) {
        // 根据课程id查询章节
        List<Chapter> chapterList = this.list(new QueryWrapper<Chapter>().eq("course_id",courserId));
        // 查询 根据对应的章节id 查询 对应章节的小节信息
        List<ChapterVo> finalChapterList = new ArrayList<>();

        chapterList.forEach(chapter -> {
            ChapterVo chapterVo = new ChapterVo();
            // 拿到每一个章节
            String chapterId = chapter.getId();
            String courseId = chapter.getCourseId();
            String chapterTitle = chapter.getTitle();
            chapterVo.setId(chapterId);
            chapterVo.setTitle(chapterTitle);
            List<Video> videoList = videoService.list(new QueryWrapper<Video>().eq("course_id", courseId));
            List<VideoChapter> finalVideoList = new ArrayList<>();
            videoList.forEach(video -> {
                VideoChapter videoVo = new VideoChapter();
                if (video.getChapterId().equals(chapterId)){
                    BeanUtils.copyProperties(video,videoVo);
                    finalVideoList.add(videoVo);
                }
            });
            BeanUtils.copyProperties(chapter,chapterVo);
            chapterVo.setChildren(finalVideoList);

            finalChapterList.add(chapterVo);
        });
        return finalChapterList;
    }

    @Override
    public boolean deleteById(String chapterId) {
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id",chapterId);
        int count = videoService.count(videoQueryWrapper);
        if (count>0){
            return false;
        }
        return this.removeById(chapterId);
    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id",courseId);
        this.remove(chapterQueryWrapper);
    }
}

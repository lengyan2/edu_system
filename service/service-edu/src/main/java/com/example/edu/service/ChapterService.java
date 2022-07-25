package com.example.edu.service;

import com.example.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-15
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courserId);

    boolean deleteById(String chapterId);

    void deleteChapterByCourseId(String courseId);
}

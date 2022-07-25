package com.example.edu.controller;


import com.example.ResponseResult;
import com.example.edu.entity.Chapter;
import com.example.edu.entity.chapter.ChapterVo;
import com.example.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-15
 */
@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    // 返回课程大纲的列表
    @GetMapping("getChapterVideo/{courseId}")
    public ResponseResult getChapterVide(@PathVariable("courseId") String courserId){
        // 查课程中的大纲 和小节
       List<ChapterVo> chapterList=  chapterService.getChapterVideoByCourseId(courserId);

        return ResponseResult.success(chapterList);
    }

    //  添加章节信息
    @PostMapping("addChapter")
    public ResponseResult addChapter(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return ResponseResult.success();
    }
    // 根据章节id查询章节
    @GetMapping("getChapter/{chapterId}")
    public ResponseResult getChapter(@PathVariable("chapterId")String chapterId){
        Chapter byId = chapterService.getById(chapterId);
        return ResponseResult.success(byId);
    }
    // 修改章节信息
    @PostMapping("updateChapter")
    public ResponseResult updateChapter(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return ResponseResult.success();
    }

    // 删除章节信息
    @DeleteMapping("{chapterId}")
    public ResponseResult deleteChapter(@PathVariable("chapterId") String chapterId){
        chapterService.deleteById(chapterId);
        return  ResponseResult.success();
    }
}


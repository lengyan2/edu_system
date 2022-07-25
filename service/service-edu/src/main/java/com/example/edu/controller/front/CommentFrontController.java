package com.example.edu.controller.front;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ResponseResult;
import com.example.edu.client.ucenterClient;
import com.example.edu.entity.Comment;
import com.example.edu.entity.comment.CommentVo;
import com.example.edu.entity.comment.oneComment;
import com.example.edu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/comment")
public class CommentFrontController {

    @Autowired
    CommentService commentService;
    @Autowired
    ucenterClient ucenterClient;
    // 带分页查询的课程评论 ， 包括二级
    @GetMapping("getComment")
    public ResponseResult getComment(@RequestParam("page") Integer page,@RequestParam("limit") Integer limit,@RequestParam("courseId") String courseId){
        Page page1 = commentService.listPage(page, limit, courseId);
        HashMap<String, Object> map = new HashMap<>();
        List records = page1.getRecords();
        long current = page1.getCurrent();
        long size = page1.getSize();
        long pages = page1.getPages();
        long total = page1.getTotal();
        boolean hasNext = page1.hasNext();
        boolean hasPrevious = page1.hasPrevious();
        map.put("records",records);
        map.put("current",current);
        map.put("size",size);
        map.put("pages",pages);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return  ResponseResult.success(map);
    }

    // 添加评论
    @PostMapping(value = "addComment")
    public ResponseResult addComment(@RequestBody CommentVo commentVo, HttpServletRequest request){
        commentService.addComment(commentVo,request);
        return ResponseResult.success();
    }
}

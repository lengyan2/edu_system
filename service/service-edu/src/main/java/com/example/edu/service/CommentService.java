package com.example.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.edu.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.edu.entity.comment.CommentVo;
import com.example.edu.entity.comment.oneComment;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-21
 */
public interface CommentService extends IService<Comment> {

    Page listPage(Integer currentPage,Integer pageSize,String courseId);

    void addComment(CommentVo commentVo, HttpServletRequest request);
}

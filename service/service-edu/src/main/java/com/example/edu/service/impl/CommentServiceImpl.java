package com.example.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.Member;
import com.example.ResponseResult;
import com.example.edu.client.ucenterClient;
import com.example.edu.entity.Comment;
import com.example.edu.entity.comment.CommentVo;
import com.example.edu.entity.comment.oneComment;
import com.example.edu.entity.comment.twoComment;
import com.example.edu.mapper.CommentMapper;
import com.example.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author daiyuanjing
 * @since 2022-07-21
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    ucenterClient ucenterClient;
    @Override
    public Page listPage(Integer currentPage,Integer pageSize, String courseId) {
         // 根据课程id 查询课程相关的评论
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("course_id",courseId);
        // 一寄分类
        commentQueryWrapper.eq("parent_id","0");
        List<Comment> Onelist = this.list(commentQueryWrapper);
         // 二级分类
        QueryWrapper<Comment> commentQueryWrapper1 = new QueryWrapper<>();
        commentQueryWrapper1.eq("course_id",courseId);
        commentQueryWrapper1.ne("parent_id","0");
        List<Comment> towList = this.list(commentQueryWrapper1);
        ArrayList<oneComment> oneComments = new ArrayList<>();
        Onelist.forEach(comment ->{
            oneComment oneComment = new oneComment();
            BeanUtils.copyProperties(comment,oneComment);
            ArrayList<twoComment> twoComments = new ArrayList<>();
            towList.forEach(twoCom ->{
                if (Objects.equals(twoCom.getParentId(), comment.getId())){
                    twoComment twoComment = new twoComment();
                    BeanUtils.copyProperties(twoCom,twoComment);
                    twoComments.add(twoComment);
                }
            });
            oneComment.setList(twoComments);
            oneComments.add(oneComment);
        });

        Page page = new Page();
        int size = oneComments.size();

        if(size == 0) {
            return null;
        }

        if(pageSize > size) {
            pageSize = size;
        }

        // 求出最大页数，防止currentPage越界
        int maxPage = size % pageSize == 0 ? size / pageSize : size / pageSize + 1;

        if(currentPage > maxPage) {
            currentPage = maxPage;
        }

        // 当前页第一条数据的下标
        int curIdx = currentPage > 1 ? (currentPage - 1) * pageSize : 0;

        List pageList = new ArrayList();

        // 将当前页的数据放进pageList
        for(int i = 0; i < pageSize && curIdx + i < size; i++) {
            pageList.add(oneComments.get(curIdx + i));
        }
        page.setCurrent(currentPage).setSize(pageSize).setTotal(oneComments.size()).setRecords(pageList);
        return page;
    }

    @Override
    public void addComment(CommentVo commentVo, HttpServletRequest request) {
        String token = request.getHeader("token");
        System.out.println(token);
        Member commentMember = ucenterClient.getCommentMember(token);
        String userId = commentMember.getId();
        System.out.println(userId);
        String nickname = commentMember.getNickname();
        String avatar = commentMember.getAvatar();
        if (commentVo.getParentId()==null){
            commentVo.setParentId("0");
        }
        commentVo.setMemberId(userId);
        commentVo.setNickname(nickname);
        commentVo.setAvatar(avatar);
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentVo,comment);
        System.out.println(comment);
        this.save(comment);
    }

}
